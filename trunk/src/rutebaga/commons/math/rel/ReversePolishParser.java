package rutebaga.commons.math.rel;

import java.util.Set;
import java.util.Stack;

import rutebaga.commons.math.BinaryOperation;
import rutebaga.commons.math.DefaultOperationTable;
import rutebaga.commons.math.OperationTable;
import rutebaga.commons.math.UnaryOperation;

public class ReversePolishParser implements Parser
{
	private OperationTable table;
	private Set<String> operations;
	
	private int currentLocation;
	
	private String[] parts;
	
	public ReversePolishParser()
	{
		this(DefaultOperationTable.getInstance());
	}

	public ReversePolishParser(OperationTable table)
	{
		super();
		this.table = table;
		operations = table.getOperations();
	}

	public ParseTreeNode parse(String expression)
	{
		parts = expression.split("[\\s]+");
		currentLocation = 0;
		return getNextNode();
	}

	private ParseTreeNode getNextNode()
	{
		String part = parts[currentLocation++];
		ParseTreeNode node;
		if(operations.contains(part))
		{
			if(table.isBinaryOp(part))
			{
				node = new BinaryNode(table.getBinaryOp(part), getNextNode(), getNextNode());
			}
			else
			{
				node = new UnaryNode(table.getUnaryOp(part), getNextNode());
			}
		}
		else
		{
			try
			{
				double value = Double.parseDouble(part);
				node = new ValueNode(value);
			}
			catch(NumberFormatException e)
			{
				node = new SymbolNode(part);
			}
		}
		return node;
	}
}
