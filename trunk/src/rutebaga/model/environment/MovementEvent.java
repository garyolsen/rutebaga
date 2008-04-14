package rutebaga.model.environment;

import java.util.Set;

import rutebaga.commons.Vector;

public class MovementEvent
{
	private final Instance instance;
	private final Vector oldCoordinate;
	private final Vector oldTile;

	public MovementEvent(Instance instance, Vector oldCoordinate, Vector oldTile)
	{
		this.instance = instance;
		this.oldCoordinate = oldCoordinate;
		this.oldTile = oldTile;
	}

	public Instance getInstance()
	{
		return instance;
	}

	public Vector getNewCoordinate()
	{
		return instance.getCoordinate();
	}

	public Vector getNewTile()
	{
		return instance.getTile();
	}
	
	public Vector getOldCoordinate()
	{
		return oldCoordinate;
	}
	
	public Vector getOldTile()
	{
		return oldTile;
	}
	
	public Set<MovementListener> getInstanceListeners()
	{
		return instance.getMovementListeners();
	}
}
