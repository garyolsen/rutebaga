package rutebaga.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import com.sun.corba.se.impl.orbutil.ObjectUtility;

import rutebaga.commons.Log;
import rutebaga.commons.UIDProvider;
import rutebaga.commons.math.BidirectionalValueProvider;
import rutebaga.commons.math.Bounds2D;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.Named;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.npc.Speech;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.storefront.EntityStoreFront;
import rutebaga.model.storefront.EntityStoreFront;
import rutebaga.model.storefront.Storefront;

/**
 * Entity stores the state related to an Entity in a physical environment.
 * Entities are objects defined by certain anthropomorphic attributes, such as
 * containing stats and an inventory. However, Entities are not necessarily
 * anthropomorphic themselves.
 * 
 * Entity is defined by its {@link EntityType}, to which it forwards most of
 * its behavior. Entity exists only to retain state. Subclasses of Entity that
 * require new operations should defer the execution of those operations to
 * their own subclass of {@link EntityType}.
 * 
 * @author Nick
 * @see EntityType
 */
public abstract class Entity<T extends Entity<T>> extends Instance<T> implements
		Named
{
	public static int SIGHT_RANGE = 7;

	private Map<Object, EntityEffect> effectQueue = new HashMap<Object, EntityEffect>();
	private Map<Object, EffectSource> effectSources = new HashMap<Object, EffectSource>();

	private Bounds2D visionBounds;
	private ConcreteVision vision;
	private CompoundVision compoundVision;

	private MutableVector2D currentWalkingDirection = new MutableVector2D(0, 0);

	private ValueProvider<Entity> movementSpeedStrat = new ConstantValueProvider<Entity>(
			0.0);

	private BidirectionalValueProvider<Entity> skillPtStrat;
	private BidirectionalValueProvider<Entity> wallet;
	private BidirectionalValueProvider<Entity> experience;
	private ValueProvider<Entity> cooldownProvider;
	private ValueProvider<Entity> experienceCalculation;

	private Vector2D facing = new Vector2D(0, 0);
	
	private int cooldown = -1;

	private Team team;
	private Storefront storeFront;
	private double money;

	private int decayTime = 100; // in milliseconds
	private long deathTimer = 0l;

	private Stack<Speech> speechStack = new Stack<Speech>();

	private SkillLevelManager skillLevelManager = new SkillLevelManager();

	private ValueProvider<Entity> bargainSkillAmount = new ConstantValueProvider<Entity>(
			1.0);
	/**
	 * Returns less than zero if the entity is dead.
	 */
	private ValueProvider<Entity> deadStrategy;

	// TODO move into AbilitySet
	private List<Ability> abilities = new ArrayList<Ability>();

	private Mount mount;

	public Entity(InstanceType<T> type)
	{
		super(type);
		visionBounds = new EllipseBounds2D(new Vector2D(SIGHT_RANGE,
				SIGHT_RANGE));
		// XXX: connascence of timing
		vision = new ConcreteVision(this, visionBounds);
		compoundVision = new CompoundVision(vision);
	}

	/**
	 * Queues an effect to be applied to this entity.
	 * 
	 * @param effect
	 *            the effect to apply
	 * @param source TODO
	 * @return the token identifying the effect's application
	 */
	public Object accept(EntityEffect effect, EffectSource source)
	{
		Object uid = UIDProvider.getUID();
		effectQueue.put(uid, effect);
		if(source == null)
		{
			new RuntimeException().printStackTrace();
		}
		effectSources.put(uid, source);
		return uid;
	}

	public void addAbility(Ability ability)
	{
		ability.setEntity(this);
		if (ability.getCategory() != null)
			skillLevelManager.getLevel(ability.getCategory());
		abilities.add(ability);
	}

	public void addToMoney(double value)
	{
		money = money + value;
	}

	public void addVision(Vision vision)
	{
		this.compoundVision.add(vision);
	}

	public void allocateSkillPoints(AbilityCategory category, int qty)
	{
		skillLevelManager.assign(category, qty);
		skillPtStrat.addTo(this, -qty);
	}

	@Override
	public final boolean blocks(Instance other)
	{
		return ObjectUtility.equals(other.getSetIdentifier(),
				InstanceSetIdentifier.ENTITY);
		// return false;
	}

	public boolean canSee(IntVector2D v)
	{
		return true;
//		if (mount != null)
//			return mount.canSee(v);
//
//		Vector2D dV = new Vector2D(v.getX(), v.getY());
//		return visionBounds.contains(dV.minus(this.getCoordinate()));
	}

	public void dismount(Mount mount)
	{
		mount.dismount(this);
	}

	public List<Ability> getAbilities()
	{
		return Collections.unmodifiableList(abilities);
	}

	@Override
	public Appearance getAppearance()
	{
		if (mount != null)
			return mount.getAppearance();

		return super.getAppearance();
	}

	public int getAvailableSkillPoints()
	{
		return (int) skillPtStrat.getValue(this);
	}

	public ValueProvider getBargainSkill()
	{
		return bargainSkillAmount;
	}

	public int getCooldown()
	{
		return cooldown;
	}

	public ValueProvider<Entity> getCooldownProvider()
	{
		return cooldownProvider;
	}

	@Override
	public Vector2D getCoordinate()
	{
		if (mount != null)
			return mount.getCoordinate();

		return super.getCoordinate();
	}

	public abstract Stats getDamageResistance();

	public ValueProvider<Entity> getDeadStrategy()
	{
		return deadStrategy;
	}

	public int getDecayTime()
	{
		return decayTime;
	}

	@Override
	public Environment getEnvironment()
	{
		if (mount != null)
			return mount.getEnvironment();

		return super.getEnvironment();
	}

	public BidirectionalValueProvider<Entity> getExperience()
	{
		return experience;
	}

	public Vector2D getFacing()
	{
		if (mount != null)
			return mount.getFacing();

		return facing;
	}

	public IntVector2D getFacingTile()
	{
		if (mount != null)
			mount.getFacingTile();

		return getEnvironment().getTileOf(
				getFacing().over(getFacing().getMagnitude()).plus(getTile()));
	}

	public abstract Inventory getInventory();

	@Override
	public double getLayer()
	{
		return DefaultLayers.GROUND.getLayer();
	}

	public double getMoneyAmount()
	{
		return money;
	}

	public Mount getMount()
	{
		return mount;
	}

	public double getMovementSpeed()
	{
		return this.movementSpeedStrat == null ? 0 : this.movementSpeedStrat
				.getValue(this);
	}

	public ConcreteVision getPrimaryVision()
	{
		return this.vision;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.ENTITY;
	}

	public int getSkillPoints(AbilityCategory category)
	{
		return (int) skillLevelManager.getLevel(category);
	}

	public BidirectionalValueProvider<Entity> getSkillPtStrat()
	{
		return skillPtStrat;
	}

	public Collection<Speech> getSpeech()
	{
		return Collections.unmodifiableCollection(speechStack);
	}

	public abstract Stats getStats();

	public Storefront getStoreFront()
	{
		return new EntityStoreFront(this);
	}

	public Team getTeam()
	{
		return this.team;
	}

	public Vision getVision()
	{
		return compoundVision;
	}

	public Bounds2D getVisionBounds()
	{
		return visionBounds;
	}

	public BidirectionalValueProvider<Entity> getWallet()
	{
		return wallet;
	}

	public boolean hasStoreFront()
	{
		return !(storeFront.equals(null));
	}

	public boolean isDead()
	{
		return deadStrategy == null ? false : deadStrategy.getValue(this) < 0;
	}

	@Override
	public boolean isMobile()
	{
		return !isDead();
	}

	public boolean isWalking()
	{
		// TODO implement
		return true;
	}

	public void mount(Mount mount)
	{
		mount.mount(this);
	}

	public void recieveSpeech(Entity entity, String speech)
	{
		speechStack.push(new Speech(entity, speech));
	}

	public void removeVision(Vision vision)
	{
		this.compoundVision.remove(vision);
	}

	public void resetCooldown()
	{
		cooldown = 1000;
	}

	public void setAppearance(Appearance appearance)
	{
		this.setAppearanceManager(new StaticAppearanceManager(appearance));
	}

	public void setBargainSkill(ValueProvider<Entity> bargainSkillAmount)
	{
		if (bargainSkillAmount != null)
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

	public void setFacing(Vector2D facing)
	{
		this.facing = facing;
	}

	public void setMount(Mount mount)
	{
		this.mount = mount;
	}

	public void setMovementSpeedStrat(ValueProvider<Entity> movementSpeedStrat)
	{
		this.movementSpeedStrat = movementSpeedStrat;
	}

	public void setSkillPtStrat(BidirectionalValueProvider<Entity> skillPtStrat)
	{
		this.skillPtStrat = skillPtStrat;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	public void setVisionBounds(Bounds2D visionBounds)
	{
		this.visionBounds = visionBounds;
		this.vision.setBounds(visionBounds);
	}

	public void setWallet(BidirectionalValueProvider<Entity> wallet)
	{
		this.wallet = wallet;
	}

	public void speak(Entity entity)
	{
		entity.recieveSpeech(this, "Hello.");
	}

	@Override
	public void tick()
	{
		if (isDead() && deathTimer == 0)
		{
			deathTimer = System.currentTimeMillis();
		}
		else if (isDead())
		{
			long now = System.currentTimeMillis();
			decayTime -= now - deathTimer;
			deathTimer = now;
		}
		
		cooldown -= cooldownProvider.getValue(this);
		if(cooldown < 0)
			cooldown = 0;

		applyWalkingImpulse(currentWalkingDirection);
		currentWalkingDirection = new MutableVector2D(0, 0);

		flushEffectQueue();
		clearSpeech();
		getVision().tick();

		if (decayTime <= 0)
		{
			this.getEnvironment().remove(this);
		}
	}

	public String toString()
	{
		return "Entity named " + getName();
	}

	public void walk(Vector2D direction)
	{
		if (mount != null)
		{
			mount.walk(direction);
		}
		else
		{
			this.currentWalkingDirection.accumulate(direction.over(direction
					.getMagnitude()));
		}
	}

	private void applyWalkingImpulse(MutableVector2D direction)
	{
		if (mount != null)
		{
			mount.walk(direction);
		}
		else
		{
			double magnitude = direction.getMagnitude();
			if (magnitude > 0.005)
				this.applyImpulse(direction.times(getMovementSpeed()
						/ magnitude));
			if (magnitude > 0.005)
				this.facing = direction;
		}
	}

	private void clearSpeech()
	{
		speechStack.clear();
	}

	private void flushEffectQueue()
	{
		for (Object id : getEffectQueue().keySet())
		{
			Log.log(getEffectQueue());
			EntityEffect effect = getEffectQueue().get(id);
			EffectSource source = effectSources.get(id);
			takeEffect(effect, source, id);
		}
		getEffectQueue().clear();
		effectSources.clear();
	}
	
	protected void takeEffect(EntityEffect effect, EffectSource source, Object id)
	{
		boolean dead = this.isDead();
		effect.affect(this, id);
		if(!dead && this.isDead())
		{
			// I just died
			if(source != null)
				source.onKill(this);
		}
	}

	protected Map<Object, EntityEffect> getEffectQueue()
	{
		return effectQueue;
	}

	public ValueProvider<Entity> getExperienceCalculation()
	{
		return experienceCalculation;
	}

	public void setExperienceCalculation(ValueProvider<Entity> experienceCalculation)
	{
		this.experienceCalculation = experienceCalculation;
	}
}
