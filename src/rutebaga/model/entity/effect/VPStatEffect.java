package rutebaga.model.entity.effect;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.stats.StatisticId;

public class VPStatEffect extends ReversibleEntityEffect
{
	private ValueProvider<Entity> augend;
	private StatisticId statId;

	@Override
	public EntityEffect getReverseEffect(Object id)
	{
		return new UndoStatEffect(id);
	}

	@Override
	protected void affect(Entity entity, Object id)
	{
		StatEffect effect = new StatEffect(statId, augend.getValue(entity));
		effect.affect(entity, id);
	}

	public ValueProvider<Entity> getAugend()
	{
		return augend;
	}

	public void setAugend(ValueProvider<Entity> augend)
	{
		this.augend = augend;
	}

	public StatisticId getStatId()
	{
		return statId;
	}

	public void setStatId(StatisticId statId)
	{
		this.statId = statId;
	}

	@Override
	public String toString()
	{
		return "VPStatEffect for " + statId + "=" + augend;
	}

}
