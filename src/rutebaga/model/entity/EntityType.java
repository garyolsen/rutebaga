package rutebaga.model.entity;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import rutebaga.appearance.EntityAppearanceManager;
import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;

public class EntityType<T extends Entity> extends ConcreteInstanceType<T>
{
	private Appearance[][] walking;
	private Appearance[][] standing;
	private ValueProvider<Entity> movementSpeed;
	private List<AbilityType> abilityTypes = new ArrayList<AbilityType>();
	private int radius;

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
		EntityAppearanceManager manager = new EntityAppearanceManager(entity);
		manager.setStanding(standing);
		manager.setWalking(walking);
		entity.setAppearanceManager(manager);
		entity.setMovementSpeedStrat(movementSpeed);
		for(AbilityType type : abilityTypes)
		{
			entity.addAbility(type.makeAbility());
		}
		entity.setVisionBounds(new RectBounds2D(new Vector2D(radius, radius)));
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
		return "EntityType: radius=" + radius + "; movementSpeed=" + movementSpeed + "; " + abilityTypes.size() + " abilities";
	}

}
