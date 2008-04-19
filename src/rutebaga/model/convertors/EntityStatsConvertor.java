package rutebaga.model.convertors;

import rutebaga.commons.Convertor;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.Stats;

public class EntityStatsConvertor implements Convertor<Stats, Entity>
{

	public Stats convert(Entity value)
	{
		return value.getStats();
	}

}
