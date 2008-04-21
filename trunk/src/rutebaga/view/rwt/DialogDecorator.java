package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import rutebaga.view.drawer.*;

public class DialogDecorator extends ComponentDecorator {

	private Area dialogBox;
	private ColorAttribute color = new ColorAttribute(Color.WHITE);
	
	
	public DialogDecorator(TextLabelComponent decorated, int width, int height) {
		super(decorated);
		
		int margin = 20;
		int decoratedWidth = width - margin;
		int decoratedHeight = height - margin;
		decorated.setBounds(new Rectangle(decoratedWidth, decoratedHeight));
		
		dialogBox = constructDialogBox(width,height);
		
		this.setBounds(dialogBox);
	}
	
	@Override
	public void setLocation(int x, int y) {
		getDecoratedComponent().setLocation(x + 10, y + getHeight() / 20);
		super.setLocation(x, y);
	}
	
	private Area constructDialogBox(int width, int height) {
		Area area = new Area(new Ellipse2D.Float(0,0,width, height));
		
		Polygon poly = new Polygon();
		
		poly.addPoint(width/6, height/2);
		poly.addPoint(width/3, 3*height/4);
		poly.addPoint(0, height+20);
		
		area.add(new Area(poly));
		return area;
	}
	
	public void draw(Drawer draw) {
		draw.setAttribute(color);
		draw.drawShape(getLocation(), dialogBox);
		
		//ViewComponent vc = this.getDecoratedComponent();
		//vc.setLocation( getX() + (this.getWidth() - vc.getWidth()) /2,   getY() + getHeight()/2 - vc.getHeight()/2);
		
		super.draw(draw);
	}

}
