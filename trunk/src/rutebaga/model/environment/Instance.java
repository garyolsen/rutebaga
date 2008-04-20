 package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.ObjectUtils;
import rutebaga.commons.UIDProvider;
import rutebaga.commons.math.GenericVector2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InternalContainer.Location;
import rutebaga.model.environment.InternalContainer.PhysicsContainer;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.model.map.TerrainType;

/**
 * A physical instance within a tile-space.
 * 
 * When the instance is not within an environment, it is an orphan. Spatial and
 * Newtonian operations are not guaranteed to be stable when invoked upon an
 * orphan.
 * 
 * @author Gary LosHuertos
 * 
 */
public abstract class Instance<T extends Instance<T>> implements Layerable, Locatable
{
	private long id = UIDProvider.getLongUID();
	
	private InstanceType<T> type;

	private Location location;
	private PhysicsContainer physicsContainer;
	private MovementAttributes movementAttributes = new MovementAttributes();
	private AppearanceManager appearanceManager;
	private double friction = 0;
	private boolean tickable = true;
	private Set<MovementListener> movementListeners = new HashSet<MovementListener>();

	public Instance(InstanceType<T> type)
	{
		this.type = type;
	}

	/**
	 * Checks to see whether an instance is allowed to be over a terrain type.
	 * 
	 * @param terrain
	 *            The TerrainType in question.
	 * @return Boolean True if this Instance can cross over the specified
	 *         TerrainType.
	 */
	public Boolean able(TerrainType terrain)
	{
		return movementAttributes.able(terrain);
	}

	/**
	 * Applies a viscous (transient) impulse to this instance.
	 * 
	 * @param impulse
	 *            the impulse to be applied
	 */
	public void applyImpulse(GenericVector2D impulse)
	{
		this.physicsContainer.applyImpulse(impulse);
	}

	/**
	 * Applies an impulse to this Instance's (persistent) momentum.
	 * 
	 * @param momentum
	 *            the impulse to be added to the momentum
	 */
	public void applyMomentum(GenericVector2D momentum)
	{
		this.physicsContainer.applyMomentum(momentum);
	}

	/**
	 * Determines whether or not this Instance is capable of blocking an
	 * Instance (regardless of its location) were it to attempt to move onto
	 * this Instance's tile.
	 * 
	 * @param other
	 *            the instance requesting access
	 * @return whether or not access is blocked
	 */
	public abstract boolean blocks(Instance other);

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Instance other = (Instance) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean existsInUniverse()
	{
		return this.getLocation() != null
				&& this.getLocation().getEnvironment() != null;
	}

	/**
	 * Returns the Appearance of this Instance.
	 * 
	 * @return The Appearance that describes this Instance.
	 */
	public Appearance getAppearance()
	{
		if ( appearanceManager == null )
			return new Appearance(null);
		
		return appearanceManager.getAppearance();
	}

	public AppearanceManager getAppearanceManager()
	{
		return appearanceManager;
	}

	/**
	 * Returns other Instances on the same game tile.
	 * 
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
	 * 
	 * @return the coordinate of this instance in space
	 */
	public Vector2D getCoordinate()
	{
		return location == null ? null : location.getCoordinate();
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
	public final double getFriction()
	{
		return this.getPhysicsContainer().getFriction();
	}

	public abstract double getLayer();

	/**
	 * @return the mass of this instance
	 */
	public abstract double getMass();

	public Vector2D getMomentum()
	{
		return physicsContainer.getMomentum();
	}

	/**
	 * @return the terrain movement attributes of this instance
	 */
	public MovementAttributes getMovementAttributes()
	{
		return movementAttributes;
	}

	public Orientation getOrientation()
	{
		return getAppearance().getOrientation();
	}

	public abstract InstanceSetIdentifier getSetIdentifier();

	/**
	 * @return the coordinate of this instance in tile-space
	 */
	public IntVector2D getTile()
	{
		return location == null ? null : location.getTile();
	}

	public InstanceType<T> getType()
	{
		return type;
	}

	/**
	 * @return the (instantaneous) velocity of this instance
	 */
	public Vector2D getVelocity()
	{
		return this.physicsContainer.getVelocity();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public final void instanceTickOps()
	{
		if(appearanceManager != null)
			appearanceManager.tick();
	}

	public boolean isMobile()
	{
		return true;
	}

	public void registerMovementListener(MovementListener listener)
	{
		this.movementListeners.add(listener);
	}

	public boolean sameTypeAs(Instance other)
	{
		return ObjectUtils.equals(this.type, other.type);
	}

	public void setAppearance(Appearance appearance)
	{
		this.appearanceManager = new StaticAppearanceManager(appearance);
	}
	
	public void setAppearanceManager(AppearanceManager appearanceManager)
	{
		this.appearanceManager = appearanceManager;
	}

	public void setMovementAttributes(MovementAttributes movementAttributes)
	{
		this.movementAttributes = movementAttributes;
	}
	
	/**
	 * This method permanently adds the passed TerrainTypes to
	 * this Instance's WhiteList.
	 * 
	 * @param terrains
	 */
	public void whiteListTerrainTypes(TerrainType... terrains)
	{
		MovementAttributeSet moveSet = new WhiteList();
		for (TerrainType terrain : terrains)
		{
			moveSet.add(terrain);
		}
		movementAttributes.add(moveSet);
	}
	
	public Object whiteListTerrainTypes(MovementAttributeSet moveSet)
	{
		Object token = UIDProvider.getUID();
		movementAttributes.add(moveSet,token);
		return token;
	}
	
	public void blackListTerrainTypes(TerrainType... terrains)
	{
		MovementAttributeSet moveSet = new BlackList();
		for (TerrainType terrain : terrains)
		{
			moveSet.add(terrain);
		}
		movementAttributes.add(moveSet);
	}
	
	public Object blackListTerrainTypes(MovementAttributeSet moveSet)
	{
		Object token = UIDProvider.getUID();
		movementAttributes.add(moveSet,token);
		return token;
	}

	public abstract void tick();

	public void unregisterMovementListener(MovementListener listener)
	{
		this.movementListeners.remove(listener);
	}
	
	private void updateTickability()
	{
		if(tickable)
		{
			getEnvironment().getTickableInstances().add(this);
		}
		else
		{
			getEnvironment().getTickableInstances().remove(this);
		}
	}
	
	/**
	 * @return the location container for this instance
	 */
	protected Location getLocation()
	{
		return location;
	}

	protected Set<MovementListener> getMovementListeners()
	{
		return movementListeners;
	}

	/**
	 * @return the physics container for this instance
	 */
	protected PhysicsContainer getPhysicsContainer()
	{
		return physicsContainer;
	}
	
	protected final void setFriction(double friction)
	{
		this.friction = friction;
		if (this.physicsContainer != null)
			physicsContainer.setFriction(friction);
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
		if(location == null) return;
		if(tickable)
		{
			getEnvironment().getTickableInstances().add(this);
		}
		else
		{
			getEnvironment().getTickableInstances().remove(this);
		}
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
		physicsContainer.setFriction(friction);
	}
	
	protected final void setTickable(boolean tickable)
	{
		this.tickable = tickable;
		if(getEnvironment() != null)
		{
			updateTickability();
		}
	}
	
	public boolean isLike(Instance other)
	{
		return ObjectUtils.equals(this.type, other.type);
	}

}
