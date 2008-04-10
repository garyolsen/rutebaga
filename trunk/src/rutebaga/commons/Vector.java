package rutebaga.commons;

/**
 * A vector in space.
 * 
 * @author Gary LosHuertos
 * 
 */
public class Vector {

	private final double[] components;
	private Double magnitude;
	private int dimension;
	
	public Vector(double ... components)
	{
		dimension = components.length;
		this.components = new double[dimension];
		for(int idx=0; idx<components.length; idx++)
		{
			this.components[idx] = components[idx];
		}
	}
	
	private Vector(int dimension)
	{
		this.components = new double[dimension];
	}
	
	public double get(int idx)
	{
		return components[idx];
	}
	
	public double getMagnitude()
	{
		if(magnitude == null)
		{
			magnitude = 0.0;
			for(double c : components)
			{
				magnitude += c*c; 
			}
			magnitude = Math.sqrt(magnitude);
		}
		return magnitude;
	}
	
	private void check(Vector rhs)
	{
		if(dimension != rhs.dimension)
			throw new IncompatibleDimensionException(dimension, rhs.dimension);
	}
	
	public Vector plus(Vector rhs)
	{
		check(rhs);
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for(int idx=0; idx<dimension; idx++)
		{
			components[idx] = this.components[idx] + rhs.components[idx];
		}
		return rval;
	}
	
	public Vector minus(Vector rhs)
	{
		check(rhs);
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for(int idx=0; idx<dimension; idx++)
		{
			components[idx] = this.components[idx] - rhs.components[idx];
		}
		return rval;
	}
	
	public double dot(Vector rhs)
	{
		check(rhs);
		double rval = 0;
		for(int idx=0; idx<dimension; idx++)
		{
			rval += components[idx]*rhs.components[idx];
		}
		return rval;
	}
	
	public Vector negate()
	{
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for(int idx=0; idx<dimension; idx++)
		{
			components[idx] = -this.components[idx];
		}
		return rval;
	}
}
