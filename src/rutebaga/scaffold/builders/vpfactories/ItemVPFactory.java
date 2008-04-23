package rutebaga.scaffold.builders.vpfactories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.ItemValueProvider;
import rutebaga.model.item.ItemType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractValueProviderFactory;

public class ItemVPFactory extends AbstractValueProviderFactory
{
	private static Set<String> validTypes;
	
	static {
		
		validTypes = new HashSet<String>();
		validTypes.add("item");
		
	}
	
	private ItemType type;

	public ItemType getType()
	{
		return type;
	}

	public void setType(ItemType type)
	{
		this.type = type;
	}

	@Override
	protected ValueProvider get(String type, Map<String, String> params,
			MasterScaffold scaffold)
	{
		return new ItemValueProvider((ItemType) scaffold.get(params.get("default")));
	}

	@Override
	public Set<String> getValidTypes()
	{
		return validTypes;
	}

}
