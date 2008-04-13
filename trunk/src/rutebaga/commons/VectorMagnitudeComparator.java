package rutebaga.commons;

import java.util.Comparator;

public class VectorMagnitudeComparator implements Comparator<Vector>
{

	public int compare(Vector lhs, Vector rhs)
	{
		return Double.compare(lhs.getMagnitude(), rhs.getMagnitude());
	}

}
