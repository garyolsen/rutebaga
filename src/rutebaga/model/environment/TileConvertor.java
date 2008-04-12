package rutebaga.model.environment;

import java.util.Set;

import rutebaga.commons.Vector;

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
}
