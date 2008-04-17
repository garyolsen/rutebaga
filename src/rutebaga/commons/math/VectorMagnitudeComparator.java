package rutebaga.commons.math;

import java.util.Comparator;

@Deprecated
public class VectorMagnitudeComparator implements Comparator<Vector>
{

	public int compare(Vector lhs, Vector rhs)
	{
		return Double.compare(lhs.getMagnitude(), rhs.getMagnitude());
	}

}
