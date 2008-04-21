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
		return properties.keySet().toArray(new String[0]);
	}

	private static Pattern pattern = Pattern
			.compile("(#.*)?([^\\s\\t]+)?[\\s\\t]*(.*?)[\\s\\t]*(#.*)?"); // matches
	// each
	// line,
	// excluding
	// comments
	private static int NAME_GP = 2; // the group of the name
	private static int VALUE_GP = 3; // the group of the value

	private Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();

	private Map<String, String> current;

	public ConfigFileBuilder()
	{
		String path = getDefaultFileName();
		new FileProcessor().execute(this, path);
	}

	public void processLine(String readData)
	{
		if (readData == null || readData.equals(""))
			return;
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
		if(!contains(id, property))
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
		if(!contains(id, property))
			return null;
		return Double.parseDouble(getProperty(id, property));
	}
	
	public boolean contains(String id, String property)
	{
		return getProperty(id, property) != null && !getProperty(id, property).equals("");
	}

	public Boolean getBoolean(String id, String property)
	{
		if(!contains(id, property))
			return null;
		return Boolean.parseBoolean(getProperty(id, property));
	}

	public ValueProvider getValueProvider(String id, String property,
			MasterScaffold scaffold)
	{
		if(!contains(id, property))
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
	
	public Rule[] getRules(String id, String property,
			MasterScaffold scaffold)
	{
		if(!contains(id, property))
			return new Rule[0];
		String[] ids = getStringArray(id, property, "[\\w\\t]");
		List<Rule> rules = new ArrayList<Rule>();
		for(String strId : ids)
		{
			DefaultRuleFactory.getInstance().get(strId, scaffold);
		}
		return rules.toArray(new Rule[0]);
	}

	public String[] getStringArray(String id, String property, String regexp)
	{
		if(!contains(id, property))
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
		if(!contains(id, property))
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
		if(!contains(id, property))
			return null;
		String expr = getProperty(id, property);
		if (expr.charAt(0) == '$')
		{
			return (SymbolicFunction) scaffold.get(expr.substring(1));
		}
		else
		{
			ParseTreeNode n = new ReversePolishParser(DefaultValueProviderFactory.getInstance(), scaffold).parse(expr);
			SymbolicFunction s = new SymbolicFunction();
			s.setTreeRoot(n);
			return s;
		}
	}
}
