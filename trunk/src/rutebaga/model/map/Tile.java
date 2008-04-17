package rutebaga.model.map;

import rutebaga.model.DefaultLayers;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

/**
 * Stores state information about a hex location. Redirects behavior to its
 * TerrainType.
 * 
 * @author Gary
 * 
 */
public class Tile extends Instance
{
	private TerrainType terrain;
	
	public Tile()
	{
		super();
		this.setTickable(false);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return other.able(this.terrain);
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
	public void tick()
	{
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

}
