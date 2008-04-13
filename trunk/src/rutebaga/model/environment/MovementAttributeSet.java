package rutebaga.model.environment;

import rutebaga.model.map.TerrainType;

public interface MovementAttributeSet {

	public boolean able( TerrainType terrain );
	public void add( TerrainType terrain );
	public void remove( TerrainType terrain );
	
}
