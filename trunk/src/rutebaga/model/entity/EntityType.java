package rutebaga.model.entity;

import rutebaga.model.environment.InstanceType;

public class EntityType implements InstanceType<Entity>
{

	public Entity makeInstance()
	{
		return new CharEntity();
	}

}
