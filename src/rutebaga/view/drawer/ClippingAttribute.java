package rutebaga.view.drawer;

import java.awt.Shape;

public class ClippingAttribute implements Attribute {
	
	private Shape clip;

	public ClippingAttribute(Shape clip) {
		this.clip = clip;
	}
	
	public void apply(Drawer drawer) {
		drawer.setClipping(clip);
	}

}
