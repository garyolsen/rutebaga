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
import java.util.Random;

import rutebaga.controller.command.Command;
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

	private int contextMenuRadius = 200; //screen pixels
	private int componentSize = 50;
	
	private static Color buttonColor = new Color(0,0,255, 70);
	
	private List<Polygon> contextTriangles = new ArrayList<Polygon>();
	
	private List<Command> contextCommands;
	private List<String> commandNames;
	
	private List<ViewComponent> components = new ArrayList<ViewComponent>();
	
	/**
	 * Contructs a ContextMenu as a decorator of the specified ViewComponent.
	 * 
	 * @param decorated
	 *            The ViewComponent to be decorated.
	 */
	public ContextMenu(List<Command> contextCommands, List<String> names)
	{
		this.contextCommands = contextCommands;
		if(contextCommands.size() == 1) {
			contextCommands.add(null);
			contextCommands.add(null);
			names.add("");
			names.add("");
		}
		
		this.commandNames = names;
		initContextMenu();
	}
	
	public void draw(Drawer draw) {

		for(ViewComponent component : components)
			component.draw(draw);
		
	}
	
	private void initContextMenu() {

		int angularSeparation = 360 / contextCommands.size();
		
		if(angularSeparation == 360)
			angularSeparation = 45;
		
		generateContextTriangles(angularSeparation, contextCommands.size());
		generateComponents(contextCommands, angularSeparation);
		
		setBoundsWithContextTriangles();
	}
	
	private void generateContextTriangles(int angle, int number) {
		int currentAngle = 0;
		
		if(number == 1)
			currentAngle = 245;
		
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
	
	private void generateComponents(List<Command> commands, int angle) {
		int currentAngle = 0;
		
		if(commands.size() == 1)
			currentAngle = 245;
		
		int i = 0;

		
		for(Command command : commands) {
			ButtonComponent button = new ButtonComponent(commandNames.get(i));
			button.setCommand(command);
			
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
	
	private void moveComponents() {
		int angle = 360 / contextCommands.size();
		
		int currentAngle = 0;
		
		for(ViewComponent component : components) {
			component.setLocation( getX() + (int) ( componentSize * Math.cos(Math.toRadians(currentAngle + angle/2)) ), getY() + (int) ( componentSize * Math.sin(Math.toRadians(currentAngle + angle/2)) ));
			
			currentAngle += angle;
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
		for(Polygon s : contextTriangles) {
			if(s.contains( event.getX() - this.getX(), event.getY() - this.getY()))
				return components.get(i).processMouseEvent(event);
			
			i++;
		}
		
		return true;
	}
	
	protected boolean processMouseMotionEvent( MouseEvent event ) { 
		
		return false;
	}

}
