package rutebaga.scaffold.builders.rulefactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.logic.Rule;
import rutebaga.commons.logic.StaticRule;
import rutebaga.model.environment.FlagRule;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractRuleFactory;

public class FlagRuleFactory extends AbstractRuleFactory
{
	private static Set<String> validTypes;

	{
		validTypes = new HashSet<String>();
		validTypes.add("flag");
	}

	@Override
	protected Rule get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		System.out.println("LAAAAA");
		String flag = params.get("flag");
		Boolean value = Boolean.parseBoolean(params.get("value"));
		
		FlagRule rule = new FlagRule(flag);
		rule.setValue(value);
		
		return rule;
	}

	@Override
	public Set<String> getValidTypes()
	{
		return validTypes;
	}
}
