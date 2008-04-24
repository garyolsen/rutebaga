package rutebaga.view.game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import rutebaga.model.entity.Entity;
import rutebaga.model.environment.Instance;
import rutebaga.view.drawer.*;
import rutebaga.view.rwt.ComponentDecorator;
import rutebaga.view.rwt.TextLabelComponent;
import rutebaga.view.rwt.View;

public class DialogDecorator extends ComponentDecorator {

	private Area dialogBox;
	private ColorAttribute color = new ColorAttribute(Color.WHITE);
	
	private Instance speaker;
	private Entity avatar;
	private View view;
	
	private static int DEFAULT_WIDTH = 150;
	private static int DEFAULT_HEIGHT = 100;
	private static int DEFAULT_TIMER = 3000; //milliseconds
	
	
	private long timer = DEFAULT_TIMER;
	
	
	private long lastTime;
	
	
	public DialogDecorator(TextLabelComponent decorated, Entity avatar, Instance instance, View view) {
		super(decorated);
		
		int margin = 20;
		int decoratedWidth = DEFAULT_WIDTH - margin;
		int decoratedHeight = DEFAULT_HEIGHT - margin;
		decorated.setBounds(new Rectangle(decoratedWidth, decoratedHeight));
		
		dialogBox = constructDialogBox(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
		speaker = instance;
		this.avatar = avatar;
		
		this.view = view;
		
		this.setBounds(dialogBox);
		
		lastTime = System.currentTimeMillis();
	}
	
	@Override
	public void setLocation(int x, int y) {
		getDecoratedComponent().setLocation(x + 10, y + getHeight() / 20);
		super.setLocation(x, y);
	}
	
	public void setLocation(Point p) {
		getDecoratedComponent().setLocation(p.x + 10, p.y + getHeight() / 20);
		super.setLocation(p);
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
		
		if(timer <= 0 ) {
			view.removeViewComponent(this);
		} else {
			long now = System.currentTimeMillis();
			
			timer -= now - lastTime;
			lastTime = now;
		}
		
		if(avatar.getVision().getActiveSet().contains(speaker) || avatar == speaker) {
			Point p = null;
			
			if(avatar == speaker) {
				p = new Point(view.getWidth()/2, view.getHeight()/2);
			} else {
				p = MapComponent.centerPointOn(avatar, speaker.getCoordinate(), view.getWidth(), view.getHeight(), getTileWidth(), getTileHeight());
			}
			p.y -= this.getHeight();
			p.y -= speaker.getAppearance().getImage().getHeight(null);
			p.x += speaker.getAppearance().getImage().getWidth(null) / 2 ;
			this.setLocation(p);
			
		
			draw.setAttribute(color);
			draw.drawShape(getLocation(), dialogBox);
			
			//ViewComponent vc = this.getDecoratedComponent();
			//vc.setLocation( getX() + (this.getWidth() - vc.getWidth()) /2,   getY() + getHeight()/2 - vc.getHeight()/2);
			
			super.draw(draw);
		} else {
			view.removeViewComponent(this);
		}
	}
	
	
	private int getTileWidth() {

		return avatar.getEnvironment().getAppearanceAttr().getTileWidth();
	}
	
	private int getTileHeight() {

		return avatar.getEnvironment().getAppearanceAttr().getTileWidth();
	}

}
