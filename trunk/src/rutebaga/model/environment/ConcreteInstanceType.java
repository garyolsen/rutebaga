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
	private String name;
	private String[] initialFlags;
	private double mass;
	
	public String[] getInitialFlags()
	{
		return initialFlags;
	}

	public void setInitialFlags(String[] initialFlags)
	{
		this.initialFlags = initialFlags;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
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
		instance.setName(name);
		
		if (movementAttributes != null)
		{
			instance.setMovementAttributes(movementAttributes);
		}
		else
		{
			instance.setMovementAttributes(new MovementAttributes(false));
		}
		if (definition != null)
			instance.setAppearanceManager(definition.make(instance));
	
		if(initialFlags != null)
		{
			for(String flag : initialFlags)
			{
				instance.setFlag(flag, true);
			}
		}
	}
	
	public double getMass() {
		return mass;
	}

	public void setMass(Double mass) {
		this.mass = mass;
	}

}
