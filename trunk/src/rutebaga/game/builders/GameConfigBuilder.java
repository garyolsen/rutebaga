package rutebaga.game.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import rutebaga.scaffold.MasterScaffold;

public class GameConfigBuilder extends ConfigFileBuilder
{

	public GameConfigBuilder(MasterScaffold scaffold)
	{
		super(scaffold);
	}

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
