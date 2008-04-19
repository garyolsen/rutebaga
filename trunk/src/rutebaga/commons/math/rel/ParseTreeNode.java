package rutebaga.commons.math.rel;

public abstract class ParseTreeNode<T>
{
	private T value;

	public ParseTreeNode(T value)
	{
		super();
		this.value = value;
	}

	public T getValue()
	{
		return value;
	}

	public void setValue(T value)
	{
		this.value = value;
	}

	public abstract void accept(ParseTreeVisitor v);
}
