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
			if (t.equals(terrain))
				return true;
		}

		return false;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Whitelist[");
		for(TerrainType type : terrainSet)
		{
			sb.append(type.getName()).append("|");
		}
		sb.append("]");
		return sb.toString();
	}

	public void add(TerrainType terrain)
	{
		if(terrain == null)
			throw new RuntimeException();
		terrainSet.add(terrain);
	}

	public void remove(TerrainType terrain)
	{
		terrainSet.remove(terrain);
	}

}
