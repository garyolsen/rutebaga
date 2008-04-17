package rutebaga.model.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.Vector2D;

/**
 * Convertor for a basic rectangular tile system, within which tile-space is
 * congruent to actual space.
 * 
 * Discrete tile coordinates are just the rounded versions of their actual space
 * counterparts. That is,
 * 
 * tile{x,y} = { round(actual[x]), round(actual[y]) }
 * 
 * Such that a tile (x, y) is defined as the region in actual-space { [x-0.5,
 * x+0.5), [y-0.5, y+0.5) }
 * 
 * On a side-note, these notations are entirely fictional.
 * 
 * @author Gary LosHuertos
 * 
 */
public class Rect2DTileConvertor implements TileConvertor
{
	final static double xratio = Math.sqrt(3)/2.0;
	final static double yratio = 1.0/2.0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.TileConvertor#tileOf(rutebaga.commons.Vector)
	 */
	public IntVector2D tileOf(Vector2D coordinate)
	{
		return new IntVector2D((int) Math.round(coordinate.getX()), (int) Math
				.round(coordinate.getY()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.TileConvertor#adjacentTo(rutebaga.commons.Vector)
	 */
	public Collection<IntVector2D> adjacentTo(IntVector2D tile)
	{
		ArrayList<IntVector2D> rval = new ArrayList<IntVector2D>();
		rval.add(tile.plus(new IntVector2D(0, 1)));
		rval.add(tile.plus(new IntVector2D(0, -1)));
		rval.add(tile.plus(new IntVector2D(1, 0)));
		rval.add(tile.plus(new IntVector2D(-1, 0)));
		return rval;
	}

	public int getDimension()
	{
		return 2;
	}

	public Vector2D toRect(Vector2D coordinate)
	{
		return coordinate;
	}

	public Collection<IntVector2D> between(IntVector2D a, IntVector2D b)
	{
		Collection<IntVector2D> rval = new HashSet<IntVector2D>();
		Vector2D slopeV = b.minus(a).getDirection();
		double slope = slopeV.get(1) / slopeV.get(0);
		if (slope == Double.POSITIVE_INFINITY
				|| slope == Double.NEGATIVE_INFINITY)
		{
			int x = (int) a.get(0);
			for (int y = (int) a.get(1); y <= b.get(1); y++)
			{
				rval.add(new IntVector2D(x, y));
			}
			return rval;
		}
		double currentY = a.get(1);
		for (int x = (int) a.get(0); x < b.get(0); x++)
		{
			double nextY = currentY + slope;
			for (int y = (int) Math.round(currentY - 0.5); y <= Math
					.round(nextY - 0.5); y++)
			{
				rval.add(new IntVector2D(x, y));
			}
			currentY = nextY;
		}
		rval.add(b);
		return rval;
	}
}
