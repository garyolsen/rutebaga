package rutebaga.appearance;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.appearance.AppearanceManager;

public interface AppearanceManagerDefinition<T extends Instance>
{
	AppearanceManager make(T instance);
}
