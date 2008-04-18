package rutebaga.test.model.effect;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.DefaultLayers;
import rutebaga.model.effect.TargetableEffect;
import rutebaga.model.environment.Instance;

public class RandomEffect extends TargetableEffect<RandomEffect, Instance>
{

	public RandomEffect(
			ValueProvider<? super RandomEffect> impulse)
	{
		super(null, impulse);
	}

	@Override
	protected void tickLogic()
	{
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

}
