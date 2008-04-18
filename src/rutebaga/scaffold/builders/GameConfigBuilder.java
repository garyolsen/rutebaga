package rutebaga.scaffold.builders;

import java.util.Properties;

import rutebaga.scaffold.MasterScaffold;

public class GameConfigBuilder extends ConfigFileBuilder
{
	@Override
	protected String getDefaultFileName()
	{
		return "config/game";
	}

	public Object create(String id)
	{
		Properties prop = new Properties();
		prop.putAll(this.getMap(id));
		return prop;
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		
	}

}
