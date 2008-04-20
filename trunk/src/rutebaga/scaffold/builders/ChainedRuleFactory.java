package rutebaga.scaffold.builders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.logic.Rule;
import rutebaga.commons.math.ValueProvider;
import rutebaga.scaffold.MasterScaffold;

public class ChainedRuleFactory extends AbstractRuleFactory
{
	private Map<String, AbstractRuleFactory> factories = new HashMap<String, AbstractRuleFactory>();

	@Override
	protected Rule get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		return factories.get(type).get(type, params, scaffold);
	}

	@Override
	public Set<String> getValidTypes()
	{
		return Collections.unmodifiableSet(factories.keySet());
	}

	public void addFactory(AbstractRuleFactory factory)
	{
		for (String type : factory.getValidTypes())
			factories.put(type, factory);
	}

}
