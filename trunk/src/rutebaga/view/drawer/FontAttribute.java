package rutebaga.view.drawer;

import java.awt.Font;

/**
 * Provides the ability for
 * {@link rutebaga.view.rwt.ViewComponent ViewComponents} to specify their Font
 * without directly calling the Drawer's individual methods.
 * 
 * @author Ryan
 * 
 */
public class FontAttribute implements Attribute {

	private Font font;

	/**
	 * Constructs a new FontAttribute with a default Font.
	 */
	public FontAttribute() {
		//TODO:provide a default font
	}

	/**
	 * Constructs a new FontAttribute using the provided Font.
	 * @param font A Font to use in this new FontAttribute.
	 */
	public FontAttribute(Font font) {
		this.font = font;
	}

	public void apply(Drawer drawer) {
		drawer.setFont(font);
	}

	/**
	 * Changes the Font that defines this FontAttribute.
	 * @param font The Font to use in this FontAttribute.
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Returns the Font being used by this FontAttribute.
	 * @return The current Font.
	 */
	public Font getFont() {
		return font;
	}

}
