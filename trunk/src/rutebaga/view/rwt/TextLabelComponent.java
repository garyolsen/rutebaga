package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;

import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.CompositeAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.drawer.FontAttribute;

/**
 * Provides the simple ability of displaying a
 * String to the view with whatever Font or Color attributes that have been
 * specified.
 * 
 * @author Ryan
 * 
 */
public class TextLabelComponent extends ViewComponent {

	private String label;

	private FontAttribute font;

	private ColorAttribute color;

	private CompositeAttribute composite;

	private TextLabelComponent() {
		font = new FontAttribute(new Font("Arial", Font.PLAIN, 10));
		color = new ColorAttribute(Color.BLACK);
		composite = new CompositeAttribute();
		composite.addAttribute(font);
		composite.addAttribute(color);
	}

	/**
	 * Constructs a new TextLabelComponent for displaying the specified String.
	 * @param label Text to display.
	 */
	public TextLabelComponent(String label) {
		this();
		this.label = label;
	}

	public void draw(Drawer draw) {
		draw.setAttribute(composite);
		draw.drawString(this.getLocation(), label);
	}

	/**
	 * Changes the Font used to draw this TextLabelComponent.
	 * @param font The font to use.
	 */
	public void setFont(Font font) {
		this.font.setFont(font);
	}

	/**
	 * Changes the Color to draw this TextLabelComponent in.
	 * @param color The new Color of the text.
	 */
	public void setFontColor(Color color) {
		this.color.setColor(color);
	}

	/**
	 * Changes the text stored in this TextLabelComponent.
	 * @param label The new text.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

}
