package rutebaga.model.effect;

import java.util.ArrayList;
import java.util.List;

import rutebaga.commons.logic.ChainedRule;
import rutebaga.commons.math.Bounds2D;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;

public class AreaEffectType extends ConcreteInstanceType<AreaEffect>
{
	private List<EntityEffect> effects = new ArrayList<EntityEffect>();
	private int blockingRate;
	private Bounds2D bounds;
	private ChainedRule<Entity> rules = new ChainedRule<Entity>(true);
	private ChainedRule<AreaEffect> activeRules = new ChainedRule<AreaEffect>(true);

	public ChainedRule<AreaEffect> getActiveRules()
	{
		return activeRules;
	}

	public int getBlockingRate()
	{
		return blockingRate;
	}

	public Bounds2D getBounds()
	{
		return bounds;
	}

	public List<EntityEffect> getEffects()
	{
		return effects;
	}

	public ChainedRule<Entity> getRules()
	{
		return rules;
	}

	public void setActiveRules(ChainedRule<AreaEffect> activeRules)
	{
		this.activeRules = activeRules;
	}

	public void setBlockingRate(int blockingRate)
	{
		this.blockingRate = blockingRate;
	}

	public void setBounds(Bounds2D bounds)
	{
		this.bounds = bounds;
	}

	@Override
	public String toString()
	{
		return "AOE with blocking=" + blockingRate + " and " + effects.size() + " effects";
	}


	@Override
	protected AreaEffect create()
	{
		return new AreaEffect(this);
	}

	@Override
	protected void initialize(AreaEffect instance)
	{
		super.initialize(instance);
		
		instance.getEffects().addAll(effects);
		instance.getRules().addAll(rules.getRules());
		instance.getActiveRules().addAll(activeRules.getRules());
		
		if (bounds != null)
			instance.setBounds(bounds);
		instance.setBlockingRate(blockingRate);
	
		instance.blackListTerrainTypes();
	}
}
