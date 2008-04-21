package rutebaga.model.entity.ability;

import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;

public class EntityEffectAction implements AbilityAction
{
	private boolean actOnTarget = false;
	private EntityEffect effect;

	public boolean isActOnTarget()
	{
		return actOnTarget;
	}

	public void setActOnTarget(boolean actOnTarget)
	{
		this.actOnTarget = actOnTarget;
	}

	public EntityEffect getEffect()
	{
		return effect;
	}

	public void setEffect(EntityEffect effect)
	{
		this.effect = effect;
	}

	public void act(Ability ability, Object target)
	{
		if(!actOnTarget)
			ability.getEntity().accept(effect);
		else
			((Entity) target).accept(effect);
	}

}
