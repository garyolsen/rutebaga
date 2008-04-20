package rutebaga.appearance;

import java.awt.Image;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;

public class AnimatedAppearanceDef implements
		AppearanceManagerDefinition<Instance>
{
	private Image[] images;
	private int wait;
	private Orientation orientation;
	private int offsetX;
	private int offsetY;

	public Image[] getImages()
	{
		return images;
	}

	public int getOffsetX()
	{
		return offsetX;
	}

	public int getOffsetY()
	{
		return offsetY;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	public int getWait()
	{
		return wait;
	}

	public AppearanceManager make(Instance instance)
	{
		Appearance[] appearances = new Appearance[images.length];
		for (int i=0; i<images.length; i++)
		{
			Image image = images[i];
			Appearance appearance = new Appearance(image);
			if (orientation != null)
				appearance.setOrientation(orientation);
			appearance.setOffsetX(offsetX);
			appearance.setOffsetY(offsetY);
			appearances[i] = appearance;
		}
		AnimatedAppearanceManager manager = new AnimatedAppearanceManager(appearances, wait);
		return manager;
	}

	public void setImages(Image[] images)
	{
		this.images = images;
	}

	public void setOffsetX(int offsetX)
	{
		this.offsetX = offsetX;
	}

	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}

	public void setWait(int wait)
	{
		this.wait = wait;
	}

}
