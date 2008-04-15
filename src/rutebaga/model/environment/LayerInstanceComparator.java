package rutebaga.model.environment;

import java.util.Comparator;

public class LayerInstanceComparator  implements Comparator<Instance>
{

	public int compare(Instance arg0, Instance arg1)
	{
		return Double.compare(arg0.getLayer(), arg1.getLayer());
	}

}
