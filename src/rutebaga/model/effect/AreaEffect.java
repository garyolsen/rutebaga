package rutebaga.model.effect;

import java.util.ArrayList;
import java.util.List;

import rutebaga.commons.logic.ChainedRule;
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
	private ChainedRule<Entity> rules = new ChainedRule<Entity>(true);
	private ChainedRule<AreaEffect> activeRules = new ChainedRule<AreaEffect>(true);

	public AreaEffect(InstanceType<AreaEffect> type)
	{
		super(type);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	public ChainedRule<AreaEffect> getActiveRules()
	{
		return activeRules;
	}

	public int getBlockingRate()
	{
		return blockingRate;
	}

	public List<EntityEffect> getEffects()
	{
		return effects;
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

	public ChainedRule<Entity> getRules()
	{
		return rules;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.EFFECT;
	}

	@Override
	public boolean isMobile()
	{
		return false;
	}

	public void setBlockingRate(int blockingRate)
	{
		this.blockingRate = blockingRate;
	}

	public void setBounds(Bounds2D bounds)
	{
		boundsTracker = new BoundsTracker(bounds, this);
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
			if (rules.determine(entity))
				for (EntityEffect effect : effects)
				{
					entity.accept(effect);
				}
		}
	}

	@Override
	public void setMass(double mass) {
		// area effects have infinite mass
	}

}
