package rutebaga.scaffold.builders;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutebaga.commons.logic.Rule;
import rutebaga.commons.math.SymbolicFunction;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.commons.math.rel.ParseTreeNode;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public abstract class ConfigFileBuilder implements Builder, ReaderProcessor
{
	public String[] availableIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(properties.keySet());
		list.addAll(getGlobalIds());
		return list.toArray(new String[0]);
	}

	public final static Pattern pattern = Pattern
			.compile("[\\s\\t]*(#.*)?([^\\s\\t]+)?[\\s\\t]*(.*?)[\\s\\t]*(#.*)?"); // matches

	private static Pattern multilineStartPattern = Pattern
			.compile("[\\s\\t]*(\\w+)[\\s\\t]*\\{.*");
	private static int MULTILINE_NAME_IDX = 1;
	private static Pattern multilineEndPattern = Pattern.compile(".*\\}.*");
	
	// each
	// line,
	// excluding
	// comments
	public static int NAME_GP = 2; // the group of the name
	public static int VALUE_GP = 3; // the group of the value

	private ArrayList activeInnerList;

	private Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
	private Map<String, Map<String, ArrayList>> innerLists = new HashMap<String, Map<String, ArrayList>>();
	
	private ArrayList<String> globalIds = new ArrayList<String>();

	private String currentId;
	private Map<String, String> current;

	public ConfigFileBuilder()
	{
		String path = getDefaultFileName();
		new FileProcessor().execute(this, path);
	}
	
	public ArrayList<String> getInnerList(String id, String property)
	{
		return innerLists.get(id).get(property);
	}
	
	public final ArrayList<String> globalIds()
	{
		return globalIds;
	}

	public void processLine(String readData)
	{
		if (readData == null || readData.equals(""))
			return;

		if (activeInnerList != null)
		{
			Matcher endMatcher = multilineEndPattern.matcher(readData);
			if (endMatcher.matches())
			{
				activeInnerList = null;
				return;
			}
			else
			{
				activeInnerList.add(readData);
			}
		}
		else
		{
			Matcher startMatcher = multilineStartPattern.matcher(readData);
			if (startMatcher.matches())
			{
				String propertyName = startMatcher.group(MULTILINE_NAME_IDX);
				activeInnerList = new ArrayList();
				if (innerLists.get(currentId) == null)
				{
					innerLists.put(currentId, new HashMap<String, ArrayList>());
				}
				innerLists.get(currentId).put(propertyName, activeInnerList);
				
				return;
			}
		}

		Matcher m = pattern.matcher(readData);
		String[] tokens = null;
		if (m.matches())
		{
			tokens = new String[2];
			tokens[0] = m.group(NAME_GP);
			tokens[1] = m.group(VALUE_GP);
		}
		rutebaga.commons.Log.log(readData);
		rutebaga.commons.Log.log(":" + tokens[0] + ":" + tokens[1] + ":");
		if (tokens == null)
			return;
		if (tokens.length == 0)
			return;
		if (tokens[0] == null)
			return;
		if (tokens[1] == null || tokens[1].equals(""))
		{
			// this is a new id
			currentId = tokens[0];
			rutebaga.commons.Log.log();
			current = new HashMap<String, String>();
			properties.put(tokens[0], current);
		}
		else
		{
			current.put(tokens[0], tokens[1]);
		}
	}

	public Map<String, Map<String, String>> getProperties()
	{
		return properties;
	}

	protected Object getObjectFor(String id, String property,
			MasterScaffold scaffold)
	{
		if (!contains(id, property))
			return null;
		if (scaffold.get(id) != null)
			return scaffold.get(properties.get(id).get(property));
		return null;
	}

	protected abstract String getDefaultFileName();

	protected String getProperty(String id, String property)
	{
		return properties.get(id).get(property);
	}

	public Map<String, String> getMap(String id)
	{
		return properties.get(id);
	}

	public Integer getInteger(String id, String property)
	{
		if (getProperty(id, property) != null)
			return Integer.parseInt(getProperty(id, property));
		return null;
	}

	public Double getDouble(String id, String property)
	{
		if (!contains(id, property))
			return null;
		return Double.parseDouble(getProperty(id, property));
	}

	public boolean contains(String id, String property)
	{
		return getProperty(id, property) != null
				&& !getProperty(id, property).equals("");
	}

	public Boolean getBoolean(String id, String property)
	{
		if (!contains(id, property))
			return null;
		return Boolean.parseBoolean(getProperty(id, property));
	}

	public ValueProvider getValueProvider(String id, String property,
			MasterScaffold scaffold)
	{
		if (!contains(id, property))
			return null;
		String expr = getProperty(id, property);
		if (expr.charAt(0) == '$')
		{
			return (ValueProvider) scaffold.get(expr.substring(1));
		}
		else
		{
			ParseTreeNode n = new ReversePolishParser().parse(expr);
			ValueProviderASTVisitor v = new ValueProviderASTVisitor(
					DefaultValueProviderFactory.getInstance(), scaffold);
			n.accept(v);
			return v.getValueProvider();
		}
	}
	
	public Vector2D getVector2D(String id, String property)
	{
		if (!contains(id, property))
			return null;
		return getVector2D(getProperty(id, property));
	}
	
	public Vector2D getVector2D(String description)
	{
		String[] parts = description.split("[\\s\\t]");
		return new Vector2D(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
	}

	public Rule[] getRules(String id, String property, MasterScaffold scaffold)
	{
		if (!contains(id, property))
			return new Rule[0];
		String[] ids = getStringArray(id, property, "[\\w\\t]");
		List<Rule> rules = new ArrayList<Rule>();
		for (String strId : ids)
		{
			DefaultRuleFactory.getInstance().get(strId, scaffold);
		}
		return rules.toArray(new Rule[0]);
	}

	public String[] getStringArray(String id, String property, String regexp)
	{
		if (!contains(id, property))
			return new String[0];
		String prop = getProperty(id, property);
		if (prop == null)
			return new String[0];
		String[] rval = prop.split(regexp);
		return rval;
	}

	public Object[] getObjectArray(String id, String property, String regexp,
			MasterScaffold scaffold)
	{
		if (!contains(id, property))
			return new Object[0];
		ArrayList list = new ArrayList();
		for (String scaffId : getStringArray(id, property, regexp))
		{
			list.add(scaffold.get(scaffId));
		}
		return list.toArray();
	}

	public SymbolicFunction getFunction(String id, String property,
			MasterScaffold scaffold)
	{
		if (!contains(id, property))
			return null;
		String expr = getProperty(id, property);
		if (expr.charAt(0) == '$')
		{
			return (SymbolicFunction) scaffold.get(expr.substring(1));
		}
		else
		{
			ParseTreeNode n = new ReversePolishParser(
					DefaultValueProviderFactory.getInstance(), scaffold)
					.parse(expr);
			SymbolicFunction s = new SymbolicFunction();
			s.setTreeRoot(n);
			return s;
		}
	}

	public ArrayList<String> getGlobalIds()
	{
		return globalIds;
	}
}
