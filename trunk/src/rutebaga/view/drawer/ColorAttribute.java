package rutebaga.view.drawer;

import java.awt.Color;

/**
 *
 * Color Attribute provides the ability for ViewComponents
 * to specify their color without directly calling 
 * the Drawer's individual methods.
  * @author Ryan
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
	 * @param color The Color you woud like to set this attribute to.
	 */
	public void setColor( Color color ) {
		this.color = color;
	}
	
	/**
	 * Returns the Color of this ColorAttribute.
	 * 
	 * @return Java.awt.Color The Color this attribute is currently set to.
	 */
	public Color getColor() {
		return color;
	}

}
