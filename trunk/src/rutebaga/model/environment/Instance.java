package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.model.environment.InternalContainer.Location;
import rutebaga.model.environment.InternalContainer.PhysicsContainer;

public abstract class Instance
{
	private Location location;
	private PhysicsContainer physicsContainer;

	public void applyImpulse(Vector impulse)
	{
		this.physicsContainer.applyImpulse(impulse);
	}

	public void applyMomentum(Vector momentum)
	{
		this.physicsContainer.applyMomentum(momentum);
	}

	public abstract boolean blocks(Instance other);
	
	public Set<Instance> getCoexistantInstances()
	{
		Set<Instance> rval = new HashSet<Instance>();
		rval.addAll(getEnvironment().instancesAt(this.getTile()));
		rval.remove(this);
		return rval;
	}
	
	public Vector getCoordinate()
	{
		return location.getCoordinate();
	}

	public Environment getEnvironment()
	{
		return location == null ? null : location.getEnvironment();
	}

	public abstract double getFriction();

	public abstract double getMass();
	
	public Vector getTile()
	{
		return location.getTile();
	}

	protected Location getLocation()
	{
		return location;
	}
	
	protected PhysicsContainer getPhysicsContainer()
	{
		return physicsContainer;
	}
	protected Vector getVelocity()
	{
		return this.physicsContainer.getVelocity();
	}
	protected void setLocation(Location location)
	{
		this.location = location;
	}
	
	protected void setPhysicsContainer(PhysicsContainer physicsContainer)
	{
		this.physicsContainer = physicsContainer;
	}
}
