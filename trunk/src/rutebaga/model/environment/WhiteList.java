package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.model.map.TerrainType;

/**
 * An implementation of MovementAttributeSet that is has "white list"
 * functionality.
 * 
 * @author nicholasstamas
 * 
 */
public class WhiteList implements MovementAttributeSet
{

	public Set<TerrainType> terrainSet = new HashSet<TerrainType>();

	public boolean able(TerrainType terrain)
	{

		for (TerrainType t : terrainSet)
		{
			if (t.getName() == terrain.getName())
			{
				return true;
			}
		}

		return false;
	}

	public void add(TerrainType terrain)
	{
		terrainSet.add(terrain);
	}

	public void remove(TerrainType terrain)
	{
		terrainSet.remove(terrain);
	}

}
