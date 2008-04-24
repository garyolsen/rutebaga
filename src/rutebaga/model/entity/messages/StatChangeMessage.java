package rutebaga.model.entity.messages;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatisticId;

public class StatChangeMessage extends rutebaga.model.entity.Message
{
	private Entity entity;
	private StatisticId statistic;
	private double amount;

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
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

	public StatChangeMessage(Entity entity, StatisticId statistic, double amount)
	{
		super();
		this.entity = entity;
		this.statistic = statistic;
		this.amount = amount;
	}

	@Override
	public void accept(MessageVisitor visitor)
	{
		visitor.visitStatChangeMessage(this);
	}

	@Override
	public String asString()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
