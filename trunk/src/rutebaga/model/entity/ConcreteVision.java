package rutebaga.model.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.Bounds2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;

public class ConcreteVision extends Vision
{

	// TODO make a movement listener of the entity

	private Map<IntVector2D, Set<Memory>> memorySet = new HashMap<IntVector2D, Set<Memory>>();
	private BoundsTracker boundsTracker;
	private Entity entity;

	public ConcreteVision(Entity entity, Bounds2D bounds)
	{
		this.entity = entity;
	}

	public Set<Instance> getActiveSet()
	{
		return boundsTracker.getInstances();
	}

	public Map<IntVector2D, Set<Memory>> getMemory()
	{
		return memorySet;
	}

	public void setBounds(Bounds2D visionBounds)
	{
		this.boundsTracker = new BoundsTracker(visionBounds, entity);
	}
}
