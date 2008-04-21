package rutebaga.scaffold.builders.vpfactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.AbilityCategory;
import rutebaga.model.entity.SkillLevelValueProvider;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractValueProviderFactory;

public class SkillLevelVPFactory extends AbstractValueProviderFactory
{

	private static Set<String> types;

	static
	{
		types = new HashSet<String>();
		types.add("stat");
	}

	@Override
	protected ValueProvider get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		AbilityCategory cat = (AbilityCategory) scaffold.get(params.get("default"));
		SkillLevelValueProvider p = new SkillLevelValueProvider();
		p.setCategory(cat);
		return p;
	}

	@Override
	public Set<String> getValidTypes()
	{
		return types;
	}

}
