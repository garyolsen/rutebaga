package rutebaga.scaffold.builders;

import rutebaga.model.entity.AbilityCategory;
import rutebaga.model.entity.AbilityType;
import rutebaga.scaffold.MasterScaffold;

public abstract class AbstractAbilityBuilder extends ConfigFileBuilder
{
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		AbilityType type = (AbilityType) object;
		
		String name = getProperty(id, "name");
		type.setName(name);
		
		AbilityCategory category = (AbilityCategory) getObjectFor(id, "category", scaffold);
		if(category != null)
			type.setCategory(category);
	}

}
