package rutebaga.view.rwt;

import java.awt.Image;

import rutebaga.view.drawer.Drawer;

/**
 * Allows for Images to be drawn to the screen.
 * 
 * @author Ryan
 * 
 */
public class ImageComponent extends ViewComponent {

	private Image image;

	/**
	 * Constructs a new ImageComponent for viewing the specified Image.
	 * @param image The Image that this ImageComponent should draw.
	 */
	public ImageComponent(Image image) {
		this.image = image;
	}

	public void draw(Drawer draw) {
		draw.drawImage(this.getLocation(), image);
	}

}
