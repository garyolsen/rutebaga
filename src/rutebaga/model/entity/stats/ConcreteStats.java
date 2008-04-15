package rutebaga.model.entity.stats;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.model.entity.Entity;

public class ConcreteStats implements Stats
{
	private Map<StatisticId, StatValue> stats = new HashMap<StatisticId, StatValue>();
	private Map<Object, StatModification> statModifications = new HashMap<Object, StatModification>();
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
		if (!stats.containsKey(stat))
		{
			stats.put(stat, new StatValue(stat));
		}
		return stats.get(stat);
	}

	public double getValue(StatisticId stat)
	{
		return getStatObject(stat).getValue();
	}

	public void modifyStat(StatModification modification, Object id)
	{
		this.statModifications.put(id, modification);
		double current = getValue(modification.getStat());
		double newValue = modification.getAmount() + current;
		getStatObject(modification.getStat()).setValue(newValue);
	}

	public void undo(Object id)
	{
		StatModification modification = this.statModifications.remove(id);
		double current = getValue(modification.getStat());
		double newValue = current - modification.getAmount();
		getStatObject(modification.getStat()).setValue(newValue);
	}

	public Set<StatisticId> getStatIds()
	{
		return Collections.unmodifiableSet(stats.keySet());
	}

}
