package rutebaga.game.builders;

import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Damage;
import rutebaga.model.environment.MovementAttributes;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbstractAbilityBuilder;
import rutebaga.scaffold.builders.AppearanceDefFactory;
import rutebaga.scaffold.builders.ConfigFileBuilder;
import rutebaga.test.model.ability.CheeseArrowAbilityType;
import rutebaga.test.model.effect.RandomEffectType;

public class CustomAbilityBuilder extends AbstractAbilityBuilder
{
	@Override
	protected String getDefaultFileName()
	{
		return "config/agabatur/abilities";
	}

	public Object create(String id)
	{
		String typedef = getProperty(id, "type");
		if ("~abEgg".equals(typedef))
		{
			return new CheeseArrowAbilityType();
		}
		return null;
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		String typedef = getProperty(id, "type");
		if ("~abEgg".equals(typedef))
		{
			CheeseArrowAbilityType type = (CheeseArrowAbilityType) object;

			{
				RandomEffectType effectType = new RandomEffectType();

				ValueProvider speed = getValueProvider(id, "speed", scaffold);
				effectType.setSpeed(speed);

				int lifetime = getInteger(id, "lifetime");
				effectType.setLifetime(lifetime);

				String appDefStr = getProperty(id, "appearance");
				AppearanceManagerDefinition def = AppearanceDefFactory
						.getInstance().get(appDefStr, scaffold);
				effectType.setAppearanceDefinition(def);

				Damage damage = (Damage) scaffold
						.get(getProperty(id, "damage"));
				if (damage != null)
				{
					double damageAmount = Double.parseDouble(getProperty(id,
							"dmgamount"));
					effectType.setDamage(damage);
					effectType.setDamageAmount(damageAmount);
				}

				effectType.setMovementAttributes(new MovementAttributes(true));

				type.setEffectType(effectType);
			}

		}
	}
}
