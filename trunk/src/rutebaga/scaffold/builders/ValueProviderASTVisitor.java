package rutebaga.scaffold.builders;

import rutebaga.commons.math.BinaryOperation;
import rutebaga.commons.math.BinaryOperationValueProvider;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.UnaryOperation;
import rutebaga.commons.math.UnaryOperationValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.rel.BinaryNode;
import rutebaga.commons.math.rel.ParseTreeVisitor;
import rutebaga.commons.math.rel.SymbolNode;
import rutebaga.commons.math.rel.UnaryNode;
import rutebaga.commons.math.rel.ValueNode;
import rutebaga.scaffold.MasterScaffold;

public class ValueProviderASTVisitor implements ParseTreeVisitor
{
	private ValueProvider p;
	private AbstractValueProviderFactory factory;
	private MasterScaffold scaffold;

	public ValueProvider getValueProvider()
	{
		return p;
	}

	public ValueProviderASTVisitor(AbstractValueProviderFactory factory,
			MasterScaffold scaffold)
	{
		super();
		this.factory = factory;
		this.scaffold = scaffold;
	}

	public void visitBinaryNode(BinaryNode node)
	{
		BinaryOperation op = node.getValue();
		node.getLhs().accept(this);
		ValueProvider lhs = p;
		node.getRhs().accept(this);
		ValueProvider rhs = p;
		p = new BinaryOperationValueProvider(op, lhs, rhs);
	}

	public void visitSymbolNode(SymbolNode node)
	{
		p = factory.get(node.getValue(), scaffold);
	}

	public void visitUnaryNode(UnaryNode node)
	{
		UnaryOperation op = node.getValue();
		node.getArg().accept(this);
		ValueProvider lhs = p;
		p = new UnaryOperationValueProvider(op, lhs);
	}

	public void visitValueNode(ValueNode node)
	{
		p = new ConstantValueProvider(node.getValue());
	}

}
