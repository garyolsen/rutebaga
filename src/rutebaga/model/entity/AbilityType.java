package rutebaga.model.entity;

public abstract class AbilityType
{
	private String name;
	private AbilityCategory category;
	
	public AbilityCategory getCategory()
	{
		return category;
	}

	public void setCategory(AbilityCategory category)
	{
		this.category = category;
	}

	public Ability makeAbility()
	{
		Ability ability = new Ability();
		ability.setType(this);
		ability.setName(name);
		ability.setCategory(category);
		return ability;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Ability named " + name + " in category " + category;
	}
}
