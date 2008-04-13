package rutebaga.model.map;

import rutebaga.model.environment.Instance;

public class Tile extends Instance
{
	@Override
	public boolean blocks(Instance other)
	{
		// TODO add blocking logic (glosh)
		return false;
	}

	@Override
	public double getFriction()
	{
		// TODO add friction logic (glosh)
		return 0;
	}

	@Override
	public double getMass()
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public void tick()
	{
	}

}
