package rutebaga.scaffold;

import java.util.HashMap;
import java.util.Map;

public class MasterScaffold
{
	private Map<String, Object> scaffold = new HashMap<String, Object>();
	private Map<String, Builder> builders = new HashMap<String, Builder>();
	
	public Object get(String id)
	{
		if(scaffold.get(id) == null)
		{
			Builder builder = builders.get(id);
			Object object = builder.create(id);
			scaffold.put(id, object);
			builder.initialize(id, object, this);
		}
		return scaffold.get(id);
	}
	
	public void setBuilder(String id, Builder builder)
	{
		this.builders.put(id, builder);
	}
}
