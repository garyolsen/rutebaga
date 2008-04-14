package rutebaga.model.environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import rutebaga.commons.Bounds;
import rutebaga.commons.Vector;
import rutebaga.model.environment.InternalContainer.Location;
import rutebaga.model.environment.InternalContainer.PhysicsContainer;

/**
 * A physical environment of entities in tile-space.
 * 
 * Tile-space is a spatial region which contains an infinite number of
 * coordinates, each of which can be identified as being part of a discrete
 * tile.
 * 
 * The relation of the environment's tile-space and actual space is provided by
 * a TileConvertor.
 * 
 * @author Gary LosHuertos
 * 
 * @see TileConvertor
 * @see Instance
 * 
 */
public class Environment
{
	private Set<Instance> instances = new InstanceSet();
	private Map<Vector, InstanceSet> tileCache = new HashMap<Vector, InstanceSet>();
	private Map<Instance, Vector> reverseTileCache = new HashMap<Instance, Vector>();
	private Set<MovementListener> listeners = new CopyOnWriteArraySet<MovementListener>();
	private TileConvertor tileConvertor;

	/**
	 * Constructs a new Environment using the given converter to define the
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
		if (blocked(instance, tile, false))
			return false;

		// everything is okay -- first remove from old environment
		if (instance.getEnvironment() != null)
		{
			// note: not LOD violation-- getEnvironment()
			// returns an instance of this type
			instance.getEnvironment().remove(instance);
		}

		MovementEvent event = new MovementEvent(instance, null, null);

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

		notifyListeners(event);

		return true;
	}

	/**
	 * @param tile
	 *            the tile to query
	 * @return a read-only set of the instances on the tile
	 */
	public Set<Instance> instancesAt(Vector tile)
	{
		return Collections.unmodifiableSet(getInstanceSetAt(tile));
	}

	/**
	 * Removes an instance from this environment.
	 * 
	 * @param instance
	 *            the instance to remove;
	 */
	public void remove(Instance instance)
	{
		MovementEvent event = new MovementEvent(instance, instance
				.getCoordinate(), instance.getTile());
		instances.remove(instance);
		Vector tile = reverseTileCache.remove(instance);
		instancesAt(tile).remove(instance);
		instance.setLocation(null);
		notifyListeners(event);
	}

	/**
	 * Advances this environment through time.
	 */
	public void tick()
	{
		updatePhysics();
		performMovement();
		for (Instance instance : instances)
			instance.tick();
	}

	/**
	 * Determines whether or not an instance is able to move onto or within a
	 * tile.
	 * 
	 * @param instance
	 *            the instance requiring access
	 * @param tile
	 *            the tile access is being requested to
	 * @return whether or not access is blocked
	 */
	protected boolean blocked(Instance instance, Vector tile,
			boolean emptyBlocks)
	{
		InstanceSet instances = tileCache.get(tile);
		if (instances == null || instances.size() == 0)
			return emptyBlocks;
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
			if (velocity.getMagnitude() <= 0.0001)
				continue;
			Vector newCoordinate = instance.getCoordinate().plus(velocity);
			Vector newTile = tileConvertor.tileOf(newCoordinate);
			if (blocked(instance, newTile, true))
			{
				PhysicsContainer physics = instance.getPhysicsContainer();
				physics.setMomentum(physics.getMomentum().times(0.0));
				physics.setAppliedImpulse(physics.getAppliedImpulse()
						.times(0.0));
				physics.setVelocity(physics.getVelocity().times(0.0));
			}
			else
			{
				Location location = instance.getLocation();
				MovementEvent event = new MovementEvent(instance, instance
						.getCoordinate(), instance.getTile());
				location.setCoordinate(newCoordinate);
				location.setTile(newTile);
				updateTileOf(instance);
				notifyListeners(event);
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

			Set<Instance> newInstances = getInstanceSetAt(newTile);
			newInstances.add(instance);
			reverseTileCache.put(instance, newTile);
		}
	}

	/**
	 * Gets all instances within a certain bounds, when placed in this
	 * environment at a particular center.
	 * 
	 * @param bounds
	 *            the bounds to use to filter
	 * @param center
	 *            the center of the bounds
	 * @return the set of instances within the bounds
	 */
	public Set<Instance> getInstances(Bounds bounds, Vector center)
	{
		Set<Instance> rval = new HashSet<Instance>();
		Set<Vector> tiles = tileCache.keySet();
		tiles = bounds.filter(tiles, center);
		for (Vector tile : tiles)
		{
			rval.addAll(tileCache.get(tile));
		}
		return Collections.unmodifiableSet(rval);
	}

	/**
	 * Registers a movement listener with this environment.
	 * 
	 * @param listener
	 *            the listener to register
	 * 
	 * @see MovementListener
	 */
	public void registerMovementListener(MovementListener listener)
	{
		this.listeners.add(listener);
	}

	/**
	 * Unregisters a movement listener with this environment.
	 * 
	 * @param listener
	 *            the listener to unregister
	 * 
	 * @see MovementListener
	 */
	public void unregisterMovementListener(MovementListener listener)
	{
		this.listeners.remove(listener);
	}

	/**
	 * Notifies all listeners of a movement event.
	 * 
	 * @param event
	 *            the movement event
	 * 
	 * @see MovementEvent
	 */
	private void notifyListeners(MovementEvent event)
	{
		for (MovementListener listener : listeners)
			listener.onMovement(event);
		for (MovementListener listener : event.getInstanceListeners())
		{
			// only notify listeners once
			if (!listeners.contains(listener))
				listener.onMovement(event);
		}
	}

	/**
	 * Gets the set of instances at a tile, creating the set if null.
	 * 
	 * @param tile
	 *            the tile to query
	 * @return the set of instances at the tile
	 */
	private Set<Instance> getInstanceSetAt(Vector tile)
	{
		InstanceSet set = tileCache.get(tile);
		if (set == null)
		{
			set = new InstanceSet();
			tileCache.put(tile, set);
		}
		return set;
	}

	public Set<Vector> getSpace()
	{
		return Collections.unmodifiableSet(tileCache.keySet());
	}

	public int getDimension()
	{
		return tileConvertor.getDimension();
	}
}
