package rutebaga.view.rwt;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EventDispatcher implements KeyListener, MouseListener, MouseMotionListener {

	private Set<ViewComponent> registeredComponents;
	private Set<ViewComponent> containsMouse;
	
	private List<KeyListener> keyListeners = new ArrayList<KeyListener>();
	private List<MouseListener> mouseListeners = new ArrayList<MouseListener>();
	
	private DispatchVisitor dispatchVisitor;
	
	public EventDispatcher() {
		dispatchVisitor = new DispatchVisitor(this);
		
		registeredComponents = new HashSet<ViewComponent>();
		containsMouse = new HashSet<ViewComponent>();
	}
	
	public void registerComponent( ViewComponent vc ) {
		vc.visit(dispatchVisitor);
	}
	
	public void deregisterComponent( ViewComponent vc ) {
		registeredComponents.remove(vc);
		containsMouse.remove(vc);
	}
	
	public void addKeyListener(KeyListener kl ) {
		keyListeners.add(kl);
	}
	
	public void removeKeyListener(KeyListener kl) {
		keyListeners.remove(kl);
	}
	
	public void addMouseListener(MouseListener ml) {
		mouseListeners.add(ml);
	}
	
	public void removeMouseListener(MouseListener ml) {
		mouseListeners.remove(ml);
	}
	
	private void addComponent(ViewComponent vc) {
		registeredComponents.add(vc);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) //TODO remove when controller handles this!
			System.exit(0);
		
		boolean consumed = false;
		
		for(ViewComponent vc : registeredComponents)
			if(vc.hasFocus())
				consumed |= vc.processKeyEvent(e);
		
		if(!consumed)
			for(KeyListener kl : keyListeners)
				kl.keyPressed(e);
		
		eventReceivedTest(e);
	}

	public void keyReleased(KeyEvent e) {
		boolean consumed = false;
		
		for(ViewComponent vc : registeredComponents)
			if(vc.hasFocus())
				consumed |= vc.processKeyEvent(e);
		
		if(!consumed)
			for(KeyListener kl : keyListeners)
				kl.keyReleased(e);
		
		eventReceivedTest(e);
	}

	public void keyTyped(KeyEvent e) {
		boolean consumed = false;
		
		for(ViewComponent vc : registeredComponents)
			if(vc.hasFocus())
				consumed |= vc.processKeyEvent(e);
		
		if(!consumed)
			for(KeyListener kl : keyListeners)
				kl.keyTyped(e);
		
		eventReceivedTest(e);
	}

	
	
	public void mouseClicked(MouseEvent e) {
		boolean consumed = false;
		
		for(ViewComponent vc : containsMouse)
			consumed |= vc.processMouseEvent(e);
		
		if(!consumed)
			for(MouseListener ml : mouseListeners)
				ml.mouseClicked(e);
		
		eventReceivedTest(e);
	}
	
	public void mousePressed(MouseEvent e) {
		boolean consumed = false;
		
		for(ViewComponent vc : containsMouse)
			consumed |= vc.processMouseEvent(e);
		
		if(!consumed)
			for(MouseListener ml : mouseListeners)
				ml.mousePressed(e);
		
		eventReceivedTest(e);
	}

	public void mouseReleased(MouseEvent e) {
		boolean consumed = false;
		
		for(ViewComponent vc : containsMouse)
			consumed |= vc.processMouseEvent(e);
		
		if(!consumed)
			for(MouseListener ml : mouseListeners)
				ml.mouseReleased(e);
		
		eventReceivedTest(e);
	}

	public void mouseEntered(MouseEvent e) {
		for(MouseListener ml : mouseListeners)
			ml.mouseEntered(e);
	}
	public void mouseExited(MouseEvent e) {
		for(MouseListener ml : mouseListeners)
			ml.mouseEntered(e);
	}

	public void mouseDragged(MouseEvent e) {
		
		for(ViewComponent vc : registeredComponents) {
			Point mouse = new Point(e.getX() - vc.getX(), e.getY() - vc.getY());
			
			if(vc.getBounds().contains( mouse )) { 
				if( !containsMouse.contains( vc )) {
					containsMouse.add(vc);
					mouseEntered(vc, e);
				} else {
					vc.processMouseMotionEvent( e );
				}
			} else {
				if( containsMouse.contains( vc )) {
					containsMouse.remove(vc);
					mouseExited(vc, e);
				}
			}
		}
		
		eventReceivedTest(e);
	}

	public void mouseMoved(MouseEvent e) {
		
		for(ViewComponent vc : registeredComponents) {
			Point mouse = new Point(e.getX() - vc.getX(), e.getY() - vc.getY());
			
			if(vc.getBounds().contains( mouse )) { 
				
				if( !containsMouse.contains( vc )) {
					containsMouse.add(vc);
					mouseEntered(vc, e);
				} else {
					vc.processMouseMotionEvent( e );
				}
			} else {
				if( containsMouse.contains( vc )) {
					containsMouse.remove(vc);
					mouseExited(vc, e);
				}
			}
		}
		
		eventReceivedTest(e);
	}
	
	private void mouseEntered(ViewComponent vc, MouseEvent e) {
		vc.processMouseMotionEvent( createMouseEvent( MouseEvent.MOUSE_ENTERED, e) );
	}
	
	private void mouseExited(ViewComponent vc, MouseEvent e) {
		vc.processMouseMotionEvent( createMouseEvent( MouseEvent.MOUSE_EXITED, e) );
	}
	
	private MouseEvent createMouseEvent( int type, MouseEvent e ) {
		return new MouseEvent(e.getComponent(), type, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()	);
	}
	
	private void eventReceivedTest(AWTEvent e) {
		//System.out.println("Received: " + e);
	}
	
	private class DispatchVisitor implements ViewVisitor {
		
		private EventDispatcher dispatcher;

		public DispatchVisitor(EventDispatcher dispatcher) {
			this.dispatcher = dispatcher;
		}
		
		public void visit(ViewComponent vc) {
			dispatcher.addComponent(vc);
		}

		public void visit(ViewCompositeComponent vcc) {
			dispatcher.addComponent(vcc);
			
			for(ViewComponent vc : vcc.getChildren())
				vc.visit(this);
		}
		
	}
}


