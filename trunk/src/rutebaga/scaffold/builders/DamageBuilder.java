package rutebaga.scaffold.builders;

import rutebaga.commons.math.SymbolicFunction;
import rutebaga.model.entity.Damage;
import rutebaga.model.entity.DamageType;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.scaffold.MasterScaffold;

public class DamageBuilder extends ConfigFileBuilder
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/damage";
	}

	public Object create(String id)
	{
		return new Damage();
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		Damage damage = (Damage) object;
		
		DamageType type = (DamageType) getObjectFor(id, "type", scaffold);
		damage.setType(type);
		
		SymbolicFunction<Entity> function = getFunction(id, "function", scaffold);
		damage.setMagnitudeProvider(function);
		
		SymbolicFunction<Entity> lifetime = getFunction(id, "lifetime", scaffold);
		damage.setLifetimeProvider(lifetime);
		
		StatisticId dStat = (StatisticId) getObjectFor(id, "dstat", scaffold);
		damage.setDamagedStat(dStat);
	}

}
