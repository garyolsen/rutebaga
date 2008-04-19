package rutebaga.scaffold.builders;

import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.MovementAttributes;
import rutebaga.scaffold.MasterScaffold;

public abstract class InstanceBuilder<T extends ConcreteInstanceType> extends ConfigFileBuilder
{

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		ConcreteInstanceType instance = (ConcreteInstanceType) object;
		instance.setMovementAttributes((MovementAttributes) getObjectFor(id,
				"movementAttr", scaffold));
	}

}
