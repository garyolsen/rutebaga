package rutebaga.model.entity;

import java.awt.Image;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;

public class EntityType implements InstanceType<Entity>
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

	public Entity makeInstance()
	{
		Entity entity = new CharEntity(this);
		entity.setAppearance(new Appearance(image));
		entity.setMovementSpeedStrat(movementSpeed);
		return entity;
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
