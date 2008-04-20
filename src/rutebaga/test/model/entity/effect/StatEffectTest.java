package rutebaga.test.model.entity.effect;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.effect.StatEffect;
import rutebaga.model.entity.stats.StatisticId;

public class StatEffectTest
{

	/**
	 * @param args
	 */
	/*
	public static void main(String[] args)
	{
		Entity entity = new CharEntity();
		StatisticId stat = new StatisticId("some stat");

		rutebaga.commons.Log.log("before: " + entity.getStats().getValue(stat));

		StatEffect effect = new StatEffect(stat, 20);
		Object id = entity.accept(effect);

		rutebaga.commons.Log.log("accepted, before tick: "
				+ entity.getStats().getValue(stat));

		entity.tick();

		rutebaga.commons.Log.log("after tick: " + entity.getStats().getValue(stat));

		EntityEffect reverse = effect.getReverseEffect(id);
		entity.accept(reverse);

		rutebaga.commons.Log.log("accepted reverse, before tick: "
				+ entity.getStats().getValue(stat));

		entity.tick();

		rutebaga.commons.Log.log("after tick: " + entity.getStats().getValue(stat));
	}
	*/

}
