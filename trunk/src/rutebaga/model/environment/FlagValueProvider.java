package rutebaga.model.environment;

import rutebaga.commons.math.ValueProvider;

/**
 * Returns 1 if a particular flag of an entity is set; -1 otherwise.
 * 
 * @author Gary LosHuertos
 *
 */
public class FlagValueProvider extends ValueProvider<Instance> 
{
	private String flag;

	public FlagValueProvider()
	{
		super();
	}

	public FlagValueProvider(String flag)
	{
		super();
		this.flag = flag;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	@Override
	public double getValue(Instance t)
	{
		return t.getFlag(flag) ? 1 : -1;
	}
}
