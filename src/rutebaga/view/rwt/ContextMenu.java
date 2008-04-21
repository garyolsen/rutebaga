package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Observer;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElement;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;

/**
 * Provides a simple way to create interactable menus. These ContextMenus send
 * {@link rutebaga.controller.command.Command Commands} to the model. Since
 * ContextMenus decorate a ViewComponent, any information can be contained
 * within a ContextMenu, not just options.
 * <p>
 * ContextMenus will entail some of the following:
 * <ul>
 * <li> Inventory ContextMenu (a scroll decorated list of items with a button
 * next to each item that will trigger a new ItemOptionsContextMenu).
 * <li>ItemOptions ContextMenu ( a list of buttons to equip, use, drop, unequip
 * etc that is context based on the item ).
 * </ul>
 * <p>
 * Responsibilities are to
 * <ul>
 * <li>
 * 
 * Forward events to the decorated object and the sub-ContextMenu (if any).
 * <LI> Maintain the sub-ContextMenu.
 * 
 * @author Ryan
 * 
 */
public class ContextMenu extends ViewComponent 
{

	private int contextMenuRadius = 250; //screen pixels
	private int componentSize = 50;
	
	private static Color buttonColor = new Color(0,0,255, 70);
	private static Color buttonToggledColor = new Color(0,0,255,170);
	
	private static ColorAttribute contextHover = new ColorAttribute(new Color(0,50,0, 50));
	
	private List<Polygon> contextTriangles = new ArrayList<Polygon>();
	
	private ElementalList list;
	
	private List<ViewComponent> components = new ArrayList<ViewComponent>();
	
	private int currentContextHover;
	
	private boolean dirty;
	
	/**
	 * Contructs a ContextMenu as a decorator of the specified ViewComponent.
	 * 
	 * @param decorated
	 *            The ViewComponent to be decorated.
	 */
	public ContextMenu(ElementalList list)
	{
		this.list = list;
		initContextMenu();
	}
	
	public void draw(Drawer draw) {
		if (list.hasChanged(this))
			initContextMenu();
		
		draw.setAttribute(contextHover);
		
		draw.drawShape(getLocation(), contextTriangles.get(currentContextHover));
		
		draw.setAttribute(null);

		for(ViewComponent component : components)
			component.draw(draw);
		
		
	}
	
	private void initContextMenu() {

		int angularSeparation = 0;
		if(list.contentSize() > 3)
			angularSeparation = 360 / list.contentSize();
		else
			angularSeparation = 120;
		
		int i = (list.contentSize() > 3) ? list.contentSize() : 3;
		
		generateContextTriangles(angularSeparation, i);
		generateComponents(angularSeparation);
		
		setBoundsWithContextTriangles();
	}
	
	private void generateContextTriangles(int angle, int number) {
		int currentAngle = 0;
		
		synchronized(contextTriangles) {
			contextTriangles.clear();
			
			for(int i=0; i < number; i++) {
				Polygon poly = new Polygon();
				
				poly.addPoint(0, 0);
				
				poly.addPoint( (int) (contextMenuRadius * Math.cos(Math.toRadians(currentAngle))), (int) (contextMenuRadius * Math.sin(Math.toRadians(currentAngle))));
				
				currentAngle += angle;
				
				poly.addPoint( (int) (contextMenuRadius * Math.cos(Math.toRadians(currentAngle))), (int) (contextMenuRadius * Math.sin(Math.toRadians(currentAngle))));
				
				contextTriangles.add(poly);
				
			}
		}
		
	}
	
	private void generateComponents(int angle) {
		int currentAngle = 0;
		
		int i = 0;
		
		components.clear();

		
		for(ListElement element : list) {
			ButtonComponent button = new ButtonComponent(element.getLabel());
			button.setCommand(element.getCommand());
			button.setUntoggledColor(buttonColor);
			button.setToggledColor(buttonToggledColor);
			
			Polygon poly = new Polygon();
			
			poly.addPoint(0, 0);
			
			poly.addPoint( (int) (componentSize * Math.cos(Math.toRadians(currentAngle))), (int) (componentSize * Math.sin(Math.toRadians(currentAngle))));
			
			
			button.setLocation( getX() + (int) ( componentSize * Math.cos(Math.toRadians(currentAngle + angle/2)) ), getY() + (int) ( componentSize * Math.sin(Math.toRadians(currentAngle + angle/2)) ));
					
			currentAngle += angle;
			
			poly.addPoint( (int) (componentSize * Math.cos(Math.toRadians(currentAngle))), (int) (componentSize * Math.sin(Math.toRadians(currentAngle))));
			
			button.setBounds(poly);
		
			components.add(button);
			i++;
		}
		
		if(i < 3) {

			int num = 3 - i;
			
			for(int j = 0; j < num; j++) {
				ButtonComponent button = new ButtonComponent();
				button.setCommand(null);
				button.setUntoggledColor(buttonColor);
				button.setToggledColor(buttonToggledColor);
				
				Polygon poly = new Polygon();
				
				poly.addPoint(0, 0);
				
				poly.addPoint( (int) (componentSize * Math.cos(Math.toRadians(currentAngle))), (int) (componentSize * Math.sin(Math.toRadians(currentAngle))));
				
				button.setLocation( getX() + (int) ( componentSize * Math.cos(Math.toRadians(currentAngle + angle/2)) ), getY() + (int) ( componentSize * Math.sin(Math.toRadians(currentAngle + angle/2)) ));
						
				currentAngle += angle;
				
				poly.addPoint( (int) (componentSize * Math.cos(Math.toRadians(currentAngle))), (int) (componentSize * Math.sin(Math.toRadians(currentAngle))));
				
				button.setBounds(poly);
			
				components.add(button);
				i++;
			}
			
		}
	}
	
	private void moveComponents() {
		int angle = 0;
		if(list.contentSize() > 3)
			angle = 360 / list.contentSize();
		else
			angle = 120;
		
		
		int currentAngle = 0;
		
		synchronized(components) {
			
			for(ViewComponent component : components) {
				component.setLocation( getX() + (int) ( componentSize * Math.cos(Math.toRadians(currentAngle + angle/2)) ), getY() + (int) ( componentSize * Math.sin(Math.toRadians(currentAngle + angle/2)) ));
				
				currentAngle += angle;
			}
		}
	}
	
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		
		moveComponents();
	}
	
	public void setLocation(Point p) {
		super.setLocation(p);
		
		moveComponents();
	}
	
	private void setBoundsWithContextTriangles() {
		Area area = new Area();
		
		for(Polygon poly : contextTriangles)
			area.add(new Area(poly));
		
		this.setBounds(area);
	}
	
	protected boolean processMouseEvent( MouseEvent event ) {
		int i = 0;
		
		synchronized(contextTriangles) {
			for(Polygon s : contextTriangles) {
				if(s.contains( event.getX() - this.getX(), event.getY() - this.getY())) {
					currentContextHover = i;
					synchronized(components) {
						return components.get(i).processMouseEvent(event);
					}
				}
				
				i++;
			}
		}
		
		return true;
	}
	
	protected boolean processMouseMotionEvent( MouseEvent event ) { 
		int i = 0;
		
		synchronized(contextTriangles) {
			for(Polygon s : contextTriangles) {
				if(s.contains( event.getX() - this.getX(), event.getY() - this.getY())) {
					currentContextHover = i;
					break;
				}
				
				i++;
			}
		}
			
		return true;
	}
}
