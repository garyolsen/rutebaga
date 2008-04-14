package rutebaga.view.drawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;

/**
 * 
 * The Graphics2DDrawer uses Java.awt.Graphics2D to draw primitives and text to
 * the screen.
 * 
 * @author Ryan
 */
public class Graphics2DDrawer extends Drawer {

	private Graphics2D g2d;

	/**
	 * Constructs a new Graphics2DDrawer using the specified Graphics2D.
	 * 
	 * @param g2d
	 *            The Graphics2D to be used by this Graphics2DDrawer. Previous
	 *            settings will be overridden by the Drawer's
	 *            {@link Attribute Attriubtes}.
	 */
	public Graphics2DDrawer(Graphics2D g2d) {
		this.g2d = g2d;
	}

	/**
	 * Returns the Graphics2D being used by this Graphics2DDrawer.
	 * @return The Graphics2D being used by this Graphics2DDrawer.
	 */
	public Graphics2D getGraphics2D() {
		return g2d;
	}

	/**
	 * Replaces the Graphics2D being used by this Graphics2DDrawer.
	 * @param g2d A Graphics2D to be used by this Graphics2DDrawer.
	 */
	public void setGraphcis2D(Graphics2D g2d) {
		this.g2d = g2d;
	}

	private void applyAttribute() {
		this.getAttribute().apply(this);
	}

	public void drawImage(Point p, Image img) {
		applyAttribute();
		g2d.drawImage(img, p.x, p.y, p.x + img.getWidth(null), p.y
				+ img.getHeight(null), null);
	}

	public void drawLine(Point begin, Point end) {
		applyAttribute();
		g2d.drawLine(begin.x, begin.y, end.x, end.y);
	}

	public void drawPoint(Point p) {
		applyAttribute();
		g2d.drawRect(p.x, p.y, 1, 1);
	}

	public void drawRectangle(Point p, int width, int height) {
		applyAttribute();
		g2d.drawRect(p.x, p.y, width, height);
	}

	public void drawString(Point p, String string) {
		applyAttribute();
		g2d.drawString(string, p.x, p.y);
	}

	protected void setClipping(Shape clipping) {
		g2d.setClip(clipping);
	}

	protected void setDrawColor(Color draw) {
		g2d.setColor(draw);
	}

	protected void setFont(Font font) {
		g2d.setFont(font);
	}

	public FontMetrics getFontMetrics() {
		return g2d.getFontMetrics();
	}

}
