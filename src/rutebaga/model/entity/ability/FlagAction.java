package rutebaga.model.entity.ability;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.Instance;

public class FlagAction implements AbilityAction<Instance>
{
	private ValueProvider<Entity> value;
	private String flag;

	public void act(Ability<? extends Instance> ability, Instance target)
	{
		double actValue = value.getValue(ability.getEntity());
		int cmp = Double.compare(actValue, 0);
		if(cmp != 0)
			target.setFlag(flag, cmp > 0);
	}

	public String getFlag()
	{
		return flag;
	}

	public ValueProvider<Entity> getValue()
	{
		return value;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public void setValue(ValueProvider<Entity> value)
	{
		this.value = value;
	}

}
