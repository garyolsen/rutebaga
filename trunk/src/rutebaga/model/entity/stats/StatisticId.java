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

	public StatisticId(String name)
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
