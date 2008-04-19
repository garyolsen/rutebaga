package rutebaga.model.entity;

import java.awt.Image;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;

public class EntityType<T extends Entity> extends ConcreteInstanceType<T>
{
	private Image image;
	private ValueProvider<Entity> movementSpeed;

	public Image getImage()
	{
		return image;
	}

	public ValueProvider<Entity> getMovementSpeed()
	{
		return movementSpeed;
	}

	public void initialize(T entity)
	{
		entity.setAppearance(new Appearance(image));
		entity.setMovementSpeedStrat(movementSpeed);
	}
	
	public T create()
	{
		return (T) new CharEntity(this);
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public void setMovementSpeed(ValueProvider<Entity> movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}

}
