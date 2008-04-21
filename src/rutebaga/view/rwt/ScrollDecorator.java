package rutebaga.view.rwt;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import rutebaga.view.drawer.ClippingAttribute;
import rutebaga.view.drawer.Drawer;

public class ScrollDecorator extends ComponentDecorator {

	private final static int SPACING = 5;
	private int speed = 5;

	private int currentX, scrollHeight, scrollWidth;
	
	private double scroll;

	public ScrollDecorator(ViewComponent vc, int scrollWidth, int scrollHeight) {
		super(vc);
		
		this.scrollWidth = scrollWidth;
		this.scrollHeight = scrollHeight;
		
		this.setBounds(new Rectangle( scrollWidth, scrollHeight ));
	}
	
	public void draw(Drawer draw ) {
		
		int clippingX = getLocation().x;
		int clippingY = getLocation().y;
		
		int clippingWidth = getWidth();
		
		draw.setAttribute(new ClippingAttribute(new Rectangle(clippingX, clippingY, clippingWidth, getHeight())));
		
		
		ScrollVisitor visitor = new ScrollVisitor();
		this.getDecoratedComponent().visit(visitor);
		
		
		currentX += speed * scroll;
		
		int offset = this.getX() - currentX;
		for(ViewComponent vc : visitor.getElements()) {
			int offsetY = getY() + (scrollHeight - vc.getHeight()) /2;
			
			vc.setLocation(offset, offsetY);
			
			vc.draw(draw);
			
			offset += vc.getWidth() + SPACING;
		}
		
		
		draw.setAttribute(new ClippingAttribute(null));
		
	}
	
	public void setScrollSpeed(int speed) {
		this.speed = speed;
	}
	
	protected boolean processMouseMotionEvent( MouseEvent event ) {
		
		if(event.getID() == MouseEvent.MOUSE_EXITED)
			scroll = 0.0;
		
		if(event.getID() == MouseEvent.MOUSE_MOVED) {
			int x = event.getX() - this.getX();
			
			if( x > scrollWidth/2)
				scroll = (x - (scrollWidth/2.0d))/ (scrollWidth/2.0d);
			
			if( x < scrollWidth/2)
				scroll = (x - (scrollWidth/2.0d)) / (scrollWidth/2.0d);
		}
		
		super.processMouseMotionEvent(event);
		return true;
	}
	
	@Override
	protected boolean processKeyEvent(KeyEvent event) {
		return getDecoratedComponent().processKeyEvent(event);
	}



	private class ScrollVisitor implements ViewVisitor {
		
		private ArrayList<ViewComponent> components = new ArrayList<ViewComponent>();

		public ScrollVisitor() {
			
		}
		
		public void visit(ViewComponent vc) {
			components.add(vc);
		}

		public void visit(ViewCompositeComponent vcc) {
			for(ViewComponent vc : vcc.getChildren())
				vc.visit(this);
		}
		
		public List<ViewComponent> getElements() {
			return components;
		}

		public void visit(ComponentDecorator cd) {
			components.add(cd);
		}
		
	}
}
