package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.model.environment.InternalContainer.Location;
import rutebaga.model.environment.InternalContainer.PhysicsContainer;

/**
 * An physical instance within a tile-space.
 * 
 * When the instance is not within an environment, it is an orphan. Spatial and
 * Newtonian operations are not guaranteed to be stable when invoked upon an
 * orphan.
 * 
 * @author Gary LosHuertos
 * 
 */
public abstract class Instance
{
	private Location location;
	private PhysicsContainer physicsContainer;

	/**
	 * Applies a viscous (transient) impulse to this instance.
	 * 
	 * @param impulse
	 *            the impulse to be applied
	 */
	public void applyImpulse(Vector impulse)
	{
		this.physicsContainer.applyImpulse(impulse);
	}

	/**
	 * Applies an impulse to this instance's (persistent) momentum.
	 * 
	 * @param momentum
	 *            the impulse to be added to the momentum
	 */
	public void applyMomentum(Vector momentum)
	{
		this.physicsContainer.applyMomentum(momentum);
	}

	/**
	 * Determines whether or not this instance is capable of blocking an
	 * instance (regardless of its location) were it to attempt to move onto
	 * this instance's tile.
	 * 
	 * @param other
	 *            the instance requesting access
	 * @return whether or not access is blocked
	 */
	public abstract boolean blocks(Instance other);

	/**
	 * @return the set of the instances that share this instance's tile
	 */
	public Set<Instance> getCoexistantInstances()
	{
		Set<Instance> rval = new HashSet<Instance>();
		rval.addAll(getEnvironment().instancesAt(this.getTile()));
		rval.remove(this);
		return rval;
	}

	/**
	 * @return the coordinate of this instance in space
	 */
	public Vector getCoordinate()
	{
		return location.getCoordinate();
	}

	/**
	 * @return the environment that this instance is in (if any)
	 */
	public Environment getEnvironment()
	{
		return location == null ? null : location.getEnvironment();
	}

	/**
	 * Determines the space-friction that this instance donates to its tile.
	 * 
	 * @return friction coefficient donation
	 */
	public abstract double getFriction();

	/**
	 * @return the mass of this instance
	 */
	public abstract double getMass();

	/**
	 * @return the coordinate of this instance in tile-space
	 */
	public Vector getTile()
	{
		return location.getTile();
	}

	/**
	 * @return the (instantaneous) velocity of this instance
	 */
	public Vector getVelocity()
	{
		return this.physicsContainer.getVelocity();
	}

	/**
	 * @return the location container for this instance
	 */
	protected Location getLocation()
	{
		return location;
	}

	/**
	 * @return the physics container for this instance
	 */
	protected PhysicsContainer getPhysicsContainer()
	{
		return physicsContainer;
	}

	/**
	 * Sets the location container for this instance.
	 * 
	 * @param location
	 *            the new location
	 */
	protected void setLocation(Location location)
	{
		this.location = location;
	}

	/**
	 * Sets the physics container for this instance.
	 * 
	 * @param physicsContainer
	 *            the new container
	 */
	protected void setPhysicsContainer(PhysicsContainer physicsContainer)
	{
		this.physicsContainer = physicsContainer;
	}
}
