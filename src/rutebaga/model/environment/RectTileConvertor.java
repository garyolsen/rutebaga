package rutebaga.model.environment;

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
}
