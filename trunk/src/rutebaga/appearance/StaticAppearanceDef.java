package rutebaga.appearance;

import java.awt.Image;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;

public class StaticAppearanceDef implements AppearanceManagerDefinition<Instance>
{
	private Image image;
	private Orientation orientation;
	private int offsetX;
	private int offsetY;

	public Image getImage()
	{
		return image;
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

	public AppearanceManager make(Instance instance)
	{
		Appearance app = new Appearance(image);
		if(orientation != null) app.setOrientation(orientation);
		app.setOffsetX(offsetX);
		app.setOffsetY(offsetY);
		return new StaticAppearanceManager(app);
	}

	public void setImage(Image image)
	{
		this.image = image;
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

}
