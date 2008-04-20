package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.model.map.TerrainType;

public class BlackList implements MovementAttributeSet
{

	private Set<TerrainType> terrainSet = new HashSet<TerrainType>();

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Blacklist[");
		for(TerrainType type : terrainSet)
		{
			sb.append(type.getName()).append("|");
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean able(TerrainType terrain)
	{
		for (TerrainType t : terrainSet)
		{
			if (t.equals(terrain))
				return false;
		}
		return true;
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
