package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.Vector;

public class RectTileConvertor implements TileConvertor
{
	public Vector tileOf(Vector coordinate)
	{
		double newCoords[] = new double[coordinate.getDimension()];
		for(int idx=0; idx<newCoords.length; idx++)
		{
			newCoords[idx] = Math.round(coordinate.get(idx));
		}
		return new Vector(newCoords);
	}

	public Set<Vector> adjacentTo(Vector tile)
	{
		Set<Vector> rval = new HashSet<Vector>();
		rval.add(tile.plus(new Vector(0, 1)));
		rval.add(tile.plus(new Vector(0, -1)));
		rval.add(tile.plus(new Vector(1, 0)));
		rval.add(tile.plus(new Vector(-1, 0)));
		return rval;
	}
}
