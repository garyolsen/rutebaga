package temporary;

import java.util.Comparator;

import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Layerable;
import rutebaga.model.environment.Locatable;

public class DrawOrderComparator<T extends Layerable & Locatable> implements
		Comparator<T>
{

	public int compare(T o1, T o2)
	{
		int value = Double.compare(o1.getLayer(), o2.getLayer());
		if(value == 0)
		{
			Vector2D coords[] = { o1.getCoordinate(), o2.getCoordinate() };
			value = Double.compare(coords[0].getY(), coords[1].getY());
			if(value == 0)
			{
				value = Double.compare(coords[0].getX() , coords[1].getX());
			}
		}
		return value;
	}
}
