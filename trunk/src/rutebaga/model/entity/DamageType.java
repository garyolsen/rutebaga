package rutebaga.model.entity;

import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;

public class DamageType extends StatisticId
{
	private DamageType parent;

	public DamageType(String name)
	{
		super(name);
	}

	public DamageType getParent()
	{
		return parent;
	}

	@Override
	public StatValue makeStatValue(Stats stats)
	{
		ChainedConcreteStatValue value = new ChainedConcreteStatValue(parent, this, stats);
		value.setValue(getInitialValue());
		return value;
	}

	public void setParent(DamageType parent)
	{
		this.parent = parent;
	}
}
