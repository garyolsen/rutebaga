package rutebaga.scaffold.builders;

import rutebaga.commons.logic.Rule;
import rutebaga.model.effect.AreaEffectType;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.scaffold.MasterScaffold;

public class AOEBuilder extends InstanceBuilder
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/areaeffects";
	}

	public Object create(String id)
	{
		return new AreaEffectType();
	}

	@Override
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);

		Object[] effects = getObjectArray(id, "effects", "[\\s\\t]", scaffold);
		Object[] rules = getObjectArray(id, "rules", "[\\s\\t]", scaffold);

		AreaEffectType type = (AreaEffectType) object;

		for (Object effect : effects)
			type.getEffects().add((EntityEffect) effect);

		for (Object rule : rules)
			type.getRules().add((Rule<Entity>) rule);
	}

}
