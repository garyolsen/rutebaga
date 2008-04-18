package rutebaga.model.entity;

import java.awt.Image;

import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;

public class EntityType implements InstanceType<Entity>
{
	private Image image;

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public Entity makeInstance()
	{
		Entity entity = new CharEntity(this);
		entity.setAppearance(new Appearance(image));
		return entity;
	}

}
