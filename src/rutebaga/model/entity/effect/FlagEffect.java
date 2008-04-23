package rutebaga.model.entity.effect;

import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;

/**
 * Sets the flag of an entity based on a value provider.
 * 
 * The value will be set as follows based on the value provider's return value:
 *  - equal to 0 --> no change - less than 0 --> false - greater than 0 --> true
 * 
 * 
 * @author Gary
 * 
 */
public class FlagEffect extends ReversibleEntityEffect
{
	private String flag;
	private ValueProvider<Entity> valueProvider;

	public FlagEffect(String flag)
	{
		super();
		this.flag = flag;
	}

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
		if (valueProvider != null)
		{
			double value = valueProvider.getValue(entity);
			int comp = Double.compare(value, 0);
			if (comp == 0)
				return;
			entity.setFlag(flag, comp > 0);
		}
		else
		{
			entity.setFlag(flag, true);
		}
	}

	@Override
	public EntityEffect getReverseEffect(Object id)
	{
		FlagEffect effect = new FlagEffect();
		effect.setFlag(flag);
		effect.setValueProvider(new ConstantValueProvider<Entity>(-1));
		return effect;
	}
}
