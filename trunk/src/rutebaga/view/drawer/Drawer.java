package rutebaga.view.drawer;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;

/**
 *
 * The Drawer abstract class allows for all information
 * being drawn out by the Windowing Toolkit to be
 * independent from the actual implementation of the
 * drawer (Graphics2D, Java3D, etc.). This allows
 * for reuse of the Windowing Toolkit on a variety
 * of mediums without having to revisit the actual
 * ViewComponents.
 * @author Ryan
 */
public abstract class Drawer {

	private Attribute attribute;
	
	public abstract void drawImage( Point p, Image img );
	public abstract void drawRectangle( Point p, int width, int height );
	public abstract void drawLine( Point begin, Point end );
	public abstract void drawPoint( Point p );
	public abstract void drawString( Point p, String string );
	
	protected abstract void setDrawColor( Color draw );
	protected abstract void setFont( Font font );
	protected abstract void setClipping( Shape clipping );
	
	public abstract FontMetrics getFontMetrics();
	
	public void setAttribute( Attribute attr ) {
		this.attribute = attr;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
}
