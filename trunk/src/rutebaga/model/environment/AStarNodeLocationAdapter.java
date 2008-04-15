package rutebaga.model.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.model.pathfinding.AStarNode;

public class AStarNodeLocationAdapter extends AStarNode
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
	public float getCost(AStarNode node)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getEstimatedCost(AStarNode node)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AStarNode> getNeighbors()
	{
		Set<Vector> neighbors = environment.getTileConvertor().adjacentTo(this.tile);
		List<AStarNode> nodeNeighbors = new ArrayList<AStarNode>(neighbors.size());
		for(Vector neighbor : neighbors)
		{
			nodeNeighbors.add(manager.getNode(neighbor));
		}
		return nodeNeighbors;
	}
	
	

}
