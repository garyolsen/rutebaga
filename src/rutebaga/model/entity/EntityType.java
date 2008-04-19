package rutebaga.model.entity;

import java.awt.Image;

import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;

public class EntityType<T extends Entity> extends ConcreteInstanceType<T>
{
	private Image image;
	private ValueProvider<Entity> movementSpeed;
	private int radius;

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

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
		entity.setVisionBounds(new RectBounds2D(new Vector2D(radius,radius)));
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
