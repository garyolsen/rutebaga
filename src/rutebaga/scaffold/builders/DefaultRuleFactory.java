package rutebaga.scaffold.builders;

import rutebaga.scaffold.builders.rulefactories.EntityItemRuleFactory;
import rutebaga.scaffold.builders.rulefactories.StaticRuleFactory;
import rutebaga.scaffold.builders.vpfactories.ConvertorVPFactory;
import rutebaga.scaffold.builders.vpfactories.StatVPFactory;
import rutebaga.scaffold.builders.vpfactories.ValueProviderVPFactory;

public class DefaultRuleFactory
{
	private static ChainedRuleFactory instance;

	private static void init()
	{
		instance = new ChainedRuleFactory();
		instance.addFactory(new EntityItemRuleFactory());
		instance.addFactory(new StaticRuleFactory());
	}

	public static ChainedRuleFactory getInstance()
	{
		if(instance == null) init();
		return instance;
	}
}
