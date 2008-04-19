package rutebaga.commons.math.rel;

import rutebaga.commons.math.BinaryOperation;

public class BinaryNode extends ParseTreeNode<BinaryOperation>
{
	private ParseTreeNode lhs;
	private ParseTreeNode rhs;

	public BinaryNode(BinaryOperation value, ParseTreeNode left,
			ParseTreeNode right)
	{
		super(value);
		lhs = left;
		rhs = right;
	}

	@Override
	public void accept(ParseTreeVisitor v)
	{
		v.visitBinaryNode(this);
	}

	public ParseTreeNode getLhs()
	{
		return lhs;
	}

	public ParseTreeNode getRhs()
	{
		return rhs;
	}

	public void setLhs(ParseTreeNode lhs)
	{
		this.lhs = lhs;
	}

	public void setRhs(ParseTreeNode rhs)
	{
		this.rhs = rhs;
	}

}
