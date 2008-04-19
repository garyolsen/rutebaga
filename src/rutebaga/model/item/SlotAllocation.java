package rutebaga.model.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SlotAllocation
{
	private Map<SlotType, Integer> allocations = new HashMap<SlotType, Integer>();

	public Set<SlotType> getSlotTypes()
	{
		return Collections.unmodifiableSet(allocations.keySet());
	}

	public int getAllocation(SlotType type)
	{
		return allocations.containsKey(type) ? allocations.get(type) : 0;
	}
	
	public int getAvailable(SlotType type)
	{
		int qty = getAllocation(type);
		return type.getQtyAvailable(qty);
	}

	public void add(SlotType type, int qty)
	{
		int current = getAllocation(type);
		allocations.put(type, current + qty);
	}
	
	public boolean contains(SlotAllocation other)
	{
		for(SlotType type : other.getSlotTypes())
		{
			int required = other.getAllocation(type);
			int available = this.getAvailable(type);
			if(required > available)
				return false;
		}
		return true;
	}
	
	public void remove(SlotAllocation other)
	{
		for(SlotType type : other.getSlotTypes())
		{
			int required = other.getAllocation(type);
			add(type, -required);
		}
	}
	
	public void add(SlotAllocation other)
	{
		for(SlotType type : other.getSlotTypes())
		{
			int required = other.getAllocation(type);
			add(type, required);
		}
	}
}
