package rutebaga.view.rwt;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import rutebaga.view.drawer.Drawer;

/**
 * Provides the basic functionality of decorating a ViewComponent with
 * additional embellishments (for example, ScrollBars ).
 * 
 * The ComponentDecorator is different from that of
 * {@link ViewCompositeComponent} in primarily three ways:
 * <p>
 * <ula>
 * <li> The ComponentDecorator does not have a parent type relationship with the
 * object that it is decorating.
 * <li>The ComponentDecorator does not redefine the coordinate system for the
 * component it is decorating, it chooses how to position the decorate component
 * based on its own internal formatting strategy.
 * <li>The ComponentDecorator implies more visual information will be rendered
 * to the screen than merely the ViewComponent.
 * 
 * @author Ryan
 * 
 */
public abstract class ComponentDecorator extends ViewComponent
{

	private final ViewComponent decorated;

	/**
	 * Constructs a ComponentDecorator for decorating the specified
	 * ViewComponent.
	 * 
	 * @param decorated
	 *            The ViewComponent to be decorated.
	 */
	public ComponentDecorator(ViewComponent decorated)
	{
		this.decorated = decorated;
		decorated.setParent(this);
	}

	@Override
	public void draw(Drawer draw)
	{
		decorated.draw(draw);
	}
	
	public ViewComponent getDecoratedComponent() {
		return decorated;
	}
	
	protected boolean processKeyEvent( KeyEvent event ) { 
		return decorated.processKeyEvent(event);
	}
	protected boolean processMouseEvent( MouseEvent event ) {
		return decorated.processMouseEvent(event);
	}
	protected boolean processMouseMotionEvent( MouseEvent event ) {
		return decorated.processMouseMotionEvent(event);
	}


}
