package rutebaga.model.entity.stats;

import rutebaga.commons.math.ValueProvider;

public class StatValueProvider extends ValueProvider<Stats>
{
	private StatisticId statId;

	public StatValueProvider(StatisticId statId)
	{
		super();
		this.statId = statId;
	}

	public double getValue(Stats t)
	{
		return t.getValue(statId);
	}

}
