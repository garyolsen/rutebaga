package rutebaga.model.entity.stats;

public class StatModification
{
	private StatisticId stat;
	private double amount;

	public StatModification(StatisticId stat, double amount)
	{
		this.stat = stat;
		this.amount = amount;
	}

	public double getAmount()
	{
		return amount;
	}

	public StatisticId getStat()
	{
		return stat;
	}
}
