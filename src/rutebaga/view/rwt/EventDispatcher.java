package rutebaga.view.rwt;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class EventDispatcher implements KeyListener, MouseListener, MouseMotionListener {

	private List<ViewComponent> registeredComponents;
	private List<ViewComponent> containsMouse;
	
	private MouseEvent lastMouse;
	
	private List<KeyListener> keyListeners = new ArrayList<KeyListener>();
	private List<MouseListener> mouseListeners = new ArrayList<MouseListener>();
	
	private AdditiveDispatchVisitor addDispatchVisitor;
	private SubtractiveDispatchVisitor subDispatchVisitor;
	
	private LinkedList<ViewComponent> deregisterQueue;
	private LinkedList<ViewComponent> registerQueue;
	
	public EventDispatcher() {
		addDispatchVisitor = new AdditiveDispatchVisitor(this);
		subDispatchVisitor = new SubtractiveDispatchVisitor(this);
		
		registeredComponents = new ArrayList<ViewComponent>();
		containsMouse = new ArrayList<ViewComponent>();
		
		deregisterQueue = new LinkedList<ViewComponent>();
		registerQueue = new LinkedList<ViewComponent>();
	}
	
	public void registerComponent( ViewComponent vc ) {
		vc.visit(addDispatchVisitor);
	}
	
	public void deregisterComponent( ViewComponent vc ) {
		vc.visit(subDispatchVisitor);
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
		synchronized(registerQueue) {
			registerQueue.add(vc);
		}
	}
	
	private void removeComponent(ViewComponent vc) {
		synchronized(deregisterQueue) {
			deregisterQueue.add(vc);
		}
	}
	
	private void flushQueues() {
		dumpRegisterQueue();
		dumpDeregisterQueue();
	}
	
	private void dumpRegisterQueue() {
		if(registerQueue.size() > 0)
			synchronized(registerQueue) {
				for(ViewComponent vc : registerQueue) {
					registeredComponents.add(vc);
					if(lastMouse != null && vc.getBounds().contains(new Point(lastMouse.getX() - vc.getX(), lastMouse.getY() - vc.getY())))
						containsMouse.add(vc);
				}
				
				registerQueue.clear();
			}
	}
	
	private void dumpDeregisterQueue() {
		if(deregisterQueue.size() > 0)
			synchronized(deregisterQueue) {
				for(ViewComponent vc : deregisterQueue) {
					registeredComponents.remove(vc);
					containsMouse.remove(vc);
				}
					
				deregisterQueue.clear();
			}
	}
	
	public void keyPressed(KeyEvent e) {
		flushQueues();
		
		boolean consumed = false;
		
		synchronized(registeredComponents) {
			for(ViewComponent vc : registeredComponents)
				if(vc.hasFocus())
					consumed |= vc.processKeyEvent(e);
		}
		
		if(!consumed)
			for(KeyListener kl : keyListeners)
				kl.keyPressed(e);
		
		eventReceivedTest(e);
	}

	public void keyReleased(KeyEvent e) {
		flushQueues();
		boolean consumed = false;
		
		synchronized(registeredComponents) {
			for(ViewComponent vc : registeredComponents)
				if(vc.hasFocus())
					consumed |= vc.processKeyEvent(e);
		}
		
		if(!consumed)
			for(KeyListener kl : keyListeners)
				kl.keyReleased(e);
		
		
		eventReceivedTest(e);
	}

	public void keyTyped(KeyEvent e) {
		flushQueues();
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
		flushQueues();
		boolean consumed = false;
		
		for(ViewComponent vc : containsMouse) {
			consumed |= vc.processMouseEvent(e);
			if(consumed)
				break;
		}
		
		if(!consumed)
			for(MouseListener ml : mouseListeners)
				ml.mouseClicked(e);
		
		eventReceivedTest(e);
		lastMouse = e;
	}
	
	public void mousePressed(MouseEvent e) {
		flushQueues();
		boolean consumed = false;
		
		for(ViewComponent vc : containsMouse) {
			consumed |= vc.processMouseEvent(e);
			if(consumed)
				break;
		}
		
		if(!consumed)
			for(MouseListener ml : mouseListeners)
				ml.mousePressed(e);
		
		eventReceivedTest(e);
		lastMouse = e;
	}

	public void mouseReleased(MouseEvent e) {
		flushQueues();
		boolean consumed = false;
		
		for(ViewComponent vc : containsMouse) {
			consumed |= vc.processMouseEvent(e);
			if(consumed)
				break;
		}
		
		if(!consumed)
			for(MouseListener ml : mouseListeners)
				ml.mouseReleased(e);
		
		flushQueues();
		eventReceivedTest(e);
		lastMouse = e;
	}

	public void mouseEntered(MouseEvent e) {
		flushQueues();
		
		for(MouseListener ml : mouseListeners)
			ml.mouseEntered(e);
		
		lastMouse = e;
	}
	public void mouseExited(MouseEvent e) {
		flushQueues();
		
		for(MouseListener ml : mouseListeners)
			ml.mouseEntered(e);
		
		lastMouse = e;
	}

	public void mouseDragged(MouseEvent e) {
		flushQueues();
		
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
		lastMouse = e;
	}

	public void mouseMoved(MouseEvent e) {
		flushQueues();
		
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
		lastMouse = e;
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
		//rutebaga.commons.Log.log("Received: " + e);
	}
	
	private class AdditiveDispatchVisitor implements ViewVisitor {
		
		private EventDispatcher dispatcher;

		public AdditiveDispatchVisitor(EventDispatcher dispatcher) {
			this.dispatcher = dispatcher;
		}
		
		public void visit(ViewComponent vc) {
			dispatcher.addComponent(vc);
		}

		public void visit(ViewCompositeComponent vcc) {
			
			for(ViewComponent vc : vcc.getChildren())
				vc.visit(this);
		}
		
	}
	
	private class SubtractiveDispatchVisitor implements ViewVisitor {
		
		private EventDispatcher dispatcher;

		public SubtractiveDispatchVisitor(EventDispatcher dispatcher) {
			this.dispatcher = dispatcher;
		}
		
		public void visit(ViewComponent vc) {
			dispatcher.removeComponent(vc);
		}

		public void visit(ViewCompositeComponent vcc) {
			
			dispatcher.removeComponent(vcc);
			
			for(ViewComponent vc : vcc.getChildren())
				vc.visit(this);
		}
		
	}
	
}


