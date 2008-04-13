package rutebaga.model.entity.stats;

import java.util.HashMap;
import java.util.Map;

import rutebaga.model.entity.Entity;

public class ConcreteStats implements Stats
{
	private Map<StatisticId, StatValue> stats = new HashMap<StatisticId, StatValue>();
	private Entity parent;

	public ConcreteStats(Entity parent)
	{
		super();
		this.parent = parent;
	}

	public Entity getParent()
	{
		return parent;
	}
	
	public StatValue getStatObject(StatisticId stat)
	{
		if(!stats.containsKey(stat))
		{
			stats.put(stat, new StatValue());
		}
		return stats.get(stat);
	}

	public double getValue(StatisticId stat)
	{
		return getStatObject(stat).getValue();
	}
	
}
