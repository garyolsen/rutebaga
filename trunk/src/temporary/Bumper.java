package temporary;

import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.Vector;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public class Bumper extends Instance
{
	private BoundsTracker tracker;

	public Bumper()
	{
		super(null);
		tracker = new BoundsTracker(new EllipseBounds2D(new Vector2D(0, 0),
				new Vector2D(2, 2)), this);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	@Override
	public double getMass()
	{
		return 1;
	}

	@Override
	public void tick()
	{
		for (Instance instance : tracker.getInstances())
		{
			if (instance instanceof Entity)
			{
				Vector2D distanceTo = instance.getCoordinate().minus(
						this.getCoordinate());
				instance.applyMomentum(distanceTo);
			}
		}
	}

	@Override
	public double getLayer()
	{
		return DefaultLayers.AIR.getLayer();
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.EFFECT;
	}

}
