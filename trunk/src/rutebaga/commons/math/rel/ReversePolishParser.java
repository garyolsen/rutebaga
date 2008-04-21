package rutebaga.commons.math.rel;

import java.util.Set;
import java.util.Stack;

import rutebaga.commons.math.BinaryOperation;
import rutebaga.commons.math.DefaultOperationTable;
import rutebaga.commons.math.OperationTable;
import rutebaga.commons.math.UnaryOperation;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractValueProviderFactory;

public class ReversePolishParser implements Parser
{
	private OperationTable table;
	private Set<String> operations;

	private AbstractValueProviderFactory factory;
	private MasterScaffold scaffold;

	private int currentLocation;

	private String[] parts;

	public ReversePolishParser(AbstractValueProviderFactory factory,
			MasterScaffold scaffold)
	{
		this();
		this.factory = factory;
		this.scaffold = scaffold;
	}

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

	public ReversePolishParser(OperationTable table,
			AbstractValueProviderFactory factory, MasterScaffold scaffold)
	{
		super();
		this.table = table;
		this.factory = factory;
		this.scaffold = scaffold;
	}

	public AbstractValueProviderFactory getFactory()
	{
		return factory;
	}

	public MasterScaffold getScaffold()
	{
		return scaffold;
	}

	public ParseTreeNode parse(String expression)
	{
		parts = expression.split("[\\s]+");
		currentLocation = 0;
		return getNextNode();
	}

	public void setFactory(AbstractValueProviderFactory factory)
	{
		this.factory = factory;
	}

	public void setScaffold(MasterScaffold scaffold)
	{
		this.scaffold = scaffold;
	}

	private ParseTreeNode getNextNode()
	{
		String part = parts[currentLocation++];
		ParseTreeNode node;
		if (operations.contains(part))
		{
			if (table.isBinaryOp(part))
			{
				node = new BinaryNode(table.getBinaryOp(part), getNextNode(),
						getNextNode());
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
			catch (NumberFormatException e)
			{
				if (part.substring(0, 1).equals("&"))
					node = new ValueProviderNode(factory.get(part.substring(1), scaffold));
				else
					node = new SymbolNode(part);
			}
		}
		return node;
	}
}
