package rutebaga.model.entity;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.math.SymbolicFunction;
import rutebaga.model.entity.effect.StatEffect;
import rutebaga.model.entity.stats.StatisticId;

/**
 * Classification of damage intended to be carried out upon an Entity.
 * 
 * Execution results in the execution of a StatEffect upon the entity. The
 * returned token for this effect is provided.
 * 
 * Damage has three components:
 *  - A symbolic function that provides the magnitude of the change to the
 * statistic;<br/> - The classification of this damage; - The statistic to be
 * damaged
 * 
 * The classification is not strictly necessary, but is provided so that
 * magnitude providers can be reused.
 * 
 * @author Gary LosHuertos
 * 
 */
public class Damage
{
	private DamageType type;
	private SymbolicFunction<Entity> magnitudeProvider;
	private StatisticId damagedStat;

	public DamageType getType()
	{
		return type;
	}

	public void setType(DamageType type)
	{
		this.type = type;
	}

	public SymbolicFunction<Entity> getMagnitudeProvider()
	{
		return magnitudeProvider;
	}

	public void setMagnitudeProvider(SymbolicFunction<Entity> magnitudeProvider)
	{
		this.magnitudeProvider = magnitudeProvider;
	}

	public StatisticId getDamagedStat()
	{
		return damagedStat;
	}

	public void setDamagedStat(StatisticId dStat)
	{
		this.damagedStat = dStat;
	}

	public Object execute(Entity entity, double amount)
	{
		Map<String, Double> symT = new HashMap<String, Double>();
		symT.put("amount", -amount);
		if (type != null)
			symT.put("defense", entity.getDamageResistance().getValue(type));
		double statAffectAmount = magnitudeProvider.getValue(symT, entity);
		EntityEffect eff = new StatEffect(damagedStat, statAffectAmount);
		return entity.accept(eff);
	}
}
