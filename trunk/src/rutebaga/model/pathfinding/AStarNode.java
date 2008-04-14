package rutebaga.model.pathfinding;

import java.util.List;

/**
 * Abstract node class for use with {@link AStarSearch}. AStarNode should be
 * subclassed to provide searching capability.
 * 
 * @author Nicholas Stamas
 * 
 */

public abstract class AStarNode implements Comparable<AStarNode>
{

	private AStarNode pathParent;
	private float costFromStart;
	private float estimatedCostToGoal;

	public int compareTo(AStarNode other)
	{
		float myValue = this.getCost();
		float otherValue = other.getCost();

		float v = myValue - otherValue;
		if (v > 0)
			return 1;
		else if (v < 0)
			return -1;
		else
			return 0;
	}

	public float getCost()
	{
		return costFromStart + estimatedCostToGoal;
	}

	/**
	 * Gets cost between this node and the neighbor node.
	 * 
	 * @param node
	 * @return float
	 */
	public abstract float getCost(AStarNode node);

	/**
	 * Gets estimated cost between this node and passed node. Should never be
	 * greater than actual cost.
	 * 
	 * @param node
	 * @return float
	 */
	public abstract float getEstimatedCost(AStarNode node);

	/**
	 * Return a list of immediate neighbors.
	 * 
	 * @return List<AStarNode>
	 */
	public abstract List<AStarNode> getNeighbors();

	public AStarNode getPathParent()
	{
		return pathParent;
	}

	public void setPathParent(AStarNode pathParent)
	{
		this.pathParent = pathParent;
	}

	public float getCostFromStart()
	{
		return costFromStart;
	}

	public void setCostFromStart(float costFromStart)
	{
		this.costFromStart = costFromStart;
	}

	public float getEstimatedCostToGoal()
	{
		return estimatedCostToGoal;
	}

	public void setEstimatedCostToGoal(float estimatedCostToGoal)
	{
		this.estimatedCostToGoal = estimatedCostToGoal;
	}

}
