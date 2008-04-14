package rutebaga.view.rwt;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import rutebaga.commons.Vector;

/**
 * Captures Java.awt.AWTEvents. These captured events can then be distributed to
 * the various {@link ViewComponent ViewComponents} that have been registered
 * with the EventDispatcher. The {@link rutebaga.view.ViewFacade} is responsible
 * for registering new ViewComponents with the EventDispatcher if they want to
 * receive inputs from the user.
 * 
 * @author Ryan
 * 
 */
public class EventDispatcher implements KeyListener, MouseListener,
		MouseMotionListener {

	private Set<ViewComponent> registeredComponents;

	private Set<ViewComponent> containsMouse;

	private Vector vector;

	/**
	 * Constructs a new EventDispatcher that doesn't forward events to any
	 * ViewComponents.
	 */
	public EventDispatcher() {
		registeredComponents = new HashSet<ViewComponent>();
		containsMouse = new HashSet<ViewComponent>();
		vector = new Vector(2);
	}

	/**
	 * Quits on the escape key. Otherwise passes the event to any of the
	 * registered ViewComponents with focus.
	 * 
	 * @param A
	 *            KeyPressed Event.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		for (ViewComponent vc : registeredComponents)
			if (vc.hasFocus())
				vc.processKeyEvent(e);

		eventReceivedTest(e);
	}

	/**
	 * Passes the event to any of the registered ViewComponents with focus.
	 * 
	 * @param A
	 *            KeyReleased Event.
	 */
	public void keyReleased(KeyEvent e) {
		for (ViewComponent vc : registeredComponents)
			if (vc.hasFocus())
				vc.processKeyEvent(e);

		eventReceivedTest(e);
	}

	/**
	 * Passes the event to any of the registered ViewComponents with focus.
	 * 
	 * @param A
	 *            KeyTyped Event.
	 */
	public void keyTyped(KeyEvent e) {
		for (ViewComponent vc : registeredComponents)
			if (vc.hasFocus())
				vc.processKeyEvent(e);

		eventReceivedTest(e);
	}

	/**
	 * Passes the event to any of the registered ViewComponents containing the
	 * mouse.
	 * 
	 * @param A
	 *            MouseClicked Event.
	 */
	public void mouseClicked(MouseEvent e) {
		for (ViewComponent vc : containsMouse)
			vc.processMouseEvent(e);

		eventReceivedTest(e);
	}

	/**
	 * Passes the event to any of the registered ViewComponents containing the
	 * mouse.
	 * 
	 * @param A
	 *            MousePressed Event.
	 */
	public void mousePressed(MouseEvent e) {
		for (ViewComponent vc : containsMouse)
			vc.processMouseEvent(e);

		eventReceivedTest(e);
	}

	/**
	 * Passes the event to any of the registered ViewComponents containing the
	 * mouse.
	 * 
	 * @param A
	 *            MouseReleased Event.
	 */
	public void mouseReleased(MouseEvent e) {
		for (ViewComponent vc : containsMouse)
			vc.processMouseEvent(e);

		eventReceivedTest(e);
	}

	/**
	 * Does nothing. Required by MouseMotionListener.
	 * 
	 * @param A
	 *            MouseEntered Event.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Does nothing. Required by MouseMotionListener.
	 * 
	 * @param A
	 *            MouseExited Event.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Passes the event to the registered ViewComponent being dragged.
	 * 
	 * @param A
	 *            MouseDragged Event.
	 */
	public void mouseDragged(MouseEvent e) {

		for (ViewComponent vc : registeredComponents)
			if (vc.getBounds().contains(vector)) { // TODO change vector to do
				// real mouse click
				// detection once vector is
				// done
				if (!containsMouse.contains(vc)) {
					containsMouse.add(vc);
					mouseEntered(vc, e);
				} else {
					vc.processMouseMotionEvent(e);
				}
			} else {
				if (containsMouse.contains(vc)) {
					containsMouse.remove(vc);
					mouseExited(vc, e);
				}
			}

		eventReceivedTest(e);
	}

	/**
	 * Passes the event to the registered ViewComponent that the mouse is moving in.
	 * 
	 * @param A
	 *            MouseMoved Event.
	 */
	public void mouseMoved(MouseEvent e) {

		for (ViewComponent vc : registeredComponents)
			if (vc.getBounds().contains(vector)) { // TODO change vector to do
				// real mouse click
				// detection once vector is
				// done
				if (!containsMouse.contains(vc)) {
					containsMouse.add(vc);
					mouseEntered(vc, e);
				} else {
					vc.processMouseMotionEvent(e);
				}
			} else {
				if (containsMouse.contains(vc)) {
					containsMouse.remove(vc);
					mouseExited(vc, e);
				}
			}

		eventReceivedTest(e);
	}

	private void mouseEntered(ViewComponent vc, MouseEvent e) {
		vc.processMouseMotionEvent(createMouseEvent(
						MouseEvent.MOUSE_ENTERED, e));
	}

	private void mouseExited(ViewComponent vc, MouseEvent e) {
		vc
				.processMouseMotionEvent(createMouseEvent(
						MouseEvent.MOUSE_EXITED, e));
	}

	private MouseEvent createMouseEvent(int type, MouseEvent e) {
		return new MouseEvent(e.getComponent(), MouseEvent.MOUSE_ENTERED, e
				.getWhen(), e.getModifiers(), e.getX(), e.getY(), e
				.getClickCount(), e.isPopupTrigger());
	}

	private void eventReceivedTest(AWTEvent e) {
		System.out.println("Received: " + e);
	}
}
