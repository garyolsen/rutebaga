package rutebaga.commons.math;

public class DefaultOperationTable
{
	private static DefaultOperationTable tblInstance = new DefaultOperationTable();

	private OperationTable table;
	
	private DefaultOperationTable()
	{
		table = new OperationTable();
		Class<OperationSet> operationsSetClass = OperationSet.class;
		for (Class clazz : operationsSetClass.getDeclaredClasses())
		{
			try
			{
				if (BinaryOperation.class.isAssignableFrom(clazz))
				{
					BinaryOperation op = (BinaryOperation) clazz.newInstance();
					table.addOp(op, op.getDefaultString());
				}
				if (UnaryOperation.class.isAssignableFrom(clazz))
				{
					UnaryOperation op = (UnaryOperation) clazz.newInstance();
					table.addOp(op, op.getDefaultString());
				}
			}
			catch (Throwable t)
			{

			}
		}
	}
	
	public static OperationTable getInstance()
	{
		return tblInstance.table;
	}
}
