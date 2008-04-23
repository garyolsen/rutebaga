package rutebaga.model.entity;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import rutebaga.appearance.EntityAppearanceManager;
import rutebaga.commons.math.BidirectionalValueProvider;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.stats.ConcreteStatValue;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;

public class EntityType<T extends Entity> extends ConcreteInstanceType<T>
{
	private Appearance[][] walking;
	private Appearance[][] standing;
	private ValueProvider<Entity> movementSpeed;
	private ValueProvider<Entity> bargainSkillAmount;
	private BidirectionalValueProvider<Entity> skillPtStrat;
	private BidirectionalValueProvider<Entity> wallet;
	private ValueProvider<Entity> cooldownProvider;
	private ValueProvider<Entity> deadStrategy;
	private List<AbilityType> abilityTypes = new ArrayList<AbilityType>();
	private Team team;
	private Map<StatisticId, Double> initialStats = new HashMap<StatisticId, Double>();

	private int radius;
	private int decayTime;

	public T create()
	{
		return (T) new CharEntity(this);
	}

	public List<AbilityType> getAbilityTypes()
	{
		return abilityTypes;
	}

	public ValueProvider<Entity> getMovementSpeed()
	{
		return movementSpeed;
	}

	public int getRadius()
	{
		return radius;
	}

	public Appearance[][] getStanding()
	{
		return standing;
	}

	public Appearance[][] getWalking()
	{
		return walking;
	}

	public void initialize(T entity)
	{
		super.initialize(entity);

		if (standing != null && walking != null)
		{
			EntityAppearanceManager manager = new EntityAppearanceManager(
					entity);
			manager.setStanding(standing);
			manager.setWalking(walking);
			entity.setAppearanceManager(manager);
		}

		entity.setMovementSpeedStrat(movementSpeed);
		entity.setBargainSkill(bargainSkillAmount);
		entity.setCooldownProvider(cooldownProvider);
		entity.setSkillPtStrat(skillPtStrat);
		entity.setDeadStrategy(deadStrategy);
		entity.setTeam(team);
		entity.setWallet(wallet);
		
		for(StatisticId statId : initialStats.keySet())
		{
			entity.getStats().setBaseValue(statId, initialStats.get(statId));
		}
		
		if (abilityTypes != null)
			for (AbilityType type : abilityTypes)
			{
				if (type != null)
					entity.addAbility(type.makeAbility());
			}
		
		entity.setVisionBounds(new EllipseBounds2D(new Vector2D(radius, radius)));
	}

	public void setMovementSpeed(ValueProvider<Entity> movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	public void setStanding(Appearance[][] standing)
	{
		this.standing = standing;
	}

	public void setWalking(Appearance[][] walking)
	{
		this.walking = walking;
	}

	@Override
	public String toString()
	{
		return "EntityType: radius=" + radius + "; movementSpeed="
				+ movementSpeed + "; " + abilityTypes.size() + " abilities";
	}

	public ValueProvider<Entity> getBargainSkillAmount()
	{
		return bargainSkillAmount;
	}

	public void setBargainSkillAmount(ValueProvider<Entity> bargainSkillAmount)
	{
		this.bargainSkillAmount = bargainSkillAmount;
	}

	public ValueProvider<Entity> getDeadStrategy()
	{
		return deadStrategy;
	}

	public void setDeadStrategy(ValueProvider<Entity> deadStrategy)
	{
		this.deadStrategy = deadStrategy;
	}

	public BidirectionalValueProvider<Entity> getSkillPtStrat()
	{
		return skillPtStrat;
	}

	public void setSkillPtStrat(BidirectionalValueProvider<Entity> skillPtStrat)
	{
		this.skillPtStrat = skillPtStrat;
	}

	public void setDecayTime(int decayTime)
	{
		this.decayTime = decayTime;
	}

	public int getDecayTime()
	{
		return decayTime;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public BidirectionalValueProvider<Entity> getWallet()
	{
		return wallet;
	}

	public void setWallet(BidirectionalValueProvider<Entity> wallet)
	{
		this.wallet = wallet;
	}

	public Map<StatisticId, Double> getInitialStats()
	{
		return initialStats;
	}

	public ValueProvider<Entity> getCooldownProvider()
	{
		return cooldownProvider;
	}

	public void setCooldownProvider(ValueProvider<Entity> cooldownProvider)
	{
		this.cooldownProvider = cooldownProvider;
	}

}
