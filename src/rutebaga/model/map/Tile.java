package rutebaga.model.map;

import rutebaga.model.DefaultLayers;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

/**
 * Stores state information about a hex location. Redirects behavior to its
 * TerrainType.
 * 
 * @author Gary
 * 
 */
public class Tile<T extends Tile<T>> extends Instance<T>
{
	private TerrainType terrain;

	public Tile(InstanceType<T> type)
	{
		super(type);
		this.setTickable(false);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return !other.able(this.terrain);
	}

	@Override
	public double getLayer()
	{
		return DefaultLayers.TERRAIN.getLayer();
	}

	@Override
	public double getMass()
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.TILE;
	}

	@Override
	public boolean isMobile()
	{
		return false;
	}
	
	public void setTerrainType(TerrainType terrain)
	{
		this.terrain = terrain;
	}

	@Override
	public void tick()
	{
	}

}
