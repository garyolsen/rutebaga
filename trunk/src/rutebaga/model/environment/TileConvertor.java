package rutebaga.model.environment;

import java.util.Set;

import rutebaga.commons.Vector;

public interface TileConvertor
{
	Vector tileOf(Vector coordinate);
	Set<Vector> adjacentTo(Vector tile);
}
