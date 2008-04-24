package rutebaga.model.entity;

import rutebaga.model.entity.stats.StatisticId;

public interface EffectSource
{
	void onKill(Entity entity);
	void callback(Entity entity, EntityEffect effect, StatisticId idAffected, double amount);
}
