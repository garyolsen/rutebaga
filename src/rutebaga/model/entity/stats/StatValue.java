package rutebaga.model.entity.stats;

public class StatValue
{
	// FIXME add stat range
	private final StatisticId id;
	private double value;

	public StatValue(StatisticId id) {
		if (id == null)
			throw new NullPointerException();
		this.id = id;
	}

	public StatisticId getId() {
		return id;
	}
	
	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}
}
