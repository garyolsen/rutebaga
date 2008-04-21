package rutebaga.scaffold.builders.rulefactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.logic.Rule;
import rutebaga.commons.logic.StaticRule;
import rutebaga.model.entity.rule.ItemRule;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.item.ItemType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractRuleFactory;

public class StaticRuleFactory extends AbstractRuleFactory
{
	private static Set<String> validTypes;

	{
		validTypes = new HashSet<String>();
		validTypes.add("static");
	}

	@Override
	protected Rule get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		Boolean value = Boolean.parseBoolean(params.get("default"));
		
		StaticRule rule = new StaticRule(value != null && value.booleanValue());
		return rule;
	}

	@Override
	public Set<String> getValidTypes()
	{
		return validTypes;
	}

}
