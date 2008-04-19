package rutebaga.scaffold.builders.vpfactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.Convertor;
import rutebaga.commons.math.ConvertorValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.convertors.EntityStatsConvertor;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractValueProviderFactory;
import rutebaga.scaffold.builders.DefaultValueProviderFactory;

public class ConvertorVPFactory extends AbstractValueProviderFactory
{
	private static Set<String> types;

	static
	{
		types = new HashSet<String>();
		types.add("@entityStats");
	}

	@Override
	protected ValueProvider get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		Convertor c = null;
		ValueProvider vp = null;

		if ("@entityStats".equals(type))
		{
			c = new EntityStatsConvertor();
			vp = DefaultValueProviderFactory.getInstance().get(
					params.get("default"), scaffold);
		}

		return new ConvertorValueProvider(vp, c);
	}

	@Override
	public Set<String> getValidTypes()
	{
		return types;
	}

}
