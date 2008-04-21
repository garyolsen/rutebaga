package rutebaga.model.entity.stats;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.DamageType;
import rutebaga.model.entity.Entity;

public class Damage
{
	private DamageType type;
	private StatisticId statisticId;
	private ValueProvider<Entity> resistance;
	private ValueProvider<Double> transformation;

	
}
