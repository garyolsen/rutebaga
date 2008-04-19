package rutebaga.controller;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
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
	Entity getAvatar();
	
	/**
	 * @return
	 */
	World getWorld();
}
