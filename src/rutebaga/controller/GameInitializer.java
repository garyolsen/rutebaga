package rutebaga.controller;

import java.util.Collection;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.World;

/**
 * @author Matthew Chuah
 *
 */
public interface GameInitializer {

	/**
	 * 
	 */
	void build();
	
	/**
	 * @return
	 */
	Entity[] getAvatars();
	
	/**
	 * @return
	 */
	World getWorld();
	
	Collection<StatisticId> getDisplayedStats();
}
