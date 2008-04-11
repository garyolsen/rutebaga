package rutebaga.commons;

/**
 * Interface for declaring a bounds within space.
 * 
 * @author Gary LosHuertos
 *
 */
public abstract class Bounds
{
	public abstract boolean contains(Vector v);
	public Matrix filter(Matrix m)
	{
		Vector[] vectors = m.asVectors();
		
		for(int idx=0; idx<vectors.length; idx++)
		{
			if(!contains(vectors[idx]))
				vectors[idx] = null;
		}
		
		return new Matrix(vectors);
	}
}
