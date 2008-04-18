package rutebaga.model.effect;

import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.Targetable;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public abstract class TargetableEffect<Self extends TargetableEffect<Self, TargetType>, TargetType extends Instance>
		extends Instance implements Targetable<TargetType>
{
	private ValueProvider<? super Self> impulse;
	private TargetType target;
	private int lifetime;

	public int getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}

	public TargetableEffect(
			ValueProvider<? super Self> impulse)
	{
		super();
		this.impulse = impulse;
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.EFFECT;
	}

	public void setTarget(TargetType target)
	{
		this.target = target;
	}

	@Override
	public final void tick()
	{
		lifetime--;
		if(lifetime == 0)
		{
			this.getEnvironment().remove(this);
			return;
		}
		double impulseMag = impulse.getValue((Self) this);
		MutableVector2D direction = new MutableVector2D(target.getCoordinate());
		direction.detract(this.getCoordinate());
		direction.becomeUnitVector();
		direction.multiplyBy(impulseMag);
		applyImpulse(direction);
		tickLogic();
	}

	protected abstract void tickLogic();
}
