package rutebaga.view.rwt;

import java.awt.Image;

import rutebaga.view.drawer.Drawer;

/**
 * Allows for Images to be drawn to the screen.
 * 
 * @author Ryan
 * 
 */
public class ImageComponent extends ViewComponent
{

	private Image image;

	/**
	 * Constructs a new ImageComponent for viewing the specified Image.
	 * 
	 * @param image
	 *            The Image that this ImageComponent should draw.
	 */
	public ImageComponent(Image image)
	{
		this.image = image;
		
		image.setAccelerationPriority(1.0f);
		
		this.setBounds(0, 0, image.getWidth(null), image.getHeight(null));
		
	}

	@Override
	public void draw(Drawer draw)
	{
		draw.drawImage(getLocation(), image);
		//draw.drawRectangle(getLocation(), getWidth(), getHeight());
	}

}
