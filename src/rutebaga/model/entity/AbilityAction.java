package rutebaga.model.entity;

public interface AbilityAction<T>
{
	void act(Ability<? extends T> ability, T target);
}
