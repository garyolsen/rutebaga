package rutebaga.model.effect;

import java.util.ArrayList;
import java.util.List;

import rutebaga.commons.math.Bounds2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

public class AreaEffect extends Instance<AreaEffect>
{

	private List<EntityEffect> effects = new ArrayList<EntityEffect>();
	private int blockingRate;
	private int current = 0;
	private BoundsTracker boundsTracker;

	public AreaEffect(InstanceType<AreaEffect> type)
	{
		super(type);
	}

	public void setBounds(Bounds2D bounds)
	{
		boundsTracker = new BoundsTracker(bounds, this);
	}

	@Override
	public boolean isMobile()
	{
		return false;
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	@Override
	public double getLayer()
	{
		return DefaultLayers.SHADOW.getLayer();
	}

	@Override
	public double getMass()
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.EFFECT;
	}

	@Override
	public void tick()
	{
		current--;
		if (current < 0)
		{
			act();
			current = blockingRate;
		}
	}

	private void act()
	{
		InstanceSet set = new ConcreteInstanceSet();
		if (boundsTracker == null)
			set.addAll(this.getCoexistantInstances());
		else
			set.addAll(boundsTracker.getInstances());
		for (Entity entity : set.getEntities())
		{
			for (EntityEffect effect : effects)
			{
				entity.accept(effect);
			}
		}
	}

	public int getBlockingRate()
	{
		return blockingRate;
	}

	public void setBlockingRate(int blockingRate)
	{
		this.blockingRate = blockingRate;
	}

	public List<EntityEffect> getEffects()
	{
		return effects;
	}

}
