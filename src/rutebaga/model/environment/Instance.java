package rutebaga.model.environment;

import rutebaga.commons.Vector;
import rutebaga.model.environment.InternalContainer.Location;
import rutebaga.model.environment.InternalContainer.PhysicsContainer;

public abstract class Instance
{
	private Location location;
	private PhysicsContainer physicsContainer;

	public Vector getCoordinate()
	{
		return location.getCoordinate();
	}

	public Environment getEnvironment()
	{
		return location == null ? null : location.getEnvironment();
	}

	public Vector getTile()
	{
		return location.getTile();
	}
	
	public void applyImpulse(Vector impulse)
	{
		this.physicsContainer.applyImpulse(impulse);
	}
	
	public void applyMomentum(Vector momentum)
	{
		this.physicsContainer.applyMomentum(momentum);
	}

	protected Location getLocation()
	{
		return location;
	}

	protected PhysicsContainer getPhysicsContainer()
	{
		return physicsContainer;
	}

	protected void setLocation(Location location)
	{
		this.location = location;
	}
	
	protected Vector getVelocity()
	{
		return this.physicsContainer.getVelocity();
	}

	protected void setPhysicsContainer(PhysicsContainer physicsContainer)
	{
		this.physicsContainer = physicsContainer;
	}
	
	public abstract boolean blocks(Instance other);
	public abstract double getMass();
	public abstract double getFriction();
}
