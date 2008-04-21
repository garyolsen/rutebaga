package rutebaga.model.environment;

import java.util.Collection;

import rutebaga.commons.math.GenericVector2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;

public class ChainedMatrixConvertor implements TileConverter
{
	private TileConverter backing;
	private MatrixTileConvertor matrix;

	public ChainedMatrixConvertor(TileConverter backing,
			MatrixTileConvertor matrix)
	{
		super();
		this.backing = backing;
		this.matrix = matrix;
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
		return matrix.fromRect(backing.fromRect(coordinate));
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
		return backing.toRect(matrix.toRect(coordinate));
	}

}
