package rutebaga.model.environment;

import java.util.Set;

import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;

public class MovementEvent
{
	private final Instance instance;
	private final Vector2D oldCoordinate;
	private final IntVector2D oldTile;

	public MovementEvent(Instance instance, Vector2D oldCoordinate, IntVector2D oldTile)
	{
		this.instance = instance;
		this.oldCoordinate = oldCoordinate;
		this.oldTile = oldTile;
	}

	public Instance getInstance()
	{
		return instance;
	}

	public Vector2D getNewCoordinate()
	{
		return instance.getCoordinate();
	}

	public IntVector2D getNewTile()
	{
		return instance.getTile();
	}

	public Vector2D getOldCoordinate()
	{
		return oldCoordinate;
	}

	public IntVector2D getOldTile()
	{
		return oldTile;
	}

	public Set<MovementListener> getInstanceListeners()
	{
		return instance.getMovementListeners();
	}
}
