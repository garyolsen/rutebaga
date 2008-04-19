package rutebaga.model.environment;

import java.util.Collection;

import rutebaga.commons.math.GenericVector2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;

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
	IntVector2D tileOf(Vector2D coordinate);

	/**
	 * Calculates the set of adjacent tiles to this tile based on the given
	 * mapping's adjacency rules.
	 * 
	 * @param tile
	 *            the tile-space coordinate upon which the calculation is based
	 * @return the set of adjacent tile-space coordinates
	 */
	Collection<IntVector2D> adjacentTo(IntVector2D tile);

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
	Vector2D toRect(GenericVector2D coordinate);
	
	public Collection<IntVector2D> between(IntVector2D a, IntVector2D b);
	
	Vector2D fromRect(GenericVector2D coordinate);
}
