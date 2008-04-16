package rutebaga.model.entity;

import rutebaga.commons.math.Vector;
import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Layerable;

public class Memory implements Layerable
{
	private Appearance appearance;
	private Vector coordinate;
	private double layer;

	public Memory(Appearance appearance, Vector coordinate, double layer)
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

	public Vector getCoordinate()
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

	public void setCoordinate(Vector coordinate)
	{
		this.coordinate = coordinate;
	}
}
