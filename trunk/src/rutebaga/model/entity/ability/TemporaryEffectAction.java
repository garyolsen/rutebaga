package rutebaga.model.entity.ability;

import com.sun.org.apache.xpath.internal.axes.ReverseAxesWalker;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.effect.TemporaryEffect;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;

public class TemporaryEffectAction implements AbilityAction
{
	private ReversibleEntityEffect effect;
	private int lifetime;

	public int getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}

	public ReversibleEntityEffect getEffect()
	{
		return effect;
	}

	public void setEffect(ReversibleEntityEffect effect)
	{
		this.effect = effect;
	}

	public void act(Ability ability, Object target)
	{
		TemporaryEffect effect = new TemporaryEffect(null);
		effect.setEffect(this.effect);
		effect.setLifetime(lifetime);
		effect.setTarget((Entity) target);
		ability.getEnvironment().add(effect, new Vector2D(0, 0));
	}

}
