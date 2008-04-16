package rutebaga.model.map;

import rutebaga.model.DefaultLayers;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet.InstanceSetIdentifier;

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

	@Override
	public boolean blocks(Instance other)
	{
		return other.able(this.terrain);
	}

	@Override
	public double getFriction()
	{
		// TODO add friction logic (glosh)
		return 0;
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
	protected InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.TILE;
	}

}
