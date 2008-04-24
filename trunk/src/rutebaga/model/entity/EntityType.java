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
	private BidirectionalValueProvider<Entity> experience;
	private ValueProvider<Entity> cooldownProvider;
	private ValueProvider<Entity> experienceCalculation;
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

	public ValueProvider<Entity> getBargainSkillAmount()
	{
		return bargainSkillAmount;
	}

	public ValueProvider<Entity> getCooldownProvider()
	{
		return cooldownProvider;
	}

	public ValueProvider<Entity> getDeadStrategy()
	{
		return deadStrategy;
	}

	public int getDecayTime()
	{
		return decayTime;
	}

	public BidirectionalValueProvider<Entity> getExperience()
	{
		return experience;
	}

	public ValueProvider<Entity> getExperienceCalculation()
	{
		return experienceCalculation;
	}

	public Map<StatisticId, Double> getInitialStats()
	{
		return initialStats;
	}

	public ValueProvider<Entity> getMovementSpeed()
	{
		return movementSpeed;
	}

	public int getRadius()
	{
		return radius;
	}

	public BidirectionalValueProvider<Entity> getSkillPtStrat()
	{
		return skillPtStrat;
	}

	public Appearance[][] getStanding()
	{
		return standing;
	}

	public Appearance[][] getWalking()
	{
		return walking;
	}

	public BidirectionalValueProvider<Entity> getWallet()
	{
		return wallet;
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
		entity.setExperienceCalculation(experienceCalculation);
		entity.setTeam(team);
		entity.setExperience(experience);
		entity.setWallet(wallet);

		for (StatisticId statId : initialStats.keySet())
		{
			entity.getStats().setBaseValue(statId, initialStats.get(statId));
		}

		if (abilityTypes != null)
			for (AbilityType type : abilityTypes)
			{
				if (type != null)
					entity.addAbility(type.makeAbility());
			}

		entity
				.setVisionBounds(new EllipseBounds2D(new Vector2D(radius,
						radius)));
	}

	public void setBargainSkillAmount(ValueProvider<Entity> bargainSkillAmount)
	{
		this.bargainSkillAmount = bargainSkillAmount;
	}

	public void setCooldownProvider(ValueProvider<Entity> cooldownProvider)
	{
		this.cooldownProvider = cooldownProvider;
	}

	public void setDeadStrategy(ValueProvider<Entity> deadStrategy)
	{
		this.deadStrategy = deadStrategy;
	}

	public void setDecayTime(int decayTime)
	{
		this.decayTime = decayTime;
	}

	public void setExperience(BidirectionalValueProvider<Entity> experience)
	{
		this.experience = experience;
	}

	public void setExperienceCalculation(ValueProvider<Entity> experienceCalculation)
	{
		this.experienceCalculation = experienceCalculation;
	}

	public void setMovementSpeed(ValueProvider<Entity> movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	public void setSkillPtStrat(BidirectionalValueProvider<Entity> skillPtStrat)
	{
		this.skillPtStrat = skillPtStrat;
	}

	public void setStanding(Appearance[][] standing)
	{
		this.standing = standing;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	public void setWalking(Appearance[][] walking)
	{
		this.walking = walking;
	}

	public void setWallet(BidirectionalValueProvider<Entity> wallet)
	{
		this.wallet = wallet;
	}

	@Override
	public String toString()
	{
		return "EntityType: radius=" + radius + "; movementSpeed="
				+ movementSpeed + "; " + abilityTypes.size() + " abilities";
	}

}
