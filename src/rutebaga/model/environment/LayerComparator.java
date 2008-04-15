package rutebaga.model.environment;

import java.util.Comparator;

public class LayerComparator implements Comparator<Layerable>
{

	public int compare(Layerable arg0, Layerable arg1)
	{
		return Double.compare(arg0.getLayer(), arg1.getLayer());
	}

}
