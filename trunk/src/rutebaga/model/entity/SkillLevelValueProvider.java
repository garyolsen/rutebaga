package rutebaga.model.entity;

import rutebaga.commons.math.BidirectionalValueProvider;

public class SkillLevelValueProvider extends BidirectionalValueProvider<Entity>
{
	private AbilityCategory category;

	public AbilityCategory getCategory()
	{
		return category;
	}

	public void setCategory(AbilityCategory category)
	{
		this.category = category;
	}

	@Override
	public double addTo(Entity t, double value)
	{
		t.allocateSkillPoints(category, (int) value);
		return getValue(t);
	}

	@Override
	public double getValue(Entity t)
	{
		return t.getSkillPoints(category);
	}

}
