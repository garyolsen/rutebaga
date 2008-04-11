package rutebaga.commons;

public class StaticRule<T> implements Rule<T>
{
	private final boolean value;

	public StaticRule(boolean value)
	{
		this.value = value;
	}

	public boolean isValue()
	{
		return value;
	}

	public boolean determine(T context)
	{
		return value;
	}

}
