package rutebaga.model;

import rutebaga.model.environment.Instance;

public interface Targetable<T extends Instance>
{
	void setTarget(T t);
}
