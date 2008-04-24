package rutebaga.test.model.effect;

import java.util.Set;

import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.DefaultLayers;
import rutebaga.model.effect.TargetableEffect;
import rutebaga.model.entity.Damage;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.PlayerEffectSource;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public class RandomEffect extends TargetableEffect<RandomEffect, Instance>
{

	public RandomEffect(ValueProvider<? super RandomEffect> impulse, Entity entity)
	{
		super(null, impulse);
		this.source = entity;
	}

	private Damage damageToApply;
	private double damageAmount;
	private Entity source;

	public Damage getDamageToApply()
	{
		return damageToApply;
	}

	public void setDamageToApply(Damage damageToApply)
	{
		this.damageToApply = damageToApply;
	}

	public double getDamageAmount()
	{
		return damageAmount;
	}

	public void setDamageAmount(double damageAmount)
	{
		this.damageAmount = damageAmount;
	}

	@Override
	protected void tickLogic()
	{
		Set<Instance> set = getCoexistantInstances();
		if (set.contains(getTarget()))
		{
			Instance target = getTarget();
			if (target.getSetIdentifier().equals(InstanceSetIdentifier.ENTITY))
			{
				MutableVector2D direction = new MutableVector2D(target.getCoordinate().getX(), target.getCoordinate()
						.getY());
				direction.detract(this.getCoordinate());
				direction.multiplyBy((3 - direction.getMagnitude()) / 20);
				target.applyMomentum(direction);
				if (damageToApply != null)
				{
					damageToApply.execute((Entity) target, damageAmount, new PlayerEffectSource(source));
				}
			}
		}
	}

	@Override
	public double getLayer()
	{
		return DefaultLayers.AIR.getLayer();
	}

	@Override
	public double getMass()
	{
		return 1.0;
	}

	@Override
	public void setMass(double mass)
	{
		// does nothing
	}

	public Entity getSource()
	{
		return source;
	}

	public void setSource(Entity source)
	{
		this.source = source;
	}

}
