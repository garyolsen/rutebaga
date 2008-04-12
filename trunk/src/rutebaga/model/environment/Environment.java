package rutebaga.model.environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.model.environment.InternalContainer.Location;
import rutebaga.model.environment.InternalContainer.PhysicsContainer;

public class Environment
{
	private Set<Instance> instances = new InstanceSet();
	private Map<Vector, InstanceSet> tileCache = new HashMap<Vector, InstanceSet>();
	private Map<Instance, Vector> reverseTileCache = new HashMap<Instance, Vector>();
	private TileConvertor tileConvertor;

	public Environment(TileConvertor convertor)
	{
		this.tileConvertor = convertor;
	}
	
	public Set<Instance> getInstances()
	{
		return Collections.unmodifiableSet(instances);
	}

	public boolean add(Instance instance, Vector coordinate)
	{
		if(instances.contains(instance))
		{
			//TODO should this throw an exception instead?
			return false;
		}
		
		Vector tile = tileConvertor.tileOf(coordinate);
		if(blocked(instance, tile))
			return false;
		
		// everything is okay -- first remove from old environment
		if(instance.getEnvironment() != null)
		{
			//note: not LOD violation-- getEnvironment()
			//returns an instance of this type
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

	public InstanceSet instancesAt(Vector tile)
	{
		InstanceSet set = tileCache.get(tile);
		if(set == null)
		{
			set = new InstanceSet();
			tileCache.put(tile, set);
		}
		return set;
	}

	public void remove(Instance instance)
	{
		instances.remove(instance);
		Vector tile = reverseTileCache.remove(instance);
		instancesAt(tile).remove(instance);
		instance.setLocation(null);
	}
	
	public void tick()
	{
		updatePhysics();
		performMovement();
	}
	
	protected boolean blocked(Instance instance, Vector tile)
	{
		InstanceSet instances = tileCache.get(tile);
		if(instances == null) return false;
		for(Instance other : instances)
		{
			if(other != instance && (other.blocks(instance) || instance.blocks(other)))
				return true;
		}
		return false;
	}
	
	protected double frictionAt(Vector tile)
	{
		double rval = 0;
		for(Instance instance : instancesAt(tile))
		{
			rval += instance.getFriction();
		}
		return rval;
	}
	
	protected Map<Vector, InstanceSet> getTileCache()
	{
		return tileCache;
	}
	
	protected void performMovement()
	{
		for(Instance instance : instances)
		{
			Vector velocity = instance.getVelocity();
			Vector newCoordinate = instance.getCoordinate().plus(velocity);
			Vector newTile = tileConvertor.tileOf(newCoordinate);
			if(blocked(instance, newTile))
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
	
	protected void updatePhysics()
	{
		for(Vector tile : tileCache.keySet())
		{
			double friction = frictionAt(tile);
			for(Instance instance : instancesAt(tile))
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

			InstanceSet newInstances = instancesAt(newTile);
			newInstances.add(instance);
		}
	}
}
