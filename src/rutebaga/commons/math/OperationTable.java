package rutebaga.commons.math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OperationTable
{
	private Map<String, UnaryOperation> unaryOperations = new HashMap<String, UnaryOperation>();
	private Map<String, BinaryOperation> binaryOperations = new HashMap<String, BinaryOperation>();
	
	public boolean isBinaryOp(String op)
	{
		return binaryOperations.containsKey(op);
	}
	
	public boolean isUnaryOp(String op)
	{
		return unaryOperations.containsKey(op);
	}
	
	public UnaryOperation getUnaryOp(String op)
	{
		return unaryOperations.get(op);
	}
	
	public BinaryOperation getBinaryOp(String op)
	{
		return binaryOperations.get(op);
	}
	
	public void addOp(BinaryOperation op, String text)
	{
		binaryOperations.put(text, op);
	}
	
	public void addOp(UnaryOperation op, String text)
	{
		unaryOperations.put(text, op);
	}
	
	public Set<String> getOperations()
	{
		Set<String> rval = new HashSet<String>();
		rval.addAll(unaryOperations.keySet());
		rval.addAll(binaryOperations.keySet());
		return rval;
	}
}
