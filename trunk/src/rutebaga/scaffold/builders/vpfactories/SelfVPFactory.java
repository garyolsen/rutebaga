package rutebaga.scaffold.builders.vpfactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.SelfValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.stats.StatValueProvider;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractValueProviderFactory;

public class SelfVPFactory extends AbstractValueProviderFactory
{
	private static Set<String> types;
	
	static {
		types = new HashSet<String>();
		types.add("value");
	}

	@Override
	protected ValueProvider get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		return new SelfValueProvider();
	}

	@Override
	public Set<String> getValidTypes()
	{
		return types;
	}

}
