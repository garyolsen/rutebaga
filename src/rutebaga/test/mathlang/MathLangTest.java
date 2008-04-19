package rutebaga.test.mathlang;

import rutebaga.commons.math.DefaultOperationTable;
import rutebaga.commons.math.OperationTable;
import rutebaga.commons.math.rel.ParseTreeNode;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.commons.math.rel.StringVisitor;

public class MathLangTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		OperationTable table = DefaultOperationTable.getInstance();
		System.out.println("Operation Table:");
		for(String op : table.getOperations())
		{
			Object operation = table.getBinaryOp(op);
			operation = operation == null ? table.getUnaryOp(op) : operation;
			System.out.println(op + ": " + operation);
		}
		System.out.println();
		ParseTreeNode node = new ReversePolishParser(table).parse("+ something + 3 + sin 3 4");
		
		StringVisitor v = new StringVisitor();
		node.accept(v);
		
		System.out.println(v.getString());
	}

}
