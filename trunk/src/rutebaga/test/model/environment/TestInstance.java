package rutebaga.test.model.environment;

import rutebaga.model.environment.Instance;

public class TestInstance extends Instance
{
	public String name;

	public TestInstance(String name)
	{
		super();
		this.name = name;
	}

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
	public double getFriction()
	{
		return 0;
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
}
