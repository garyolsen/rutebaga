package rutebaga.commons.math.rel;

public class SymbolNode extends ParseTreeNode<String>
{

	public SymbolNode(String value)
	{
		super(value);
	}

	@Override
	public void accept(ParseTreeVisitor v)
	{
		v.visitSymbolNode(this);
	}

}
