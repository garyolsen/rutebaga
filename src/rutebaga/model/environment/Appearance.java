package rutebaga.model.environment;

import java.awt.Image;

public class Appearance
{

	private Instance instance;
	private Image image;

	public Appearance(Instance instance)
	{
		super();
		this.instance = instance;
	}

	public void tick()
	{
		// FIXME temporary
	}

	public Image getImage()
	{
		return image;
	}
	
	public void setImage(Image image) 
	{
		this.image = image;
	}

	public Instance getInstance()
	{
		return instance;
	}

}
