package rutebaga.model.entity.stats;

import rutebaga.commons.math.BidirectionalValueProvider;

public class StatValueProvider extends BidirectionalValueProvider<Stats>
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

	@Override
	public double addTo(Stats t, double value)
	{
		t.modifyStat(statId, value);
		return getValue(t);
	}

}
