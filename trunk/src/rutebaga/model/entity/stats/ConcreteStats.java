package rutebaga.model.entity.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rutebaga.model.entity.Entity;

public class ConcreteStats implements Stats
{
	private Map<StatisticId, StatValue> stats = new HashMap<StatisticId, StatValue>();
	private Map<Object, StatModification> statModifications = new HashMap<Object, StatModification>();
	private Entity parent;
	private ArrayList<StatEventHandler> listeners = new ArrayList<StatEventHandler>();

	public ConcreteStats(Entity parent)
	{
		super();
		this.parent = parent;
	}

	public Entity getParent()
	{
		return parent;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Stats:");
		List<StatisticId> ids = new ArrayList<StatisticId>(stats.keySet());
		Collections.sort(ids, new Comparator<StatisticId>()
		{
			public int compare(StatisticId o1, StatisticId o2)
			{
				return o1.getName().compareTo(o2.getName());
			}	
		});
		for(StatisticId id : ids)
		{
			sb.append("\t").append(id.getName()).append("\t").append(getValue(id)).append("\n");
		}
		return sb.toString();
	}

	public StatValue getStatObject(StatisticId stat)
	{
		if (!stats.containsKey(stat))
		{
			stats.put(stat, stat.makeStatValue(this));
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
		getStatObject(modification.getStat()).addValue(modification.getAmount());
	}

	public void undo(Object id)
	{
		StatModification modification = this.statModifications.remove(id);
		getStatObject(modification.getStat()).addValue(-modification.getAmount());
	}

	public Set<StatisticId> getStatIds()
	{
		return Collections.unmodifiableSet(stats.keySet());
	}

	public void modifyStat(StatModification mod)
	{
		this.stats.get(mod.getStat()).addValue(mod.getAmount());
		notifyHandlers(mod);
	}

	private void notifyHandlers(StatModification mod)
	{
		for(StatEventHandler handler : listeners)
			handler.onStatChange(this, mod);
	}

	public void modifyStat(StatisticId id, double amount)
	{
		modifyStat(new StatModification(id, amount));
	}
	
	public void addHandler(StatEventHandler handler)
	{
		this.listeners.add(handler);
	}
	
	public void removeHandler(StatEventHandler handler)
	{
		this.listeners.remove(handler);
	}

}
