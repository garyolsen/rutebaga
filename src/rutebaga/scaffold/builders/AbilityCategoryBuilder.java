package rutebaga.scaffold.builders;

import rutebaga.model.entity.AbilityCategory;
import rutebaga.model.item.SlotType;
import rutebaga.scaffold.MasterScaffold;

public class AbilityCategoryBuilder extends ConfigFileBuilder
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/abilitycategories";
	}

	public Object create(String id)
	{
		return new AbilityCategory(getProperty(id, "name"));
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		// no initialization
	}

}
