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

	public StatisticId(String name)
	{
		super();
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Stat " + name + " (init " + initialValue + ")";
	}

	public double getInitialValue()
	{
		return initialValue;
	}

	public void setInitialValue(double initialValue)
	{
		this.initialValue = initialValue;
	}

	public String getName()
	{
		return name;
	}
	
	public StatValue makeStatValue(Stats stats)
	{
		ConcreteStatValue value = new ConcreteStatValue(this, stats);
		value.setValue(initialValue);
		return value;
	}
}
