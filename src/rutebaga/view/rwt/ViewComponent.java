package rutebaga.view.rwt;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import rutebaga.commons.Bounds;
import rutebaga.view.drawer.Drawer;

public abstract class ViewComponent {

	private ViewComponent parent;
	@SuppressWarnings("unused") //TODO
	private boolean isVisible, hasFocus, dirtyBounds;
	private Point screenPosition = new Point(0,0);
	
	public abstract void draw( Drawer draw );

	public ViewComponent getParent() {
		return parent;
	}
	
	public Point getLocation() {
		return (Point) screenPosition.clone();
	}
	
	public int getX() {
		return screenPosition.x;
	}
	
	public int getY( ) {
		return screenPosition.y;
	}
	
	public void setLocation( Point p ) {
		this.screenPosition = p;
	}
	
	public void setLocation( int x, int y ) {
		screenPosition.setLocation(x, y);
	}

	public void setParent(ViewComponent parent) {
		this.parent = parent;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visibility) {
		this.isVisible = visibility;
	}

	public boolean hasFocus() {
		return hasFocus;
	}

	public void setHasFocus(boolean hasFocus) {
		this.hasFocus = hasFocus;
	}
	
	public Bounds getBounds() {
		//TODO
		return null;
	}
	
	protected void dirtyBounds() {
		dirtyBounds = true;
	}
	
	protected void processKeyEvent( KeyEvent event ) { eventReceived(event);}
	protected void processMouseEvent( MouseEvent event ) {eventReceived(event);}
	protected void processMouseMotionEvent( MouseEvent event ) {eventReceived(event);}
	
	private void eventReceived( AWTEvent e ) {
		System.out.println("ViewComponent: " + this + " received " + e);
	}
}
