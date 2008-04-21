package rutebaga.model.map;

import rutebaga.model.environment.ConcreteInstanceType;

public class RiverNodeType extends ConcreteInstanceType<RiverNode>
{

	@Override
	protected RiverNode create() {
		return new RiverNode(this);
	} 
	
	@Override
	protected void initialize(RiverNode instance)
	{
		super.initialize(instance);
	}
}