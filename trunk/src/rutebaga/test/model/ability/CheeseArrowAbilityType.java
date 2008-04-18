package rutebaga.test.model.ability;


import rutebaga.model.entity.Ability;
import rutebaga.model.entity.ability.SpawnAction;
import rutebaga.model.environment.Instance;
import rutebaga.test.model.effect.RandomEffectType;

public class CheeseArrowAbilityType
{
	public Ability makeAbility()
	{
		Ability<Instance> ability = new Ability<Instance>();
		ability.addAction(new SpawnAction(new RandomEffectType()));
		return ability;
	}
}
