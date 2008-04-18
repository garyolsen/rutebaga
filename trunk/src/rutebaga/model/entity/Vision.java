package rutebaga.model.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.IntVector2D;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;

public class Vision
{

	// TODO make a movement listener of the entity

	private Map<IntVector2D, Set<Memory>> memorySet = new HashMap<IntVector2D, Set<Memory>>();
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

	public Map<IntVector2D, Set<Memory>> getMemory()
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
			return;
		}

		// insert what we last saw into memory

		Map<IntVector2D, Set<Memory>> lastSaw = new HashMap<IntVector2D, Set<Memory>>();

		for (Instance instance : getActiveSet())
		{
			IntVector2D tile = instance.getTile();
			Set<Memory> tileMemory = lastSaw.get(instance.getTile());
			if (tileMemory == null)
			{
				tileMemory = new HashSet<Memory>();
				lastSaw.put(tile, tileMemory);
			}
			tileMemory.add(new Memory(instance));
		}

		memorySet.putAll(lastSaw);
	}
}
