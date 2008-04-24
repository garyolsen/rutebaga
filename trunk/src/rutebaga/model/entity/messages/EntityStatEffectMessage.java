package rutebaga.model.entity.messages;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.Message;

import rutebaga.model.entity.stats.StatisticId;

public class EntityStatEffectMessage extends Message
{
	private Entity source;
	private Entity target;
	private StatisticId statistic;
	private double amount;

	public EntityStatEffectMessage(Entity source, Entity target, StatisticId statistic, double amount)
	{
		super();
		this.source = source;
		this.target = target;
		this.statistic = statistic;
		this.amount = amount;
	}

	public Entity getTarget()
	{
		return target;
	}

	public void setTarget(Entity target)
	{
		this.target = target;
	}

	public StatisticId getStatistic()
	{
		return statistic;
	}

	public void setStatistic(StatisticId statistic)
	{
		this.statistic = statistic;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	@Override
	public void accept(MessageVisitor visitor)
	{
		visitor.visitEntityStatEffectMessage(this);
	}

	@Override
	public String asString()
	{
		return source + " affected " + target + "'s " + statistic + " by " + amount;
	}

	public Entity getSource()
	{
		return source;
	}

	public void setSource(Entity source)
	{
		this.source = source;
	}
}
