package rutebaga.test.scaffold;

import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.DefaultBuilder;

public class TestScaffold
{
	private static MasterScaffold instance;
	
	public static MasterScaffold getInstance()
	{
		if(instance == null)
		{
			instance = new MasterScaffold();
			instance.registerBuilder(new DefaultBuilder());
			instance.build();
		}
		return instance;
	}
}
