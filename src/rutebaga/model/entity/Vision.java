package rutebaga.model.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;

public class Vision
{

	// TODO make a movement listener of the entity

	private Set<Instance> previousActiveSet = new HashSet<Instance>();
	private Map<Vector, Set<Memory>> memorySet = new HashMap<Vector, Set<Memory>>();
	private BoundsTracker boundsTracker;
	private Entity entity;

	public Vision(Entity entity)
	{
		this.entity = entity;
		boundsTracker = new BoundsTracker(entity.getVisionBounds(), entity);
	}

	public Set<Instance> getActiveSet()
	{
		return boundsTracker.getInstances();
	}

	public Map<Vector, Set<Memory>> getMemory()
	{
		return memorySet;
	}
	
	public boolean inActiveSet(Instance i) {
		return boundsTracker.getInstances().contains(i);
	}

	public final void tick()
	{

		if (!entity.existsInUniverse())
		{
			memorySet.clear();
			previousActiveSet.clear();
			return;
		}

		// insert what we last saw into memory

		Map<Vector, Set<Memory>> lastSaw = new HashMap<Vector, Set<Memory>>();

		for (Instance instance : previousActiveSet)
		{
			Vector tile = instance.getTile();
			Set<Memory> tileMemory = lastSaw.get(instance.getTile());
			if (tileMemory == null)
			{
				tileMemory = new HashSet<Memory>();
				lastSaw.put(tile, tileMemory);
			}
			tileMemory.add(new Memory(instance));
		}

		memorySet.putAll(lastSaw);

		// Set<Vector> tilesInMemory = memorySet.keySet();
		// Set<Vector> memoryInSight =
		// entity.getVisionBounds().filter(tilesInMemory, entity.getTile());
		// tilesInMemory.removeAll(memoryInSight);

		// update what we will have seen with the current set

		previousActiveSet = getActiveSet();
	}
}
