package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import rutebaga.view.drawer.*;

/**
 * Provides the standard ability of drawing a button out to the
 * {@link rutebaga.view.rwt.View} that can be depressed, firing a
 * {@link rutebaga.controller.command.Command}.
 * <p>
 * Responsibilities
 * <ul>
 * <li> Call the appropriate draw methods so that its appearance reflects its
 * current state.
 * <li>Maintain whether it is pressed or not
 * <li>Execute the Command that is associated with it(if any).
 * 
 * @author Ryan
 * 
 */
public class ButtonComponent extends ViewComponent {

	private String label;

	private boolean toggle = false;

	private int width, height;

	private CompositeAttribute untoggled, toggled;

	private FontAttribute font;//XXX: why isn't this at the ViewComponent level?

	/**
	 * Constructs an empty ButtonComponent.
	 */
	public ButtonComponent() {
		font = new FontAttribute(new Font("Arial", Font.PLAIN, 10));

		untoggled = new CompositeAttribute();
		untoggled.addAttribute(font);
		untoggled.addAttribute(new ColorAttribute(Color.LIGHT_GRAY));

		toggled = new CompositeAttribute();
		toggled.addAttribute(font);
		toggled.addAttribute(new ColorAttribute(Color.DARK_GRAY));

		width = 10;
		height = 10;
	}

	/**
	 * Constructs a ButtonComponent decorated with the specified String.
	 * @param label The string written over this ButtonComponent.
	 */
	public ButtonComponent(String label) {
		this();
		this.label = label;
	}

	public void draw(Drawer draw) {
/*		if (toggle)
			drawToggled(draw);
		else
			drawUntoggled(draw);
*/
		if (toggle)
			drawButtonState(draw,toggled);
		else
			drawButtonState(draw,untoggled);
		
	}

	private void drawButtonState(Drawer draw, CompositeAttribute state) {
		draw.setAttribute(state);
		draw.drawRectangle(getLocation(), width, height);

		Point centeredText = new Point();
		FontMetrics fm = draw.getFontMetrics();

		centeredText.x = this.getLocation().x + (width / 2)
				- (fm.stringWidth(label) / 2);
		centeredText.y = this.getLocation().y + (height / 2)
				+ (font.getFont().getSize() / 2);

		draw.drawString(centeredText, label);
	}

/*	private void drawUntoggled(Drawer draw) {
		draw.setAttribute(untoggled);
		draw.drawRectangle(getLocation(), width, height);

		Point centeredText = new Point();
		FontMetrics fm = draw.getFontMetrics();

		centeredText.x = this.getLocation().x + (width / 2)
				- (fm.stringWidth(label) / 2);
		centeredText.y = this.getLocation().y + (height / 2)
				+ (font.getFont().getSize() / 2);

		draw.drawString(centeredText, label);
	}

	private void drawToggled(Drawer draw) {
		draw.setAttribute(toggled);
		draw.drawRectangle(getLocation(), width, height);

		Point centeredText = new Point();
		FontMetrics fm = draw.getFontMetrics();

		centeredText.x = this.getLocation().x + (width / 2)
				- (fm.stringWidth(label) / 2);
		centeredText.y = this.getLocation().y + (height / 2)
				+ (font.getFont().getSize() / 2);

		draw.drawString(centeredText, label);
	}
*/
	protected void processMouseEvent(MouseEvent event) {
		if (event.getID() == MouseEvent.MOUSE_CLICKED)
			toggle = !toggle;
	}

}
