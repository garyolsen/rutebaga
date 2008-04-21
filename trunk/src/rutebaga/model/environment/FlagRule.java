package rutebaga.model.environment;

import rutebaga.commons.logic.Rule;

public class FlagRule implements Rule<Instance>
{
	private String flag;
	private boolean value = true;

	public boolean isValue()
	{
		return value;
	}

	public void setValue(boolean value)
	{
		this.value = value;
	}

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
		return context.getFlag(flag) == value;
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
