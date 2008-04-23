package rutebaga.model.entity.stats;

import rutebaga.commons.math.ValueProvider;

public class DerivedStatValue implements StatValue
{
	private double offset = 0;
	private ValueProvider<Stats> base;
	private Stats parent;
	private StatisticId statId;

	public DerivedStatValue(ValueProvider<Stats> base, Stats parent,
			StatisticId statId)
	{
		super();
		this.base = base;
		this.parent = parent;
		this.statId = statId;
	}

	public void addValue(double value)
	{
		offset += value;
	}

	public StatisticId getId()
	{
		return statId;
	}

	public double getValue()
	{
		return base.getValue(parent) + offset;
	}

	public void setBase(double value)
	{
		this.offset = value;
	}

}
