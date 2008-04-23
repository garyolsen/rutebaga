package rutebaga.model.entity.stats;

import java.util.Set;

public interface Stats
{
	/**
	 * Gets the current value of the statistic.
	 * 
	 * @param stat
	 *            the id of the statistic whose value should be returned
	 * @return the current value of the stat
	 */
	double getValue(StatisticId stat);

	/**
	 * Gets the actual StatValue object for the given statistic id.
	 * 
	 * @param stat
	 *            the id of the statistic whose StatValue should be returned
	 * @return the StatValue for the stat
	 * 
	 * @see ConcreteStatValue
	 */
	StatValue getStatObject(StatisticId stat);

	/**
	 * Reverse the effect of the stat modification with the given ID. This ID
	 * must have been given to the modifyStat operation.
	 * 
	 * This operation is not guaranteed to be stable for IDs that have either
	 * not been used with this Stats object, or for IDs representing
	 * modifications that have already been undone.
	 * 
	 * @param id
	 *            the ID for the modification to reverse
	 *            
	 * @see StatModification
	 */
	void undo(Object id);

	/**
	 * Modifies a statistic according to a StatModification, assigning an
	 * identifier to the modification so that it can be undone later.
	 * 
	 * This operation is not guaranteed to be stable for reused IDs.
	 * 
	 * @param modification
	 *            the modification to perform
	 * @param id
	 *            the identifier to use for the modification
	 *            
	 * @see StatModification
	 */
	void modifyStat(StatModification modification, Object id);

	/**
	 * @return the identifiers of the statistics currently supported by this
	 *         Stats object
	 *         
	 * @see StatisticId
	 */
	Set<StatisticId> getStatIds();
	
	void modifyStat(StatModification mod);
	
	void modifyStat(StatisticId id, double amount);
	
	void setBaseValue(StatisticId id, double amount);
}
