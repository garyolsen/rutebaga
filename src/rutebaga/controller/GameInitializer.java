package rutebaga.controller;

import rutebaga.model.entity.CharEntity;
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
	CharEntity getAvatar();
	
	/**
	 * @return
	 */
	World getWorld();
}
