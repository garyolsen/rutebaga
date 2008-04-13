package rutebaga.model.entity.stats;

import java.util.HashMap;
import java.util.Map;

public class ConcreteStats implements Stats
{
	private Map<StatisticId, StatValue> stats = new HashMap<StatisticId, StatValue>();

	public double getValue(StatisticId stat)
	{
		return getStatObject(stat).getValue();
	}
	
	public StatValue getStatObject(StatisticId stat)
	{
		if(!stats.containsKey(stat))
		{
			stats.put(stat, new StatValue());
		}
		return stats.get(stat);
	}
	
}
