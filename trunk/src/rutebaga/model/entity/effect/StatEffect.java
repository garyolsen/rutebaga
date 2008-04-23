package rutebaga.model.entity.effect;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.stats.StatModification;
import rutebaga.model.entity.stats.StatisticId;

public class StatEffect extends ReversibleEntityEffect
{
	private StatModification modification;

	public StatEffect(StatModification modification)
	{
		super();
		this.modification = modification;
	}

	public StatEffect(StatisticId statId, double amount)
	{
		this(new StatModification(statId, amount));
	}

	@Override
	public EntityEffect getReverseEffect(Object id)
	{
		return new UndoStatEffect(id);
	}

	@Override
	protected void affect(Entity entity, Object id)
	{
		// XXX LOD violation
		entity.getStats().modifyStat(modification, id);
	}

	@Override
	public String toString()
	{
		return "StafEffect for " + modification;
	}

}
