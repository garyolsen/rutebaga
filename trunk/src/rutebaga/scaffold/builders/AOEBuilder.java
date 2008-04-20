package rutebaga.scaffold.builders;

import rutebaga.model.effect.AreaEffectType;
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

		Appearance[] app = getAppearanceArray(id, "images", "[\\s\\t]",
				scaffold);
		int blockingRate = getInteger(id, "blocking");
		Integer framewait = getInteger(id, "framewait");
		Object[] effects = getObjectArray(id, "effects", "[\\s\\t]", scaffold);

		AreaEffectType type = (AreaEffectType) object;
		type.setBlockingRate(blockingRate);

		for (Object effect : effects)
			type.getEffects().add((EntityEffect) effect);

		type.setAppearances(app);
		if (framewait != null)
			type.setFrameWait(framewait);
	}

}
