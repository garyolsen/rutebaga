package rutebaga.commons.math.rel;

public interface ParseTreeVisitor
{
	void visitBinaryNode(BinaryNode node);
	void visitUnaryNode(UnaryNode node);
	void visitSymbolNode(SymbolNode node);
	void visitValueNode(ValueNode node);
}
