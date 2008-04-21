package rutebaga.model.entity;

import rutebaga.model.entity.stats.ConcreteStatValue;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;

public class ChainedConcreteStatValue extends ConcreteStatValue
{
	private StatisticId parentStat;

	public ChainedConcreteStatValue(StatisticId parentStat, StatisticId id, Stats parent)
	{
		super(id, parent);
		this.parentStat = parentStat;
	}

	@Override
	public double getValue()
	{
		return super.getValue() + getInheritedValue();
	}

	@Override
	public void setValue(double value)
	{
		super.setValue(value - getInheritedValue());
	}
	
	
	private double getInheritedValue()
	{
		if(parentStat == null)
			return 0;
		Stats stats = this.getParent();
		return stats.getValue(parentStat);
	}

}
