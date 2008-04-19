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
		ConcreteStatValue value = new ConcreteStatValue(this);
		value.setValue(initialValue);
		return value;
	}
}
