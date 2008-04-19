package rutebaga.scaffold.builders;

import rutebaga.scaffold.builders.vpfactories.ConvertorVPFactory;
import rutebaga.scaffold.builders.vpfactories.StatVPFactory;
import rutebaga.scaffold.builders.vpfactories.ValueProviderVPFactory;

public class DefaultValueProviderFactory
{
	private static ChainedValueProviderFactory instance;

	private static void init()
	{
		instance = new ChainedValueProviderFactory();
		instance.addFactory(new StatVPFactory());
		instance.addFactory(new ValueProviderVPFactory());
		instance.addFactory(new ConvertorVPFactory());
	}

	public static ChainedValueProviderFactory getInstance()
	{
		if(instance == null) init();
		return instance;
	}

}
