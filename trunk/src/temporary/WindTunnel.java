package temporary;

import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.Vector;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public class WindTunnel extends Instance
{
	private BoundsTracker tracker;

	private int direction = 0;
	private int wait = 0;

	public WindTunnel()
	{
		tracker = new BoundsTracker(new EllipseBounds2D(new Vector2D(0, 0),
				new Vector2D(2, 2)), this);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	@Override
	public double getLayer()
	{
		return DefaultLayers.AIR.getLayer();
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
				instance.applyMomentum(new Vector2D(0.3, 0));
			}
		}

		double dx = direction % 2;
		double dy = direction % 2 + 1;
		boolean negative = (direction / 2) % 2 == 0;
		Vector2D impulse = new Vector2D(dx * 0.05, dy * 0.05);
		if (negative)
			impulse = impulse.opposite();

		this.applyImpulse(impulse);

		wait++;
		if (wait % 10 == 0)
			direction++;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.EFFECT;
	}
}
