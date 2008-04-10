package rutebaga.view.drawer;

import java.awt.Color;

public class ColorAttribute implements Attribute {
	
	private Color color;
	
	public ColorAttribute() {
		
	}
	
	public ColorAttribute( Color color ) {
		this.color = color;
	}

	public void apply(Drawer drawer) {
		drawer.setDrawColor( color );
	}
	
	public void setColor( Color color ) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

}
