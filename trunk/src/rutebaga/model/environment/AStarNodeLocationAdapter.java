package rutebaga.model.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rutebaga.commons.math.IntVector2D;
import rutebaga.model.pathfinding.AStarNode;

public class AStarNodeLocationAdapter extends AStarNode<AStarNodeLocationAdapter>
{
	private Environment environment;
	private AStarNodeLocationManager manager;
	private IntVector2D tile;

	public AStarNodeLocationAdapter(AStarNodeLocationManager manager, IntVector2D tile)
	{
		super();
		this.environment = manager.getEnvironment();
		this.manager = manager;
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
		Collection<IntVector2D> neighbors = environment.getTileConvertor().adjacentTo(this.tile);
		List<AStarNodeLocationAdapter> nodeNeighbors = new ArrayList<AStarNodeLocationAdapter>(neighbors.size());
		AStarNodeLocationAdapter node;
		for(IntVector2D neighbor : neighbors)
		{
			node = manager.getNode(neighbor);
			if (!(node == null))
				nodeNeighbors.add(node);
		}
		return nodeNeighbors;
	}
	
	public IntVector2D getTile() {
		return tile;
	}

	@Override
	public boolean equals(AStarNodeLocationAdapter other) {
		return tile.equals(other.getTile());
	}

}
