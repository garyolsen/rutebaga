package rutebaga.scaffold.builders;

import java.util.ArrayList;

import rutebaga.commons.logic.Rule;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.AbilityCategory;
import rutebaga.model.entity.AbilityType;
import rutebaga.scaffold.MasterScaffold;

public class AbilityTypeBuilder extends AbstractAbilityBuilder
{
	private AbilityActionFactory factory = new AbilityActionFactory();

	@Override
	protected String getDefaultFileName()
	{
		return "config/abilities";
	}

	public Object create(String id)
	{
		return new AbilityType();
	}

	@Override
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		
		AbilityType type = (AbilityType) object;
		
		String[] actionDescriptions = getInnerList(id, "actions").toArray(new String[0]);
		for(String description : actionDescriptions)
		{
			AbilityAction action = factory.get(description, scaffold);
			type.getActions().add(action);
		}
		
		for(Rule rule : getRules(id, "existence", scaffold))
		{
			System.out.println("WORDUP " + rule);
			type.getExistenceRule().add(rule);
		}
		
		for(Rule rule : getRules(id, "feasibility", scaffold))
		{
			type.getFeasibilityRule().add(rule);
		}
	}

}
