package rutebaga.model.entity;

import java.util.HashMap;
import java.util.Map;

import rutebaga.model.entity.messages.StatChangeMessage;
import rutebaga.model.entity.stats.StatisticId;

public class MessageDaemon
{
	private Entity entity;

	private Map<StatisticId, Double> previousValues = new HashMap<StatisticId, Double>();

	public MessageDaemon(Entity entity)
	{
		super();
		this.entity = entity;
	}

	public void tick()
	{
		for (StatisticId stat : entity.getStats().getStatIds())
		{
			double value = entity.getStats().getValue(stat);
			if (!previousValues.containsKey(stat) || Double.compare(value, previousValues.get(stat)) != 0)
			{
				Message message = new StatChangeMessage(entity, stat, value);
				entity.acceptMessage(message);
			}
		}
	}
}
