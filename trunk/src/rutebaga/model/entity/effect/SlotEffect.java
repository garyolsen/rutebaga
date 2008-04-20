package rutebaga.model.entity.effect;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.item.SlotType;

public class SlotEffect extends ReversibleEntityEffect
{
	private SlotType type;
	private int qty;

	public SlotEffect(SlotType type, int qty)
	{
		super();
		this.type = type;
		this.qty = qty;
	}

	@Override
	public EntityEffect getReverseEffect(Object id)
	{
		return new SlotEffect(type, -qty);
	}

	@Override
	protected void affect(Entity entity, Object id)
	{
		entity.getInventory().getCurrentAllocations().add(type, qty);
	}

	public SlotType getType()
	{
		return type;
	}

	public void setType(SlotType type)
	{
		this.type = type;
	}

	public int getQty()
	{
		return qty;
	}

	public void setQty(int qty)
	{
		this.qty = qty;
	}

}
