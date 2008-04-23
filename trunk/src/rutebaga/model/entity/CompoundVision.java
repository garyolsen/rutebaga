package rutebaga.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.IntVector2D;
import rutebaga.model.environment.Instance;

public class CompoundVision extends Vision
{
	private Vision primary;
	private List<Vision> external = new ArrayList<Vision>();
	
	public CompoundVision(Vision primary)
	{
		this.primary = primary;
	}
	
	public void add(Vision vision)
	{
		this.external.add(vision);
	}
	
	public void remove(Vision vision)
	{
		this.external.remove(vision);
	}

	@Override
	public Set<Instance> getActiveSet()
	{
		Set<Instance> active = new HashSet<Instance>();
		active.addAll(primary.getActiveSet());
		for(Vision vision : external)
		{
			active.addAll(vision.getActiveSet());
		}
		return active;
	}

	@Override
	public Map<IntVector2D, Set<Memory>> getMemory()
	{
		return primary.getMemory();
	}
}
