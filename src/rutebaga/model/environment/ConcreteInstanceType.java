package rutebaga.model.environment;

/**
 * @author Gary
 *
 * @param <T> MUST be a wildcard when the client instantiates
 */
public abstract class ConcreteInstanceType<T extends Instance> implements
		InstanceType<T>
{
	private MovementAttributes movementAttributes;

	public MovementAttributes getMovementAttributes()
	{
		return movementAttributes;
	}

	public T makeInstance()
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
		instance.setMovementAttributes(movementAttributes);
	}

}
