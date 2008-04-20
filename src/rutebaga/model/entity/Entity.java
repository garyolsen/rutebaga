package rutebaga.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rutebaga.commons.UIDProvider;
import rutebaga.commons.math.BidirectionalValueProvider;
import rutebaga.commons.math.Bounds2D;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.Named;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.StaticAppearanceManager;

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
public abstract class Entity<T extends Entity<T>> extends Instance<T> implements Named
{
	public static int SIGHT_RANGE = 7;
	
	private Map<Object, EntityEffect> effectQueue = new HashMap<Object, EntityEffect>();

	private Bounds2D visionBounds;
	private Vision vision;
	
	private ValueProvider<Entity> movementSpeedStrat = new ConstantValueProvider<Entity>(0.0);

	private String name;
	private double money;
	
	public String toString()
	{
		return "Entity named " + name;
	}
	
	//TODO move into AbilitySet
	private List<Ability> abilities = new ArrayList<Ability>();
	
	public List<Ability> getAbilities()
	{
		return Collections.unmodifiableList(abilities);
	}
	
	public void addAbility(Ability ability)
	{
		ability.setEntity(this);
		abilities.add(ability);
	}

	public Entity(InstanceType<T> type)
	{
		super(type);
		visionBounds = new RectBounds2D(new Vector2D(SIGHT_RANGE, SIGHT_RANGE));
		// XXX: connascence of timing
		vision = new Vision(this);
	}
	
	/**
	 * Queues an effect to be applied to this entity.
	 * 
	 * @param effect
	 *            the effect to apply
	 * @return the token identifying the effect's application
	 */
	public Object accept(EntityEffect effect)
	{
		Object uid = UIDProvider.getUID();
		effectQueue.put(uid, effect);
		return uid;
	}

	public abstract Inventory getInventory();

	@Override
	public double getLayer()
	{
		return DefaultLayers.GROUND.getLayer();
	}

	public String getName()
	{
		return name;
	}

	public abstract Stats getStats();

	public Vision getVision()
	{
		return vision;
	}

	public Bounds2D getVisionBounds()
	{
		return visionBounds;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setVisionBounds(Bounds2D visionBounds)
	{
		this.visionBounds = visionBounds;
		this.vision = new Vision(this);
	}
	
	public boolean canSee(IntVector2D v) {
		Vector2D dV = new Vector2D(v.getX(), v.getY());
		return visionBounds.contains(dV.minus(this.getCoordinate()));
	}

	@Override
	public void tick()
	{
		flushEffectQueue();
		getVision().tick();
	}
	
	private void flushEffectQueue()
	{
		for (Object id : getEffectQueue().keySet())
		{
			getEffectQueue().get(id).affect(this, id);
		}
		getEffectQueue().clear();
	}

	protected Map<Object, EntityEffect> getEffectQueue()
	{
		return effectQueue;
	}

	@Override
	public final boolean blocks(Instance other)
	{
		return other.getSetIdentifier().equals(InstanceSetIdentifier.ENTITY);
//		return false;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.ENTITY;
	}

	public void setMovementSpeedStrat(ValueProvider<Entity> movementSpeedStrat)
	{
		this.movementSpeedStrat = movementSpeedStrat;
	}
	
	public void walk(Vector2D direction)
	{
		double magnitude = direction.getMagnitude();
		this.applyImpulse(direction.times(movementSpeedStrat.getValue(this)/magnitude));
	}

	public void setAppearance(Appearance appearance)
	{
		this.setAppearanceManager(new StaticAppearanceManager(appearance));
	}
	
	public double getMovementSpeed()
	{
		return this.movementSpeedStrat.getValue(this);
	}
	
	public BidirectionalValueProvider<Entity> getWallet()
	{
		return new Wallet();
	}

	public void addToMoney(double value) {
		money = money + value;
	}

	public double getMoneyAmount() {
		return money;
	}
	
	public boolean isWalking()
	{
		//TODO implement
		return true;
	}
}
