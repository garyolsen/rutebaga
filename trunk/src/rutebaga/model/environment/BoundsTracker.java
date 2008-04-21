package rutebaga.model.environment;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.ObjectUtils;
import rutebaga.commons.math.Bounds2D;
import rutebaga.commons.math.IntVector2D;

/**
 * Tracks the position of instances within a bounds centered upon an instance.
 * 
 * The use of BoundsTracker is much more efficient than querying an
 * <code>Environment<code>
 * continuously.
 * 
 * @author Gary LosHuertos
 * 
 * @see Environment
 *
 */
public class BoundsTracker implements MovementListener
{
	private Environment monitoredEnvironment;
	private Bounds2D bounds;

	private Instance<?> monitoredInstance;
	private Collection<IntVector2D> tilesWithinBounds;

	private boolean init = false;

	private Set<Instance> insideInstances = new HashSet<Instance>();

	/**
	 * Constructs a new BoundsTracker.
	 * 
	 * @param bounds
	 *            the bounds to monitor (centered upon the instance)
	 * @param instance
	 *            the instance to center upon
	 */
	public BoundsTracker(Bounds2D bounds, Instance<?> instance)
	{
		this.bounds = bounds;
		this.monitoredInstance = instance;
		instance.registerMovementListener(this);
	}

	private void init()
	{
		if (this.monitoredEnvironment != null)
		{
			monitoredEnvironment.unregisterMovementListener(this);
		}
		this.monitoredEnvironment = monitoredInstance.getEnvironment();
		if (monitoredEnvironment == null)
			return;
		this.monitoredEnvironment.registerMovementListener(this);
		tilesWithinBounds = bounds.intLocationSet();
		recheck();
		init = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.MovementListener#onMovement(rutebaga.model.environment.MovementEvent)
	 */
	public void onMovement(MovementEvent event)
	{
		if (!init)
			init();
		// FIXME not stable when the centered instance leaves the environment
		Instance movedInstance = event.getInstance();
		// first determine if the instance moved
		if (event.getInstance() != monitoredInstance)
		{
			// check if the instance is inside
			boolean inside = checkInstance(movedInstance);
			if (insideInstances.contains(movedInstance))
			{
				// the instance has been moved out of the boundaries
				if (!inside)
					insideInstances.remove(movedInstance);
			}
			else
			{
				// the instance has moved into the boundaries
				if (inside)
					insideInstances.add(movedInstance);
			}
		}
		else
		{
			if (!ObjectUtils.equals(monitoredEnvironment, movedInstance
					.getEnvironment()))
			{
				// the monitored instance has changed environments
				init();
			}
			// re-evaluate the instances with the bounds
			recheck();
		}
	}

	/**
	 * Refreshes the contents of the tracker directly from the environment.
	 */
	private void recheck()
	{
		insideInstances.clear();
		if(!monitoredInstance.existsInUniverse())
			return;
		for (IntVector2D tile : tilesWithinBounds)
		{
			insideInstances.addAll(monitoredEnvironment.instancesAt(tile
					.plus(monitoredInstance.getTile())));
		}
		insideInstances.remove(monitoredInstance);
	}

	/**
	 * Checks whether or not an instance is within the bounds.
	 * 
	 * @param instance
	 *            the instance to check
	 * @return true if the instance is within the bounds; false otherwise
	 */
	private boolean checkInstance(Instance<?> instance)
	{
		IntVector2D location = instance.getTile();
		if(location == null) return false;
		IntVector2D offset = this.monitoredInstance.getTile();
		return tilesWithinBounds.contains(location.minus(offset));
	}

	/**
	 * @return the instances contained within the tracked bounds
	 */
	public Set<Instance> getInstances()
	{
		if (!init)
			init();
		return Collections.unmodifiableSet(insideInstances);
	}
}
