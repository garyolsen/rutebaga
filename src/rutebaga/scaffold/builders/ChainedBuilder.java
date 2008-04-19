package rutebaga.scaffold.builders;

import java.util.HashMap;
import java.util.Map;

import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public class ChainedBuilder implements Builder
{
	private Map<String, Builder> subBuilders = new HashMap<String, Builder>();
	
	public void register(Builder builder)
	{
		for(String key : builder.availableIds())
		{
			subBuilders.put(key, builder);
		}
	}

	public String[] availableIds()
	{
		return subBuilders.keySet().toArray(new String[0]);
	}

	public Object create(String id)
	{
		return subBuilders.get(id).create(id);
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		subBuilders.get(id).initialize(id, object, scaffold);
	}

}
