package rutebaga.scaffold.builders;

import rutebaga.scaffold.builders.vpfactories.StatVPFactory;

public class DefaultValueProviderFactory
{
	private static ChainedValueProviderFactory instance;

	private static void init()
	{
		instance = new ChainedValueProviderFactory();
		instance.addFactory(new StatVPFactory());
	}

	public static ChainedValueProviderFactory getInstance()
	{
		if(instance == null) init();
		return instance;
	}

}
