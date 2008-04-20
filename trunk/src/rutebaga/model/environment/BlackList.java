package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.model.map.TerrainType;

public class BlackList implements MovementAttributeSet {

	private Set<TerrainType> terrainSet = new HashSet<TerrainType>();

	public boolean able(TerrainType terrain) {
		for (TerrainType t : terrainSet) {
			if (t.getName() == terrain.getName())
				return false;
		}
		return true;
	}

	public void add(TerrainType terrain) {
		terrainSet.add(terrain);
	}

	public void remove(TerrainType terrain) {
		terrainSet.remove(terrain);
	}

}
