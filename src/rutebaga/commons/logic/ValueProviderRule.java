package rutebaga.commons.logic;

import rutebaga.commons.math.ValueProvider;

public class ValueProviderRule<T> implements Rule<T>
{
	private ValueProvider<T> backing;

	public ValueProviderRule(ValueProvider<T> backing)
	{
		super();
		this.backing = backing;
	}

	public boolean determine(T context)
	{
		return backing.getValue(context) > 0;
	}

}
