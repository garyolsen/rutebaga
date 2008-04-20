package rutebaga.model.entity;

public interface AbilityType<T extends Ability>
{
	T makeAbility();
}
