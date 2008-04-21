package rutebaga.model.entity.ability;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Damage;
import rutebaga.model.entity.Entity;

/**
 * Performs an amount of damage on a target. The given value provider uses the
 * actor as its context.
 * 
 * @author Gary
 * 
 */
public class DamageAction implements AbilityAction<Entity>
{
	private ValueProvider<Entity> magnitudeProvider;
	private Damage damage;

	public DamageAction(ValueProvider<Entity> magnitudeProvider, Damage damage)
	{
		super();
		this.magnitudeProvider = magnitudeProvider;
		this.damage = damage;
	}

	public void act(Ability<? extends Entity> ability, Entity target)
	{
		double amount = magnitudeProvider.getValue(ability.getEntity());
		damage.execute(target, amount);
	}

	public Damage getDamage()
	{
		return damage;
	}

	public ValueProvider<Entity> getMagnitudeProvider()
	{
		return magnitudeProvider;
	}

	public void setDamage(Damage damage)
	{
		this.damage = damage;
	}

	public void setMagnitudeProvider(ValueProvider<Entity> magnitudeProvider)
	{
		this.magnitudeProvider = magnitudeProvider;
	}
}
