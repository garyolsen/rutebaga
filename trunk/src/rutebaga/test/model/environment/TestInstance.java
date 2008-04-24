package rutebaga.test.model.environment;

import rutebaga.model.DefaultLayers;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;


public class TestInstance extends Instance
{
	public TestInstance(InstanceType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public String name;


	@Override
	public String toString()
	{
		return "Instance named " + name + " at " + this.getCoordinate();
	}

	@Override
	public boolean blocks(Instance other)
	{
		if (other instanceof TestInstance)
		{
			if (((TestInstance) other).name.equals(this.name))
				return true;
		}
		return false;
	}

	@Override
	public double getMass()
	{
		return 1.0;
	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public double getLayer()
	{
		return DefaultLayers.GROUND.getLayer();
	}

	@Override
	public void setMass(double mass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
}

