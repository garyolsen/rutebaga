package rutebaga.model.environment;

public interface InstanceType<T extends Instance>
{
	T makeInstance();
}
