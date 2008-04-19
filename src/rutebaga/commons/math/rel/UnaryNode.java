package rutebaga.commons.math.rel;

import rutebaga.commons.math.UnaryOperation;

public class UnaryNode extends ParseTreeNode<UnaryOperation>
{
	private ParseTreeNode arg;

	public UnaryNode(UnaryOperation value, ParseTreeNode subnode)
	{
		super(value);
		arg = subnode;
	}

	@Override
	public void accept(ParseTreeVisitor v)
	{
		v.visitUnaryNode(this);
	}

	public ParseTreeNode getArg()
	{
		return arg;
	}

	public void setArg(ParseTreeNode arg)
	{
		this.arg = arg;
	}

}
