package rutebaga.commons.math;

import java.util.Map;

import rutebaga.commons.math.rel.EvaluatorVisitor;
import rutebaga.commons.math.rel.ParseTreeNode;

public class SymbolicFunction<T>
{
	private ParseTreeNode treeRoot;

	public ParseTreeNode getTreeRoot()
	{
		return treeRoot;
	}

	public void setTreeRoot(ParseTreeNode treeRoot)
	{
		this.treeRoot = treeRoot;
	}

	public double getValue(Map<String, Double> context, T root)
	{
		EvaluatorVisitor visitor = new EvaluatorVisitor(root, context);
		treeRoot.accept(visitor);
		return visitor.getValue();
	}
}
