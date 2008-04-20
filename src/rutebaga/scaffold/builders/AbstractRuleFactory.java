package rutebaga.scaffold.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.logic.Rule;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.scaffold.MasterScaffold;

public abstract class AbstractRuleFactory
{
	public abstract Set<String> getValidTypes();

	public Rule get(String description, MasterScaffold scaffold)
	{
		// FORMAT: VP_[type]_[params] (no spaces allowed)
		// params: obj=thing;obj2=thing
		String[] parts = description.split("_");
		String type = parts[1];
		String params[] = description.substring(
				parts[0].length() + 2 + parts[1].length()).split(";");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (params.length == 1)
			paramMap.put("default", params[0]);
		else
			for (int idx = 0; idx < params.length; idx++)
				paramMap.put(params[idx], params[idx + 1]);
		try
		{
			return get(type, paramMap, scaffold);

		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not find rule [" + description
					+ "]", e);
		}
	}

	protected abstract Rule get(String type,
			Map<String, String> params, MasterScaffold scaffold);
}
