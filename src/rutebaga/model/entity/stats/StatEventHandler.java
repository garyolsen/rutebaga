package rutebaga.model.entity.stats;

public interface StatEventHandler
{
	void onStatChange(Stats stats, StatModification mod);
}
