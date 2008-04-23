package rutebaga.model.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.IntVector2D;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;

public abstract class Vision
{

	public abstract Set<Instance> getActiveSet();

	public abstract Map<IntVector2D, Set<Memory>> getMemory();
	
	public boolean inActiveSet(Instance i) {
		return getActiveSet().contains(i);
	}

	public final void tick()
	{
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

		getMemory().putAll(lastSaw);
	}
}
