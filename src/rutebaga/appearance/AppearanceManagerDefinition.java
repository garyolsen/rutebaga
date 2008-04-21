package rutebaga.appearance;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.appearance.AppearanceManager;

/**
 *	Knows how to construct an Appearance manager for the paramaterized Instance.
 * @param <T>
 */
public interface AppearanceManagerDefinition<T extends Instance>
{
	/**
	 * @param instance The Instance to be represented by the returned AppearanceManager.
	 * @return A new AppearanceManager for the provided instance.
	 */
	AppearanceManager make(T instance);
}
