package rutebaga.commons.math.rel;

public class ValueNode extends ParseTreeNode<Double>
{

	public ValueNode(Double value)
	{
		super(value);
	}

	@Override
	public void accept(ParseTreeVisitor v)
	{
		v.visitValueNode(this);
	}

}
