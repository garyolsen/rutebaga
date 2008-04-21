package rutebaga.model.entity.effect;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;

/**
 * Sets the flag of an entity based on a value provider.
 * 
 * The value will be set as follows based on the value provider's return value:
 *
 * -  equal to 0 --> no change
 * -  less than 0 --> false
 * -  greater than 0 --> true
 * 
 * 
 * @author Gary
 * 
 */
public class FlagEffect extends EntityEffect
{
	private String flag;
	private ValueProvider<Entity> valueProvider;

	public FlagEffect()
	{
		super();
	}

	public FlagEffect(String flag, ValueProvider<Entity> valueProvider)
	{
		super();
		this.flag = flag;
		this.valueProvider = valueProvider;
	}

	public String getFlag()
	{
		return flag;
	}

	public ValueProvider<Entity> getValueProvider()
	{
		return valueProvider;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public void setValueProvider(ValueProvider<Entity> valueProvider)
	{
		this.valueProvider = valueProvider;
	}

	@Override
	protected void affect(Entity entity, Object id)
	{
		double value = valueProvider.getValue(entity);
		int comp = Double.compare(value, 0);
		if (comp == 0)
			return;
		entity.setFlag(flag, comp > 0);
	}
}
