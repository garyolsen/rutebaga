package rutebaga.model.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import rutebaga.model.entity.messages.EntityStatEffectMessage;
import rutebaga.model.entity.stats.StatisticId;

public class PlayerEffectSource implements EffectSource
{
	private Set<Entity> players;

	public PlayerEffectSource(Entity... entities)
	{
		this.players = new HashSet<Entity>();
		for (Entity e : entities)
			players.add(e);
	}

	public void onKill(Entity entity)
	{
		int size = players.size();
		for (Entity e : players)
		{
			if(e != null && e.getExperienceCalculation() != null) {
				double experienceAllocated = e.getExperienceCalculation().getValue(entity);
				e.getExperience().addTo(e, experienceAllocated / size);
			}
		}
	}

	public Collection<Entity> getPlayers()
	{
		return players;
	}

	public void callback(Entity entity, EntityEffect effect, StatisticId idAffected, double amount)
	{
		for (Entity e : players)
		{
			entity.acceptMessage(new EntityStatEffectMessage(e, entity, idAffected, amount));
		}
	}

}
