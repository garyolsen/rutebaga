package rutebaga.commons.math.rel;

import java.util.Map;

import rutebaga.commons.math.ValueProvider;

public class EvaluatorVisitor implements ParseTreeVisitor
{
	private double value;
	
	private Object root;
	private Map<String, Double> symbolTable;

	public EvaluatorVisitor(Object root, Map<String, Double> symbolTable)
	{
		super();
		this.root = root;
		this.symbolTable = symbolTable;
	}

	public double getValue()
	{
		return value;
	}

	public void visitBinaryNode(BinaryNode node)
	{
		node.getLhs().accept(this);
		double left = value;
		node.getRhs().accept(this);
		double right = value;
		value = node.getValue().calculate(left, right);
	}

	public void visitSymbolNode(SymbolNode node)
	{
		value = symbolTable.get(node.getValue());
	}

	public void visitUnaryNode(UnaryNode node)
	{
		node.getArg().accept(this);
		value = node.getValue().calculate(value);
	}

	public void visitValueNode(ValueNode node)
	{
		value = node.getValue();
	}

	public void visitValueProviderNode(ValueProviderNode node)
	{
		ValueProvider p = (ValueProvider) node.getValue();
		value = p.getValue(root);
	}

}
