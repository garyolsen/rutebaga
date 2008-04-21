package rutebaga.scaffold.builders;

import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.model.environment.BlackList;
import rutebaga.appearance.StaticAppearanceDef;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.MovementAttributeSet;
import rutebaga.model.environment.MovementAttributes;
import rutebaga.model.environment.WhiteList;
import rutebaga.model.map.TerrainType;
import rutebaga.scaffold.MasterScaffold;

public abstract class InstanceBuilder<T extends ConcreteInstanceType> extends
		ConfigFileBuilder
{

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		ConcreteInstanceType instance = (ConcreteInstanceType) object;

		MovementAttributes attr = new MovementAttributes();
		String attrDesc = getProperty(id, "movement");

		
		if (attrDesc != null)
		{
			String[] tokens = attrDesc.split("[\\s\\t]");
			if(tokens != null && tokens.length > 0)
			{
				String type = tokens[0];
				System.out.println("type " + type);
				MovementAttributeSet set;
				if("whitelist".equals(type))
				{
					 set = new WhiteList();
				}
				else
				{
					set = new BlackList();
				}
				for(int i=1; i<tokens.length; i++)
					set.add((TerrainType) scaffold.get(tokens[i]));
				attr.add(set);
			}
		}
		instance.setMovementAttributes(attr);
		instance.setName( getProperty(id, "name") );

		String appManDesc = getProperty(id, "appearance");
		AppearanceManagerDefinition appDef = AppearanceDefFactory.getInstance()
				.get(appManDesc, scaffold);
		if (appDef != null)
			instance.setAppearanceDefinition(appDef);
		else
			instance.setAppearanceDefinition(new StaticAppearanceDef());

	}
}
