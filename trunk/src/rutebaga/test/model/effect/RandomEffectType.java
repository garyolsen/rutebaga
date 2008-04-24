package rutebaga.test.model.effect;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Damage;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.ConcreteInstanceType;

public class RandomEffectType extends ConcreteInstanceType<RandomEffect>
{
	private int lifetime = 100;
	private Damage damage;
	private double damageAmount;
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
		RandomEffect rval = new RandomEffect(speed, null);
		rval.setLifetime(lifetime);
		rval.setDamageAmount(damageAmount);
		rval.setDamageToApply(damage);
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

	public Damage getDamage()
	{
		return damage;
	}

	public void setDamage(Damage damage)
	{
		this.damage = damage;
	}

	public double getDamageAmount()
	{
		return damageAmount;
	}

	public void setDamageAmount(double damageAmount)
	{
		this.damageAmount = damageAmount;
	}
}
