package rutebaga.test.model.effect;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.environment.ConcreteInstanceType;

public class RandomEffectType extends ConcreteInstanceType<RandomEffect>
{
	private int lifetime = 100;
	private ValueProvider<RandomEffect> speed;
	
	public int getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}
	
	@Override
	protected RandomEffect create()
	{
		RandomEffect rval = new RandomEffect(speed);
		rval.setLifetime(lifetime);
		return rval;
	}

	public ValueProvider<RandomEffect> getSpeed()
	{
		return speed;
	}

	public void setSpeed(ValueProvider<RandomEffect> speed)
	{
		this.speed = speed;
	}
}
