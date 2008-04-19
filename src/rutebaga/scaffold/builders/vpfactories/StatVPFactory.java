package rutebaga.scaffold.builders.vpfactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.stats.StatValueProvider;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractValueProviderFactory;

public class StatVPFactory extends AbstractValueProviderFactory
{
	private static Set<String> types;
	
	static {
		types = new HashSet<String>();
		types.add("stat");
	}

	@Override
	protected ValueProvider get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		StatisticId stat = (StatisticId) scaffold.get(params.get("default"));
		return new StatValueProvider(stat);
	}

	@Override
	public Set<String> getValidTypes()
	{
		return types;
	}

}
