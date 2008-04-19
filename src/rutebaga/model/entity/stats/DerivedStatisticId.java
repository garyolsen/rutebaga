package rutebaga.model.entity.stats;

import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.ValueProvider;

public class DerivedStatisticId extends StatisticId
{
	private ValueProvider<Stats> base;

	public DerivedStatisticId(String name)
	{
		super(name);
		this.base = new ConstantValueProvider<Stats>(0);
	}

	public DerivedStatisticId(String name, ValueProvider<Stats> base)
	{
		super(name);
		this.base = base;
	}
	
	public ValueProvider<Stats> getBase()
	{
		return base;
	}

	@Override
	public StatValue makeStatValue(Stats stats)
	{
		return new DerivedStatValue(base, stats, this);
	}

	public void setBase(ValueProvider<Stats> base)
	{
		this.base = base;
	}

}
