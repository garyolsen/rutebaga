package rutebaga.scaffold.builders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.ValueProvider;
import rutebaga.scaffold.MasterScaffold;

public class ChainedValueProviderFactory extends AbstractValueProviderFactory
{
	private Map<String, AbstractValueProviderFactory> factories = new HashMap<String, AbstractValueProviderFactory>();

	@Override
	protected ValueProvider get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		return factories.get(type).get(type, params, scaffold);
	}

	@Override
	public Set<String> getValidTypes()
	{
		return Collections.unmodifiableSet(factories.keySet());
	}

	public void addFactory(AbstractValueProviderFactory factory)
	{
		for (String type : factory.getValidTypes())
			factories.put(type, factory);
	}

}
