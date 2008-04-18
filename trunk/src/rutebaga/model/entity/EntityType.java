package rutebaga.model.entity;

import java.awt.Image;

import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.InstanceType;

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
		Entity entity = new CharEntity();
		entity.setAppearance(new Appearance());
		entity.getAppearance().setImage(image);
		return entity;
	}

}
