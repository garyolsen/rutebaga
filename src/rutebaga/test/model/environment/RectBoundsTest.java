package rutebaga.test.model.environment;

import rutebaga.commons.math.Vector;
import rutebaga.model.environment.Rect2DTileConvertor;

public class RectBoundsTest
{
	public static void main(String... args)
	{
		Rect2DTileConvertor conv = new Rect2DTileConvertor();
		for (Vector v : conv.between(new Vector(0, 0), new Vector(1, 1)))
		{	
			rutebaga.commons.Log.log(v);
		}
	}
}
