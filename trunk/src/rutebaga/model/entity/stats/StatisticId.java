package rutebaga.model.entity.stats;

/**
 * Token class to identify a statistic.
 * 
 * @author Gary LosHuertos
 * 
 */
public class StatisticId
{
	private final String name;
	private double initialValue = 0;
	private boolean hidden = false;

	public StatisticId(String name)
	{
		super();
		this.name = name;
	}

	public double getInitialValue()
	{
		return initialValue;
	}

	public String getName()
	{
		return name;
	}

	public boolean isHidden()
	{
		return hidden;
	}

	public StatValue makeStatValue(Stats stats)
	{
		ConcreteStatValue value = new ConcreteStatValue(this, stats);
		value.setValue(initialValue);
		return value;
	}

	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}

	public void setInitialValue(double initialValue)
	{
		this.initialValue = initialValue;
	}
	
	@Override
	public String toString()
	{
		return "Stat " + name + " (init " + initialValue + ")";
	}
}
