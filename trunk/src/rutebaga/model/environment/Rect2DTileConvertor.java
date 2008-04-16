package rutebaga.model.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.math.Vector;

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
	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.TileConvertor#tileOf(rutebaga.commons.Vector)
	 */
	public Vector tileOf(Vector coordinate)
	{
		double newCoords[] = new double[coordinate.getDimension()];
		for (int idx = 0; idx < newCoords.length; idx++)
		{
			newCoords[idx] = Math.round(coordinate.get(idx));
		}
		return new Vector(newCoords);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.TileConvertor#adjacentTo(rutebaga.commons.Vector)
	 */
	public Set<Vector> adjacentTo(Vector tile)
	{
		Set<Vector> rval = new HashSet<Vector>();
		rval.add(tile.plus(new Vector(0, 1)));
		rval.add(tile.plus(new Vector(0, -1)));
		rval.add(tile.plus(new Vector(1, 0)));
		rval.add(tile.plus(new Vector(-1, 0)));
		return rval;
	}

	public int getDimension()
	{
		return 2;
	}

	public Vector toRect(Vector coordinate)
	{
		return coordinate;
	}

	public Collection<Vector> between(Vector a, Vector b)
	{
		Collection<Vector> rval = new HashSet<Vector>();
		Vector slopeV = b.minus(a).getDirection();
		double slope = slopeV.get(1) / slopeV.get(0);
		if (slope == Double.POSITIVE_INFINITY
				|| slope == Double.NEGATIVE_INFINITY)
		{
			int x = (int) a.get(0);
			for (int y = (int) a.get(1); y <= b.get(1); y++)
			{
				rval.add(new Vector(x, y));
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
				rval.add(new Vector(x, y));
			}
			currentY = nextY;
		}
		rval.add(b);
		return rval;
	}
}
