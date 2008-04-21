package rutebaga.model.entity;

import java.util.ArrayList;
import java.util.List;

import rutebaga.commons.logic.ChainedRule;
import rutebaga.commons.logic.Rule;

public class AbilityType
{
	private String name;
	private AbilityCategory category;

	private ChainedRule<Entity> feasibilityRule = new ChainedRule<Entity>(true);
	private ChainedRule<Entity> existenceRule = new ChainedRule<Entity>(true);

	private List<AbilityAction> actions = new ArrayList<AbilityAction>();

	public List<AbilityAction> getActions()
	{
		return actions;
	}

	public AbilityCategory getCategory()
	{
		return category;
	}

	public ChainedRule<Entity> getExistenceRule()
	{
		return existenceRule;
	}

	public ChainedRule<Entity> getFeasibilityRule()
	{
		return feasibilityRule;
	}

	public String getName()
	{
		return name;
	}

	public Ability makeAbility()
	{
		Ability ability = new Ability();
		ability.setType(this);
		ability.setName(name);
		ability.setCategory(category);

		ability.addExistenceRule(existenceRule);
		ability.addFeasibilityRule(feasibilityRule);

		for (AbilityAction action : actions)
			ability.addAction(action);
		
		return ability;
	}

	public void setCategory(AbilityCategory category)
	{
		this.category = category;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Ability named " + name + " in category " + category + " with " + existenceRule.getRules().size() + " existence rules";
	}
}
