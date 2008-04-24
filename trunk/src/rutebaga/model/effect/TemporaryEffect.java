package rutebaga.model.effect;

import rutebaga.model.Targetable;
import rutebaga.model.entity.EffectSource;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

public class TemporaryEffect extends Instance implements Targetable
{
	private Integer life;
	private ReversibleEntityEffect effect;
	private EntityEffect reverse;
	private EffectSource source;
	private Entity target;
	private int lifetime;

	public ReversibleEntityEffect getEffect()
	{
		return effect;
	}

	public void setEffect(ReversibleEntityEffect effect)
	{
		this.effect = effect;
	}

	public Entity getTarget()
	{
		return target;
	}

	public void setTarget(Entity target)
	{
		this.target = target;
	}

	public int getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}

	public TemporaryEffect(InstanceType type)
	{
		super(type);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	@Override
	public double getLayer()
	{
		return 0;
	}

	@Override
	public double getMass()
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMass(double mass)
	{

	}

	@Override
	public void tick()
	{
		if (life != null)
		{
			life--;

			if (life < 0)
			{
				target.accept(reverse, source);
				this.remove();
			}
		}
	}

	public void setTarget(Instance t)
	{
		target = (Entity) t;
	}

	public Object start()
	{
		if (life == null)
		{
			Object token = target.accept(effect, source);
			reverse = effect.getReverseEffect(token);
			life = lifetime;
			return token;
		}
		throw new RuntimeException();
	}

	public EffectSource getSource()
	{
		return source;
	}

	public void setSource(EffectSource source)
	{
		this.source = source;
	}

}
