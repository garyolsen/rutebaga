package rutebaga.model.entity.stats;

public interface StatValue
{
	StatisticId getId();

	double getValue();

	void addValue(double value);
	
	void setBase(double value);
}