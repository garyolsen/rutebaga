package rutebaga.scaffold.builders;

import java.awt.Image;
import java.util.ArrayList;

import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.MovementAttributes;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.scaffold.MasterScaffold;

public abstract class InstanceBuilder<T extends ConcreteInstanceType> extends ConfigFileBuilder
{

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		ConcreteInstanceType instance = (ConcreteInstanceType) object;
		instance.setMovementAttributes((MovementAttributes) getObjectFor(id,
				"movementAttr", scaffold));
	}
	
	public Appearance[] getAppearanceArray(String id, String property,
			String regexp, MasterScaffold scaffold)
	{
		ArrayList<Appearance> list = new ArrayList<Appearance>();
		for (String scaffId : getStringArray(id, property, regexp))
		{
			list.add(new Appearance((Image) scaffold.get(scaffId)));
		}
		return list.toArray(new Appearance[0]);
	}

}
