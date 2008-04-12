package rutebaga.model.environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.model.environment.InternalContainer.Location;
import rutebaga.model.environment.InternalContainer.PhysicsContainer;

/**
 * An physical environment of entities.
 * 
 * @author Gary LosHuertos
 * 
 */
public class Environment
{
	private Set<Instance> instances = new InstanceSet();
	private Map<Vector, InstanceSet> tileCache = new HashMap<Vector, InstanceSet>();
	private Map<Instance, Vector> reverseTileCache = new HashMap<Instance, Vector>();
	private TileConvertor tileConvertor;

	/**
	 * Constructs a new Environment using the given convertor to define the
	 * tile-space.
	 * 
	 * @param convertor
	 */
	public Environment(TileConvertor convertor)
	{
		this.tileConvertor = convertor;
	}

	/**
	 * @return a read-only set of all instances within this environment
	 */
	public Set<Instance> getInstances()
	{
		return Collections.unmodifiableSet(instances);
	}

	/**
	 * Inserts an instance into this environment.
	 * 
	 * @param instance
	 *            the instance to insert
	 * @param coordinate
	 *            where the instance should be placed
	 * @return whether or not the insertion was successful
	 */
	public boolean add(Instance instance, Vector coordinate)
	{
		if (instances.contains(instance))
		{
			// TODO should this throw an exception instead?
			return false;
		}

		Vector tile = tileConvertor.tileOf(coordinate);
		if (blocked(instance, tile))
			return false;

		// everything is okay -- first remove from old environment
		if (instance.getEnvironment() != null)
		{
			// note: not LOD violation-- getEnvironment()
			// returns an instance of this type
			instance.getEnvironment().remove(instance);
		}

		// add to this environment
		Location location = new Location(instance);
		location.setCoordinate(coordinate);
		location.setTile(tile);
		location.setEnvironment(this);
		instance.setLocation(location);

		instances.add(instance);

		PhysicsContainer physics = new PhysicsContainer(instance);
		instance.setPhysicsContainer(physics);

		// update tile cache
		updateTileOf(instance);

		return true;
	}

	/**
	 * @param tile
	 *            the tile to query
	 * @return a read-only set of the instances on the tile
	 */
	public Set<Instance> instancesAt(Vector tile)
	{
		InstanceSet set = tileCache.get(tile);
		if (set == null)
		{
			set = new InstanceSet();
			tileCache.put(tile, set);
		}
		return Collections.unmodifiableSet(set);
	}

	/**
	 * Removes an instance from this environment.
	 * 
	 * @param instance
	 *            the instance to remove;
	 */
	public void remove(Instance instance)
	{
		instances.remove(instance);
		Vector tile = reverseTileCache.remove(instance);
		instancesAt(tile).remove(instance);
		instance.setLocation(null);
	}

	/**
	 * Advances this environment through time.
	 */
	public void tick()
	{
		updatePhysics();
		performMovement();
	}

	/**
	 * Determines whether or not an instance is able to move onto or within a
	 * tile.
	 * 
	 * @param instance
	 *            the instance requiring access
	 * @param tile
	 *            the tile access is being requested to
	 * @return whether or not access is granted
	 */
	protected boolean blocked(Instance instance, Vector tile)
	{
		InstanceSet instances = tileCache.get(tile);
		if (instances == null)
			return false;
		for (Instance other : instances)
		{
			if (other != instance
					&& (other.blocks(instance) || instance.blocks(other)))
				return true;
		}
		return false;
	}

	/**
	 * Determines the aggregate friction on a tile.
	 * 
	 * @param tile
	 *            the tile to query
	 * @return the space friction present on the tile
	 */
	protected double frictionAt(Vector tile)
	{
		double rval = 0;
		for (Instance instance : instancesAt(tile))
		{
			rval += instance.getFriction();
		}
		return rval;
	}

	/**
	 * @return the cache of tiles
	 */
	protected Map<Vector, InstanceSet> getTileCache()
	{
		return tileCache;
	}

	/**
	 * Moves child instances based on their velocities.
	 */
	protected void performMovement()
	{
		for (Instance instance : instances)
		{
			Vector velocity = instance.getVelocity();
			Vector newCoordinate = instance.getCoordinate().plus(velocity);
			Vector newTile = tileConvertor.tileOf(newCoordinate);
			if (blocked(instance, newTile))
			{
				PhysicsContainer physics = instance.getPhysicsContainer();
				physics.setMomentum(physics.getMomentum().times(0.0));
				physics.setVelocity(physics.getVelocity().times(0.0));
			}
			else
			{
				Location location = instance.getLocation();
				location.setCoordinate(newCoordinate);
				location.setTile(newTile);
				updateTileOf(instance);
			}
		}
	}

	/**
	 * Updates physical (Newtonian) characteristics of child instances.
	 */
	protected void updatePhysics()
	{
		for (Vector tile : tileCache.keySet())
		{
			double friction = frictionAt(tile);
			for (Instance instance : instancesAt(tile))
			{
				instance.getPhysicsContainer().update(friction);
			}
		}
	}

	/**
	 * To be called when the instance's tile has changed.
	 * 
	 * Updates internal caching of tile-space information.
	 * 
	 * @param instance
	 *            the instance to update in the cache
	 */
	protected void updateTileOf(Instance instance)
	{
		Vector current = reverseTileCache.get(instance);
		Vector newTile = instance.getTile();
		if (!newTile.equals(current))
		{
			InstanceSet currentInstances = tileCache.get(current);
			if (currentInstances != null)
			{
				currentInstances.remove(instance);
			}

			Set<Instance> newInstances = instancesAt(newTile);
			newInstances.add(instance);
		}
	}
}
