package rutebaga.commons;

import java.util.Set;
import java.util.TreeSet;

public class VectorRectangle
{
	private Vector lowerBound;
	private Vector dimensions;

	public VectorRectangle(Vector lowerBound, Vector dimensions)
	{
		this.lowerBound = lowerBound;
		this.dimensions = dimensions;
	}

	public Vector getLowerBound()
	{
		return lowerBound;
	}

	public Vector getDimensions()
	{
		return dimensions;
	}

	public Set<Vector> locationSet(double scale)
	{
		Set<Vector> rval = new TreeSet<Vector>();
		double start[] = lowerBound.asArray();
		double end[] = dimensions.plus(lowerBound).asArray();

		boolean exit = false;

		while (!exit)
		{
			rval.add(new Vector(start));
			start[0] += scale;
			// essentially a counting algorithm
			for (int component = 0; component < start.length; component++)
			{
				if (start[component] > end[component])
				{
					if (component == start.length - 1)
					{
						// overflow -- we're done here
						exit = true;
						break;
					}
					else
					{
						// carry to the next digit
						start[component] = lowerBound.get(component);
						start[component + 1] += scale;
					}
				}
				else
				{
					break;
				}
			}
		}

		return rval;
	}
}
