package rutebaga.model.entity.ability;

import rutebaga.model.Targetable;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceType;

public class SpawnAction<T extends Instance, U extends Instance & Targetable<T>>
		implements AbilityAction<T>
{
	private InstanceType<U> type;

	public SpawnAction(InstanceType<U> type)
	{
		super();
		this.type = type;
	}

	public void act(Ability<? extends T> ability, T target)
	{
		System.out.println("EXECUTING!!!");
		U instance = type.makeInstance();
		instance.setTarget(target);
		Environment e = ability.getEnvironment();
		// XXX LoD violation
		e.add(instance, ability.getCoordinate());
	}

}
