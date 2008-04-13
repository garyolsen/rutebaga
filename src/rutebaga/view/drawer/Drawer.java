package rutebaga.view.drawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;

/**
 * 
 * Allows for all information being drawn out by the Windowing Toolkit to be
 * independent from the actual implementation of the Drawer (Graphics2D, Java3D,
 * etc.). This allows for reuse of the Windowing Toolkit on a variety of mediums
 * without having to revisit the actual
 * {@link rutebaga.view.rwt.ViewComponent ViewComponents}.
 * 
 * @author Ryan
 */
public abstract class Drawer {

	private Attribute attribute;

	/**
	 * Draws an Image at specific location.
	 * 
	 * @param p
	 *            Where the upper-left corner of the Image will be located.
	 * @param img
	 *            The Image to be drawn
	 */
	public abstract void drawImage(Point p, Image img);

	/**
	 * Draws a rectangle based on the current {@link Attribute Attriubtes}.
	 * 
	 * @param p
	 *            Where the upper-left corner of the rectangle will be located.
	 * @param width
	 *            The width of the rectangle.
	 * @param height
	 *            The height of the rectangle.
	 */
	public abstract void drawRectangle(Point p, int width, int height);

	/**
	 * Draws a line based on the current {@link Attribute Attriubtes} from one
	 * point to another.
	 * 
	 * @param begin
	 *            One end of the line.
	 * @param end
	 *            One end of the line.
	 */
	public abstract void drawLine(Point begin, Point end);

	/**
	 * Draws a point based on the current {@link Attribute Attriubtes}.
	 * 
	 * @param p
	 *            The location of the point.
	 */
	public abstract void drawPoint(Point p);

	/**
	 * Draws a text String based on the current {@link Attribute Attriubtes}.
	 * 
	 * @param p
	 *            Where the lower-left corner of the text will be located.
	 * @param string
	 *            The String of text to be rendered.
	 */
	public abstract void drawString(Point p, String string);

	protected abstract void setDrawColor(Color draw);

	protected abstract void setFont(Font font);

	protected abstract void setClipping(Shape clipping);

	/**
	 * Returns a FontMetrics based on the current {@link Attribute Attriubtes}.
	 * 
	 * @return A FontMetrics based on the current Font defined by the current
	 *         Attribute.
	 */
	public abstract FontMetrics getFontMetrics();

	/**
	 * Sets the current {@link Attribute Attriubtes}, discarding any previously
	 * set Attriubtes. To set more than one Attribute remember to use a
	 * {@link CompositeAttribute}.
	 * 
	 * @param attr
	 *            The Attribute to set for this Drawer.
	 */
	public void setAttribute(Attribute attr) {
		this.attribute = attr;
	}

	/**
	 * Returns the current {@link Attribute Attriubtes}.
	 * @return The Attribute currently being used in this Drawer.
	 */
	public Attribute getAttribute() {
		return attribute;
	}
}
