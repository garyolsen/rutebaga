package rutebaga.model.entity.inventory;

import rutebaga.model.entity.EffectSource;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.stats.StatisticId;

public class InventoryEffectSource implements EffectSource
{
	private Inventory inventory;

	public InventoryEffectSource(Inventory inventory)
	{
		super();
		this.inventory = inventory;
	}

	public void onKill(Entity entity)
	{
		// this REALLY should not happen.... but whatever
	}

	public void callback(Entity entity, EntityEffect effect, StatisticId idAffected, double amount)
	{
		
	}

}
