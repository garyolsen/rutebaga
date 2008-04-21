package rutebaga.model.environment;

import rutebaga.commons.logic.Rule;

public class FlagRule implements Rule<Instance>
{
	private String flag;

	public FlagRule()
	{
		super();
	}

	public FlagRule(String flag)
	{
		super();
		this.flag = flag;
	}

	public boolean determine(Instance context)
	{
		return context.getFlag(flag);
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

}
