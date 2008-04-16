package rutebaga.model.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rutebaga.commons.math.Vector;
import rutebaga.model.pathfinding.AStarNode;

public class AStarNodeLocationAdapter extends AStarNode<AStarNodeLocationAdapter>
{
	private Environment environment;
	private AStarNodeLocationManager manager;
	private Vector tile;

	public AStarNodeLocationAdapter(AStarNodeLocationManager manager, Vector tile)
	{
		super();
		this.environment = manager.getEnvironment();
		this.tile = tile;
	}

	@Override
	public float getCost( AStarNodeLocationAdapter node)
	{
		return 1;
	}

	@Override
	public float getEstimatedCost( AStarNodeLocationAdapter node)
	{
		return new Float((node.getTile().minus(tile)).getMagnitude());
	}

	@Override
	public List<AStarNodeLocationAdapter> getNeighbors()
	{
		Set<Vector> neighbors = environment.getTileConvertor().adjacentTo(this.tile);
		List<AStarNodeLocationAdapter> nodeNeighbors = new ArrayList<AStarNodeLocationAdapter>(neighbors.size());
		AStarNodeLocationAdapter node;
		for(Vector neighbor : neighbors)
		{
			node = manager.getNode(neighbor);
			if (!(node == null))
				nodeNeighbors.add(node);
		}
		return nodeNeighbors;
	}
	
	public Vector getTile() {
		return tile;
	}

}
