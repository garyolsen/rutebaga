package rutebaga.model.environment;

import java.util.Collection;
import java.util.Set;

import rutebaga.commons.math.Vector;

/**
 * Provides a mapping between a tile-space and actual space.
 * 
 * @author Gary LosHuertos
 * 
 * @see Environment
 */
public interface TileConvertor
{
	/**
	 * Determines the discrete tile-space coordinate of a coordinate in actual
	 * space.
	 * 
	 * @param coordinate
	 *            the coordinate in actual space
	 * @return the tile-space coordinate
	 */
	Vector tileOf(Vector coordinate);

	/**
	 * Calculates the set of adjacent tiles to this tile based on the given
	 * mapping's adjacency rules.
	 * 
	 * @param tile
	 *            the tile-space coordinate upon which the calculation is based
	 * @return the set of adjacent tile-space coordinates
	 */
	Set<Vector> adjacentTo(Vector tile);

	/**
	 * @return the dimension of this space definition
	 */
	int getDimension();
	
	/**
	 * Converts the given coordinate into a coordinate within rectangular space.
	 * 
	 * @param coordinate	the coordinate to convert
	 * @return	the coordinate's rectangular location
	 */
	Vector toRect(Vector coordinate);
	
	public Collection<Vector> between(Vector a, Vector b);
}
