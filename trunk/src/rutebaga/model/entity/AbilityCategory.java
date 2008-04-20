package rutebaga.model.entity;

public class AbilityCategory
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public AbilityCategory(String property)
	{
		this.name = property;
	}

	@Override
	public String toString()
	{
		return "AbilityCategory " + name;
	}
	

}
