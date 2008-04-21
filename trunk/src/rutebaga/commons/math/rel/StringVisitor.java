package rutebaga.commons.math.rel;

public class StringVisitor implements ParseTreeVisitor
{
	private StringBuilder sb = new StringBuilder();
	private Mode mode = Mode.INFIX;
	
	public static enum Mode
	{
		INFIX, PREFIX, POSTFIX;
	}

	public void visitBinaryNode(BinaryNode node)
	{
		String op = node.getValue().getDefaultString();
		sb.append("(");
		if(mode == Mode.PREFIX) sb.append(op).append(" ");
		node.getLhs().accept(this);
		sb.append(" ");
		if(mode == Mode.INFIX) sb.append(op).append(" ");
		node.getRhs().accept(this);
		if(mode == Mode.POSTFIX) sb.append(" ").append(op);
		sb.append(")");
	}

	public void visitSymbolNode(SymbolNode node)
	{
		sb.append("$").append(node.getValue());
	}

	public void visitUnaryNode(UnaryNode node)
	{
		sb.append(node.getValue().getDefaultString()).append(" ");
		node.getArg().accept(this);
	}

	public void visitValueNode(ValueNode node)
	{
		sb.append(node.getValue().toString());
	}

	public String getString()
	{
		return sb.toString();
	}

	public void visitValueProviderNode(ValueProviderNode node)
	{
		sb.append("@").append(node.getValue()).append("@");
	}

}
