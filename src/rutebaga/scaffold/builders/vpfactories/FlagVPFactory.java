package rutebaga.scaffold.builders.vpfactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.stats.StatValueProvider;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.FlagValueProvider;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractValueProviderFactory;

public class FlagVPFactory extends AbstractValueProviderFactory
{
	private static Set<String> types;
	
	static {
		types = new HashSet<String>();
		types.add("flag");
	}

	@Override
	protected ValueProvider get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		return new FlagValueProvider(params.get("default"));
	}

	@Override
	public Set<String> getValidTypes()
	{
		return types;
	}

}
