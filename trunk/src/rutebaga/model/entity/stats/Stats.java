package rutebaga.model.entity.stats;

import java.util.Set;

public interface Stats
{
	double getValue(StatisticId stat);

	StatValue getStatObject(StatisticId stat);

	void undo(Object id);

	void modifyStat(StatModification modification, Object id);
	
	Set<StatisticId> getStatIds();
}
