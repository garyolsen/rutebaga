package rutebaga.model.environment;

import java.util.Collection;

import rutebaga.commons.math.GenericVector2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;

public class MatrixTileConvertor implements TileConverter
{
	private Rect2DTileConvertor backing = new Rect2DTileConvertor();

	private double[] values = new double[4];
	private double constant = 1;
	private double invDeterminant;

	public MatrixTileConvertor(double a, double b, double c, double d)
	{
		set(a, b, c, d);
	}

	public MatrixTileConvertor(double a, double b, double c, double d, double constant)
	{
		set(a, b, c, d, constant);
	}

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
		double x = coordinate.getX().doubleValue() * values[3]
				- coordinate.getY().doubleValue() * values[2];
		double y = -coordinate.getX().doubleValue() * values[1]
				+ coordinate.getY().doubleValue() * values[0];
		x /= invDeterminant;
		y /= invDeterminant;
		return new Vector2D(x, y);

	}

	public int getDimension()
	{
		return 2;
	}

	public void set(double a, double b, double c, double d)
	{
		values[0] = a;
		values[1] = b;
		values[2] = c;
		values[3] = d;
		invDeterminant = (values[0] * values[3] - values[1] * values[2])
				/ constant;
	}

	public void set(double a, double b, double c, double d, double constant)
	{
		this.constant = constant;
		values[0] = a;
		values[1] = b;
		values[2] = c;
		values[3] = d;
		invDeterminant = (values[0] * values[3] - values[1] * values[2])
				/ constant;
	}

	public IntVector2D tileOf(Vector2D coordinate)
	{
		return backing.tileOf(coordinate);
	}

	public Vector2D toRect(GenericVector2D coordinate)
	{
		double x = coordinate.getX().doubleValue() * values[0]
				+ coordinate.getY().doubleValue() * values[2];
		double y = coordinate.getX().doubleValue() * values[1]
				+ coordinate.getY().doubleValue() * values[3];
		x *= constant;
		y *= constant;
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
