package rutebaga.scaffold.builders.rulefactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.logic.Rule;
import rutebaga.model.entity.rule.ItemRule;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.item.ItemType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractRuleFactory;

public class EntityItemRuleFactory extends AbstractRuleFactory
{
	private static Set<String> validTypes;

	{
		validTypes = new HashSet<String>();
		validTypes.add("&EntityItem");
	}

	@Override
	protected Rule get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		ItemRule rule = new ItemRule();

		boolean mustBeEquipped = params.containsKey("equipped") ? Boolean
				.parseBoolean(params.get("equipped")) : false;
		ItemType itemType = (ItemType) scaffold.get(params.get("itemtype"));
		
		rule.setMustBeEquipped(mustBeEquipped);
		rule.setType(itemType);
		
		return rule;
	}

	@Override
	public Set<String> getValidTypes()
	{
		return validTypes;
	}

}
