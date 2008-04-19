package rutebaga.model.environment;

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

	protected abstract T create();

	protected void initialize(T instance)
	{
		instance.setMovementAttributes(movementAttributes);
	}

}
