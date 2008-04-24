package rutebaga.model.entity.inventory;

import rutebaga.model.entity.EffectSource;
import rutebaga.model.entity.Entity;

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

}
