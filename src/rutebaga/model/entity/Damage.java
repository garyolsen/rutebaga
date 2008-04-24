package rutebaga.model.entity;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.math.SymbolicFunction;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.effect.TemporaryEffect;
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
	private SymbolicFunction<Entity> lifetimeProvider;

	public Object execute(Entity entity, double amount, EffectSource source)
	{
		Map<String, Double> symT = new HashMap<String, Double>();
		symT.put("amount", -amount);
		if (type != null)
			symT.put("defense", entity.getDamageResistance().getValue(type));
		double statAffectAmount = magnitudeProvider.getValue(symT, entity);
		ReversibleEntityEffect eff = new StatEffect(damagedStat, statAffectAmount);
		if(lifetimeProvider != null)
		{
			double lifetime = lifetimeProvider.getValue(symT, entity);
			TemporaryEffect effect = new TemporaryEffect(null);
			effect.setTarget(entity);
			effect.setEffect(eff);
			effect.setLifetime((int) lifetime);
			entity.getEnvironment().add(effect, new Vector2D(0, 0));
			return effect.start();
		}
		return entity.accept(eff, source);
	}

	public StatisticId getDamagedStat()
	{
		return damagedStat;
	}

	public SymbolicFunction<Entity> getLifetimeProvider()
	{
		return lifetimeProvider;
	}

	public SymbolicFunction<Entity> getMagnitudeProvider()
	{
		return magnitudeProvider;
	}

	public DamageType getType()
	{
		return type;
	}

	public void setDamagedStat(StatisticId dStat)
	{
		this.damagedStat = dStat;
	}

	public void setLifetimeProvider(SymbolicFunction<Entity> lifetimeProvider)
	{
		this.lifetimeProvider = lifetimeProvider;
	}

	public void setMagnitudeProvider(SymbolicFunction<Entity> magnitudeProvider)
	{
		this.magnitudeProvider = magnitudeProvider;
	}

	public void setType(DamageType type)
	{
		this.type = type;
	}
}
