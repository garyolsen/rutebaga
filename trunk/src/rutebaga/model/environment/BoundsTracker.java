package rutebaga.model.environment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.Bounds;
import rutebaga.commons.Vector;

public class BoundsTracker implements MovementListener
{
	private Environment monitoredEnvironment;

	private Bounds bounds;
	private Instance monitoredInstance;
	private Set<Vector> tilesWithinBounds;

	private Set<Instance> insideInstances = new HashSet<Instance>();

	public BoundsTracker(Bounds bounds, Instance instance,
			Environment environment)
	{
		this.bounds = bounds;
		this.monitoredInstance = instance;
		this.monitoredEnvironment = environment;
		environment.registerMovementListener(this);
		tilesWithinBounds = bounds.locationSet(1.0);
		recheck();
	}

	public void onMovement(MovementEvent event)
	{
		// System.out.println("[BoundsTracker] Received event: " +
		// event.getInstance());
		Instance movedInstance = event.getInstance();
		// first determine if the instance moved
		if (event.getInstance() != monitoredInstance)
		{
			// System.out.println("[BoundsTracker] Not the monitored instance");
			// check if the instance is inside
			boolean inside = checkInstance(movedInstance);
			// System.out.println("[BoundsTracker] " + (inside ? "Inside" : "Not
			// inside"));
			if (insideInstances.contains(movedInstance))
			{
				// System.out.println("[BoundsTracker] Moved out of
				// boundaries");
				// the instance has been moved out of the boundaries
				if (!inside)
					insideInstances.remove(movedInstance);
			}
			else
			{
				// System.out.println("[BoundsTracker] Moved into boundaries");
				// the instance has moved into the boundaries
				if (inside)
					insideInstances.add(movedInstance);
			}
		}
		else
		{
			// re-evaluate the instances with the bounds
			recheck();
		}
	}

	private void recheck()
	{
		// System.out.println("[BoundsTracker] Performing recheck");
		insideInstances.clear();
		for (Vector tile : tilesWithinBounds)
		{
			insideInstances.addAll(monitoredEnvironment.instancesAt(tile.plus(monitoredInstance.getTile())));
		}
		insideInstances.remove(monitoredInstance);
	}

	private boolean checkInstance(Instance instance)
	{
		Vector location = instance.getTile();
		Vector offset = this.monitoredInstance.getTile();
		// System.out.println("[BoundsTracker] Looking for " +
		// location.minus(offset));
		return tilesWithinBounds.contains(location.minus(offset));
	}

	public Set<Instance> getInstances()
	{
		return Collections.unmodifiableSet(insideInstances);
	}
}
