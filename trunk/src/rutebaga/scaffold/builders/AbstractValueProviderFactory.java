package rutebaga.scaffold.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.ValueProvider;
import rutebaga.scaffold.MasterScaffold;

public abstract class AbstractValueProviderFactory
{
	public abstract Set<String> getValidTypes();

	public ValueProvider get(String description, MasterScaffold scaffold)
	{
		// FORMAT: VP_[type]_[params] (no spaces allowed)
		// params: obj=thing;obj2=thing
		String[] parts = description.split("_");
		String type = parts[1];
		String params[] = parts[2].split(";");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (params.length == 1)
			paramMap.put("default", params[0]);
		else
			for (int idx = 0; idx < params.length; idx++)
				paramMap.put(params[idx], params[idx + 1]);
		return get(type, paramMap, scaffold);
	}

	protected abstract ValueProvider get(String type,
			Map<String, String> params, MasterScaffold scaffold);
}
