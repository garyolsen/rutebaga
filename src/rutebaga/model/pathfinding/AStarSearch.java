package rutebaga.model.pathfinding;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Works in conjunction with {@link AStarNode}. AStarSearch should be
 * subclassed to provide searching functionality.
 * 
 * @author Nicholas Stamas
 * 
 */
public class AStarSearch<T extends AStarNode<T>>
{

	/**
	 * Constructs a path of {@link AStarNode}'s form the passed node.
	 * 
	 * @param node
	 * @return List<AStarNode>
	 */
	protected List<T> constructPath(T node)
	{
		LinkedList<T> path = new LinkedList<T>();
		while (node.getPathParent() != null)
		{
			path.addFirst(node);
			node = node.getPathParent();
		}
		return path;
	}

	/**
	 * An implementation of the A* pathfinding algorithm.
	 * 
	 * @param startNode
	 * @param goalNode
	 * @return List<AStarNode>
	 */

	public List<T> findPath(T startNode, T goalNode)
	{

		PriorityQueue<T> openList = new PriorityQueue<T>();
		LinkedList<T> closedList = new LinkedList<T>();

		startNode.setCostFromStart(0);
		startNode.setEstimatedCostToGoal(startNode.getEstimatedCost(goalNode));
		startNode.setPathParent(null);
		openList.add(startNode);

		while (!openList.isEmpty())
		{
			T node = openList.remove();

			// found a path!
			// now construct it!
			if (node == goalNode)
			{
				return constructPath(goalNode);
			}

			List<T> neighbors = node.getNeighbors();
			for (int i = 0; i < neighbors.size(); i++)
			{
				T neighborNode = neighbors.get(i);
				boolean isOpen = openList.contains(neighborNode);
				boolean isClosed = closedList.contains(neighborNode);
				float costFromStart = node.getCostFromStart()
						+ node.getCost(neighborNode);

				if ((!isOpen && !isClosed)
						|| costFromStart < neighborNode.getCostFromStart())
				{
					neighborNode.setPathParent(node);
					neighborNode.setCostFromStart(costFromStart);
					neighborNode.setEstimatedCostToGoal(neighborNode
							.getEstimatedCost(goalNode));
					if (isClosed)
					{
						closedList.remove(neighborNode);
					}
					if (!isOpen)
					{
						openList.add(neighborNode);
					}
				}
			}

			closedList.add(node);
		}

		// no path available
		return null;

	}
}
