package rutebaga.model.environment;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import rutebaga.commons.math.Bounds2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
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
 * @see TileConverter
 * @see Instance
 * 
 */
public class Environment
{
	private Set<Instance> instances = new HashSet<Instance>();
	private Map<IntVector2D, HashSet<Instance>> tileCache = new HashMap<IntVector2D, HashSet<Instance>>();
	private Map<Instance, IntVector2D> reverseTileCache = new HashMap<Instance, IntVector2D>();
	private Set<MovementListener> listeners = new CopyOnWriteArraySet<MovementListener>();
	private TileConverter tileConvertor;

	private Map<IntVector2D, Double> frictionCache = new HashMap<IntVector2D, Double>();

	private Set<Instance> dirtyPhysics = new CopyOnWriteArraySet<Instance>();
	private Set<IntVector2D> dirtyFriction = new CopyOnWriteArraySet<IntVector2D>();
	private Set<Instance> tickableInstances = new CopyOnWriteArraySet<Instance>();

	/**
	 * Constructs a new Environment using the given converter to define the
	 * tile-space.
	 * 
	 * @param convertor
	 */
	public Environment(TileConverter convertor)
	{
		this.tileConvertor = convertor;
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
	public boolean add(Instance instance, Vector2D coordinate)
	{
		if (instances.contains(instance))
		{
			// TODO should this throw an exception instead?
			return false;
		}

		IntVector2D tile = tileConvertor.tileOf(coordinate);
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
		if (!instance.isMobile())
			physics.setImmobile(true);
		instance.setPhysicsContainer(physics);

		dirtyFriction.add(instance.getTile());

		// update tile cache
		updateTileOf(instance);

		notifyListeners(event);

		return true;
	}

	public boolean blockedAtTile(IntVector2D v, Instance instance)
	{
		return this.blocked(instance, v, true);
	}

	public boolean exists(IntVector2D tile)
	{
		if (!tileCache.containsKey(tile))
			return false;
		Set<Instance> set = getInstanceSetAt(tile);
		if (set == null || set.size() == 0)
			return false;
		return true;
	}

	public int getDimension()
	{
		return tileConvertor.getDimension();
	}

	/**
	 * @return a read-only set of all instances within this environment
	 */
	public Set<Instance> getInstances()
	{
		return Collections.unmodifiableSet(instances);
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
	public Collection<Instance> getInstances(Bounds2D bounds, Vector2D center)
	{
		List<Instance> rval = new LinkedList<Instance>();
		Collection<IntVector2D> tiles = tileCache.keySet();
		tiles = bounds.filter(tiles, center);
		for (IntVector2D tile : tiles)
		{
			rval.addAll(tileCache.get(tile));
		}
		return Collections.unmodifiableList(rval);
	}

	public Set<IntVector2D> getSpace()
	{
		return Collections.unmodifiableSet(tileCache.keySet());
	}

	/**
	 * @param tile
	 *            the tile to query
	 * @return a read-only set of the instances on the tile
	 */
	public Set<Instance> instancesAt(IntVector2D tile)
	{
		return Collections.unmodifiableSet(getInstanceSetAt(tile));
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
		IntVector2D tile = reverseTileCache.remove(instance);
		getInstanceSetAt(tile).remove(instance);
		instance.setLocation(null);
		dirtyPhysics.remove(instance);
		tickableInstances.remove(instance);
		notifyListeners(event);
	}

	/**
	 * Advances this environment through time.
	 */
	public void tick()
	{
		updatePhysics();
		performMovement();
		long time = System.currentTimeMillis();
		for(Instance instance : instances)
		{
			instance.instanceTickOps();
		}
		for (Instance instance : tickableInstances)
		{
			instance.tick();
		}
		rutebaga.commons.Log.log("ticking environment: " + (System.currentTimeMillis()-time));
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
	 * Gets the set of instances at a tile, creating the set if null.
	 * 
	 * @param tile
	 *            the tile to query
	 * @return the set of instances at the tile
	 */
	private Set<Instance> getInstanceSetAt(IntVector2D tile)
	{
		HashSet<Instance> set = tileCache.get(tile);
		if (set == null)
		{
			set = new HashSet<Instance>();
			tileCache.put(tile, set);
		}
		return set;
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
		long time = System.currentTimeMillis();
		for (MovementListener listener : listeners)
			listener.onMovement(event);
		for (MovementListener listener : event.getInstanceListeners())
		{
			// only notify listeners once
			if (!listeners.contains(listener))
				listener.onMovement(event);
		}
//		rutebaga.commons.Log.log("notifying listeners: "
//				+ (System.currentTimeMillis() - time));
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
	protected boolean blocked(Instance instance, IntVector2D tile,
			boolean emptyBlocks)
	{
		HashSet<Instance> instances = tileCache.get(tile);
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
	protected double frictionAt(IntVector2D tile)
	{
		double rval = 0;
		for (Instance instance : instancesAt(tile))
		{
			rval += instance.getFriction();
		}
		return rval;
	}

	protected Set<Instance> getDirtyPhysics()
	{
		return dirtyPhysics;
	}

	/**
	 * @return the cache of tiles
	 */
	protected Map<IntVector2D, HashSet<Instance>> getTileCache()
	{
		return tileCache;
	}

	public TileConverter getTileConvertor()
	{
		return tileConvertor;
	}

	/**
	 * Moves child instances based on their velocities.
	 */
	protected void performMovement()
	{
		long time = System.currentTimeMillis();
		int moveCt = 0;
		for (Instance<?> instance : instances)
		{
			Vector2D velocity = instance.getVelocity();
			if (velocity.getMagnitude() <= 0.0001)
				continue;
			Vector2D newCoordinate = instance.getCoordinate().plus(velocity);
			IntVector2D newTile = tileConvertor.tileOf(newCoordinate);
			Collection<IntVector2D> inBetween = tileConvertor.between(instance
					.getTile(), newTile);
			boolean blocked = false;
			for (IntVector2D blockTile : inBetween)
			{
				if (blocked(instance, blockTile, true))
				{
					blocked = true;
					break;
				}
			}
			blocked = blocked || blocked(instance, newTile, true);
			if (blocked)
			{
				PhysicsContainer physics = instance.getPhysicsContainer();
				physics.getMomentum().multiplyBy(0.0);
				// physics.getAppliedImpulse().multiplyBy(0.0);
				physics.getVelocity().multiplyBy(0.0);
			}
			else
			{
				Location location = instance.getLocation();
				MovementEvent event = new MovementEvent(instance, instance
						.getCoordinate(), instance.getTile());
				if (instance.getFriction() > 0.001)
				{
					dirtyFriction.add(newTile);
					dirtyFriction.add(instance.getTile());
				}
				location.setCoordinate(newCoordinate);
				location.setTile(newTile);
				updateTileOf(instance);
				notifyListeners(event);
				moveCt++;
			}
		}
		rutebaga.commons.Log.log("movement count: " + moveCt + ": "
				+ (System.currentTimeMillis() - time));
	}

	/**
	 * Updates physical (Newtonian) characteristics of child instances.
	 */
	protected void updatePhysics()
	{
		long time = System.currentTimeMillis();
		for (Instance instance : dirtyPhysics)
		{
			IntVector2D tile = instance.getTile();
			double friction;
			if(dirtyFriction.contains(tile))
			{
				dirtyFriction.remove(tile);
				friction = frictionAt(tile);
				frictionCache.put(tile, friction);
			}
			else
			{
				friction = frictionCache.get(tile);
			}
			instance.getPhysicsContainer().update(
					frictionCache.get(instance.getTile()));

		}
		rutebaga.commons.Log.log("physics update: "
				+ (System.currentTimeMillis() - time));
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
		IntVector2D current = reverseTileCache.get(instance);
		IntVector2D newTile = instance.getTile();
		if (!newTile.equals(current))
		{
			HashSet<Instance> currentInstances = tileCache.get(current);
			if (currentInstances != null)
			{
				currentInstances.remove(instance);
			}

			Set<Instance> newInstances = getInstanceSetAt(newTile);
			newInstances.add(instance);
			reverseTileCache.put(instance, newTile);
		}
	}

	Set<Instance> getTickableInstances()
	{
		return tickableInstances;
	}
	
	public IntVector2D getTileOf(Vector2D coordinate)
	{
		return tileConvertor.tileOf(coordinate);
	}

	public void setTileConvertor(TileConverter tileConvertor)
	{
		this.tileConvertor = tileConvertor;
	}
}
