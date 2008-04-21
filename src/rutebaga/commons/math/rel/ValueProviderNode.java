package rutebaga.commons.math.rel;

import rutebaga.commons.math.ValueProvider;

public class ValueProviderNode<T> extends ParseTreeNode<ValueProvider<T>>
{
	public ValueProviderNode(ValueProvider<T> value)
	{
		super(value);
	}

	@Override
	public void accept(ParseTreeVisitor v)
	{
		v.visitValueProviderNode(this);
	}
}
