package rutebaga.scaffold.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public abstract class ConfigFileBuilder implements Builder, ReaderProcessor
{
	public String[] availableIds()
	{
		return properties.keySet().toArray(new String[0]);
	}

	private static Pattern pattern = Pattern.compile("([^\\s\\t]+)[\\s\\t]*(.*?)[\\s\\t]*(#.*)?");

	private Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();

	private Map<String, String> current;

	public ConfigFileBuilder()
	{
		String path = getDefaultFileName();
		new FileProcessor().execute(this, path);
	}

	public void processLine(String readData)
	{
		if (readData == null || readData.isEmpty())
			return;
		Matcher m = pattern.matcher(readData);
		String[] tokens = new String[m.matches() ? m.groupCount() : 0];
		System.out.println(m.groupCount());
		for (int i = 0; i < tokens.length; i++)
		{
			tokens[i] = m.group(i+1);
			System.out.println(":" + tokens[i] + ":");
		}
		if (tokens == null)
			return;
		if (tokens.length == 0)
			return;
		if (tokens[1] == null || tokens[1].isEmpty())
		{
			System.out.println();
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
		return scaffold.get(properties.get(id).get(property));
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
}
