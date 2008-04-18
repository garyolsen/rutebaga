package rutebaga.view.game;

import java.awt.Point;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.Layerable;
import rutebaga.model.environment.Locatable;

public class AppearanceInstance implements Locatable, Layerable
{
	private Appearance appearance;
	private Point location;
	private double layer;
	private Vector2D coord;

	public AppearanceInstance(Appearance appearance, Point location,
			double layer)
	{
		super();
		this.appearance = appearance;
		Point dimensions = new Point( appearance.getImage().getWidth(null), appearance.getImage().getHeight(null) );
		this.location = location;
		appearance.getOrientation().transformPoint(location, dimensions);
		this.layer = layer;
		this.coord = new Vector2D(location.x, location.y);
	}

	public Vector2D getCoordinate()
	{
		return coord;
	}

	public double getLayer()
	{
		return layer;
	}

	public Appearance getAppearance()
	{
		return appearance;
	}

	public Point getLocation()
	{
		return location;
	}

	public Vector2D getCoord()
	{
		return coord;
	}
}
