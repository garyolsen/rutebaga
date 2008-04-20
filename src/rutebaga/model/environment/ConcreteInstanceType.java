package rutebaga.model.environment;

import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.scaffold.builders.AppearanceDefFactory;

/**
 * @author Gary
 * 
 * @param <T>
 *            MUST be a wildcard when the client instantiates
 */
public abstract class ConcreteInstanceType<T extends Instance> implements
		InstanceType<T>
{
	private MovementAttributes movementAttributes = new MovementAttributes();
	private AppearanceManagerDefinition definition;

	public AppearanceManagerDefinition getAppearanceDefinition()
	{
		return definition;
	}

	public void setAppearanceDefinition(AppearanceManagerDefinition definition)
	{
		this.definition = definition;
	}

	public MovementAttributes getMovementAttributes()
	{
		return movementAttributes;
	}

	public final T makeInstance()
	{
		T rval = create();
		initialize(rval);
		return rval;
	}

	public void setMovementAttributes(MovementAttributes movementAttributes)
	{
		this.movementAttributes = movementAttributes;
	}

	/**
	 * This MUST be overridden by subclasses to guarantee type safety.
	 * 
	 * @return
	 */
	protected abstract T create();

	protected void initialize(T instance)
	{
		System.out.println(movementAttributes);
		if (movementAttributes != null)
		{
			System.out.println("Setting with " + movementAttributes);
			instance.setMovementAttributes(movementAttributes);
		}
		else
		{
			System.out.println("No attr present");
			instance.setMovementAttributes(new MovementAttributes(false));
		}
		if (definition != null)
			instance.setAppearanceManager(definition.make(instance));
	}

}
