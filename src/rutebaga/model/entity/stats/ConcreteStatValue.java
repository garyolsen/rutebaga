package rutebaga.model.entity.stats;

public class ConcreteStatValue implements StatValue
{
	// FIXME add stat range
	private final StatisticId id;
	private double value;

	public ConcreteStatValue(StatisticId id) {
		if (id == null)
			throw new NullPointerException();
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see rutebaga.model.entity.stats.StatValue#getId()
	 */
	public StatisticId getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see rutebaga.model.entity.stats.StatValue#getValue()
	 */
	public double getValue()
	{
		return value;
	}

	/* (non-Javadoc)
	 * @see rutebaga.model.entity.stats.StatValue#addValue(double)
	 */
	public void addValue(double value)
	{
		this.value += value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}
}
