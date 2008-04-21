package rutebaga.model.entity.ability;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.Targetable;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceType;

public class SpawnAction<T extends Instance, U extends Instance & Targetable<T>>
		implements AbilityAction<T>
{
	public static enum Location
	{
		SOURCE,
		TARGET;
	}

	public static enum TargetMethod
	{
		STATIC,
		FOLLOW;
	}

	private InstanceType<U> type;
	private Location location = Location.SOURCE;
	private TargetMethod targetMethod = TargetMethod.FOLLOW;

	public SpawnAction()
	{
		super();
	}

	public InstanceType<U> getType()
	{
		return type;
	}

	public void setType(InstanceType<U> type)
	{
		this.type = type;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public TargetMethod getTargetMethod()
	{
		return targetMethod;
	}

	public void setTargetMethod(TargetMethod targetMethod)
	{
		this.targetMethod = targetMethod;
	}

	public SpawnAction(InstanceType<U> type)
	{
		super();
		this.type = type;
	}

	public void act(Ability<? extends T> ability, T target)
	{
		rutebaga.commons.Log.log("EXECUTING!!!");
		U instance = type.makeInstance();
		if (targetMethod == TargetMethod.FOLLOW)
		{
			if (target == null)
				throw new RuntimeException("target is null");
			((Targetable) instance).setTarget(target);
		}
		Environment e = ability.getEnvironment();
		// XXX LoD violation
		Vector2D coordinate = null;
		switch(location)
		{
		case SOURCE:
			coordinate = ability.getCoordinate();
			break;
		case TARGET:
			coordinate = target.getCoordinate();
			break;
		}
		e.add(instance, coordinate);
	}

}
