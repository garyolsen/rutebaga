package rutebaga.scaffold.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.rel.ParseTreeNode;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.model.entity.stats.Stats;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public abstract class ConfigFileBuilder implements Builder, ReaderProcessor
{
	public String[] availableIds()
	{
		return properties.keySet().toArray(new String[0]);
	}

	private static Pattern pattern = Pattern
			.compile("(#.*)?([^\\s\\t]+)?[\\s\\t]*(.*?)[\\s\\t]*(#.*)?"); // matches each line, excluding comments
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
		return Integer.parseInt(getProperty(id, property));
	}

	public Double getDouble(String id, String property)
	{
		return Double.parseDouble(getProperty(id, property));
	}

	public Boolean getBoolean(String id, String property)
	{
		return Boolean.parseBoolean(getProperty(id, property));
	}

	public ValueProvider getValueProvider(String id, String property,
			MasterScaffold scaffold)
	{
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
}
