package rutebaga.model.entity.ability;

import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.EffectSource;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.PlayerEffectSource;

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
		EffectSource source = new PlayerEffectSource(ability.getEntity());
		if(!actOnTarget)
			ability.getEntity().accept(effect, source);
		else
			((Entity) target).accept(effect, source);
	}

}
