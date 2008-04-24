package rutebaga.model.environment;

import java.util.Collection;

import rutebaga.commons.math.GenericVector2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;

public class PolarTileConvertor implements TileConverter
{
	private Rect2DTileConvertor backing = new Rect2DTileConvertor();

	public Collection<IntVector2D> adjacentTo(IntVector2D tile)
	{
		return backing.adjacentTo(tile);
	}

	public Collection<IntVector2D> between(IntVector2D a, IntVector2D b)
	{
		return backing.between(a, b);
	}

	public Vector2D fromRect(GenericVector2D coordinate)
	{
		double r = coordinate.getMagnitude();
		double theta = Math.atan2(coordinate.getY().doubleValue(), coordinate
				.getX().doubleValue());
		return new Vector2D(r, theta);
	}

	public int getDimension()
	{
		return 2;
	}

	public IntVector2D tileOf(Vector2D coordinate)
	{
		return backing.tileOf(coordinate);
	}

	public Vector2D toRect(GenericVector2D coordinate)
	{
		double x = coordinate.getX().doubleValue()
				* Math.cos(coordinate.getY().doubleValue());
		double y = coordinate.getX().doubleValue()
				* Math.sin(coordinate.getY().doubleValue());
		return new Vector2D(x, y);
	}

	public Vector2D closestDirection(Vector2D direction) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<IntVector2D> near(IntVector2D tile) {
		// TODO Auto-generated method stub
		return null;
	}

}
