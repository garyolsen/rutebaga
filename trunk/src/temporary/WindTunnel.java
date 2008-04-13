package temporary;

import rutebaga.commons.EllipseBounds;
import rutebaga.commons.Vector;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;

public class WindTunnel extends Instance
{
	private BoundsTracker tracker;
	
	public WindTunnel()
	{
		tracker = new BoundsTracker(new EllipseBounds(new Vector(0, 0), new Vector(5, 5)), this);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	@Override
	public double getFriction()
	{
		return 0;
	}

	@Override
	public double getMass()
	{
		return 1;
	}

	@Override
	public void tick()
	{
		for(Instance instance : tracker.getInstances())
		{
			if(instance instanceof Entity)
			{
				instance.applyMomentum(new Vector(0.3, 0));
			}
		}
	}

}
