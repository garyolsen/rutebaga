package rutebaga.model.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import rutebaga.commons.math.GenericVector2D;
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
public class Hex2DTileConvertor implements TileConvertor
{
	final static double xratio = Math.sqrt(3) / 2.0;
	final static double yratio = 1.0 / 2.0;
	
	final static double rbar = 40.0/70;
	final static double eqn_m = -1/(4*rbar-2);
	final static double eqn_b = -rbar*eqn_m;
	
	final static IntVector2D xPos = new IntVector2D(1, 0);
	final static IntVector2D xNeg = new IntVector2D(-1, 0);
	final static IntVector2D yPos = new IntVector2D(0, 1);
	final static IntVector2D yNeg = new IntVector2D(0, -1);
	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.TileConvertor#tileOf(rutebaga.commons.Vector)
	 */
	public IntVector2D tileOf(Vector2D coordinate)
	{
		IntVector2D closestCenter = new IntVector2D((int)Math
				.round(coordinate.getX()), (int)Math.round(coordinate.getY()));
		Vector2D diff = coordinate.minus(closestCenter);
		Vector2D rect = toRect(diff);
		double y = rect.getY();
		double x = rect.getX();
		if(Math.abs(y) > eqn_m*Math.abs(x) + eqn_b)
		{
			rutebaga.commons.Log.log("eqn: y = " + eqn_m + "*x + " + eqn_b);
			rutebaga.commons.Log.log();
			int xDiff = (int) Math.signum(x);
			int yDiff = (int) Math.signum(y);
			rutebaga.commons.Log.log(xDiff + ", " + yDiff);
			boolean neg = yDiff == -1;
			IntVector2D augend;
			if(xDiff != yDiff)
			{
				// use y
				augend = neg ? yNeg : yPos;
			}
			else
			{
				augend = neg ? xNeg : xPos;
			}
			return closestCenter.plus(augend);
		}
		return closestCenter;
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
		rval.add(tile.plus(new IntVector2D(1, 1)));
		rval.add(tile.plus(new IntVector2D(-1, -1)));
		return rval;
	}

	public int getDimension()
	{
		return 2;
	}

	@SuppressWarnings("unchecked")
	public Vector2D toRect(GenericVector2D coordinate)
	{
		double x = xratio * (coordinate.getX().doubleValue() - coordinate.getY().doubleValue());
		double y = yratio * (coordinate.getX().doubleValue() + coordinate.getY().doubleValue());
		return new Vector2D(x, y);
	}

	public Collection<IntVector2D> between(IntVector2D a, IntVector2D b)
	{
		// FIXME plain wrong.
		Collection<IntVector2D> rval = new HashSet<IntVector2D>();
//		rval.add(b);
		return rval;
	}

	public Vector2D fromRect(GenericVector2D coordinate)
	{
		double y = (coordinate.getY().doubleValue()/yratio - coordinate.getX().doubleValue()/xratio)/2;
		double x = coordinate.getY().doubleValue()/yratio - y; 
		return new Vector2D(x, y);
	}
}
