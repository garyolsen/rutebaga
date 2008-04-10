package rutebaga.view.rwt;

import java.awt.Image;

import rutebaga.view.drawer.Drawer;

public class ImageComponent extends ViewComponent {
	
	private Image image;

	public ImageComponent( Image image ) {
		this.image = image;
	}
	
	public void draw(Drawer draw) {
		draw.drawImage(this.getLocation(), image);
	}

}
