package rutebaga.view.drawer;

import java.awt.Color;

/**
 * @author Ryan
 *
 * Color Attribute provides the ability for ViewComponents
 * to specify their color without directly calling 
 * the Drawer's individual methods.
 */
public class ColorAttribute implements Attribute {
	
	/**
	 * The Color of this ColorAttribute.
	 */
	private Color color;
	
	public ColorAttribute() {
		
	}
	
	public ColorAttribute( Color color ) {
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see rutebaga.view.drawer.Attribute#apply(rutebaga.view.drawer.Drawer)
	 */
	public void apply(Drawer drawer) {
		drawer.setDrawColor( color );
	}
	
	/**
	 * Set the Color of this ColorAttribute.
	 * 
	 * @param Color
	 */
	public void setColor( Color color ) {
		this.color = color;
	}
	
	/**
	 * Returns the Color of this ColorAttribute.
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

}
