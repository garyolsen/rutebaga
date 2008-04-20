package rutebaga.model.effect;

import java.util.ArrayList;
import java.util.List;

import rutebaga.commons.math.Bounds2D;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;

public class AreaEffectType extends ConcreteInstanceType<AreaEffect>
{
	private List<EntityEffect> effects = new ArrayList<EntityEffect>();
	private int blockingRate;
	private Bounds2D bounds;
	private Appearance[] appearances;
	private int frameWait;

	public Appearance[] getAppearances()
	{
		return appearances;
	}

	public void setAppearances(Appearance[] appearances)
	{
		this.appearances = appearances;
	}

	public int getFrameWait()
	{
		return frameWait;
	}

	public void setFrameWait(int frameWait)
	{
		this.frameWait = frameWait;
	}

	public List<EntityEffect> getEffects()
	{
		return effects;
	}

	public int getBlockingRate()
	{
		return blockingRate;
	}

	public Bounds2D getBounds()
	{
		return bounds;
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
	protected AreaEffect create()
	{
		return new AreaEffect(this);
	}

	@Override
	protected void initialize(AreaEffect instance)
	{
		super.initialize(instance);
		instance.getEffects().addAll(effects);
		instance.setBlockingRate(blockingRate);
		if (bounds != null)
			instance.setBounds(bounds);
		instance.setAppearanceManager(new AnimatedAppearanceManager(
				appearances, frameWait));
		instance.blackListTerrainTypes();
	}
}
