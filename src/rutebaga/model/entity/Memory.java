package rutebaga.model.entity;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Layerable;
import rutebaga.model.environment.Locatable;

public class Memory implements Layerable, Locatable
{
	private Appearance appearance;
	private Vector2D coordinate;
	private double layer;

	public Memory(Appearance appearance, Vector2D coordinate, double layer)
	{
		super();
		this.appearance = appearance;
		this.coordinate = coordinate;
		this.layer = layer;
	}

	public Memory(Instance entity)
	{
		this.appearance = entity.getAppearance();
		this.coordinate = entity.getCoordinate();
		this.layer = entity.getLayer();
	}

	public Appearance getAppearance()
	{
		return appearance;
	}

	public Vector2D getCoordinate()
	{
		return coordinate;
	}

	public double getLayer()
	{
		return layer;
	}

	public void setAppearance(Appearance appearance)
	{
		this.appearance = appearance;
	}

	public void setCoordinate(Vector2D coordinate)
	{
		this.coordinate = coordinate;
	}
}
