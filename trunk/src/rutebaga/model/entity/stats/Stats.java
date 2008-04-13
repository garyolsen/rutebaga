package rutebaga.model.entity.stats;

public interface Stats
{
	double getValue(StatisticId stat);
	StatValue getStatObject(StatisticId stat);
}
