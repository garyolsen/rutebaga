package rutebaga.game;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.entity.stats.StatisticId;

public class CooldownStatValue implements StatValue
{
	private Entity entity;
	private StatisticId id = new StatisticId("Cooldown");

	public CooldownStatValue(Entity entity)
	{
		super();
		this.entity = entity;
	}

	public void addValue(double value)
	{
		throw new UnsupportedOperationException();
	}

	public StatisticId getId()
	{
		return id;
	}

	public double getValue()
	{
		return entity.getCooldown();
	}

	public void setBase(double value)
	{
		throw new UnsupportedOperationException();
	}

}
