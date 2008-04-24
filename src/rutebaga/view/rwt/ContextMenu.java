package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class ContextMenu extends ViewComponent implements Menu
{
	private static Color buttonColor = new Color(0, 0, 255, 70);
	private static Color buttonToggledColor = new Color(0, 0, 255, 170);
	
	private static Color disabledButtonColor = new Color(255, 0, 0, 30);
	private static Color disabledButtonToggledColor = new Color(255, 0, 0, 30);

	private static ColorAttribute contextHover = new ColorAttribute(new Color(
			0, 50, 0, 50));

	private List<Polygon> contextTriangles = new ArrayList<Polygon>();

	private ElementalList list;

	private List<ButtonComponent> components = new ArrayList<ButtonComponent>();
	private List<MenuItemImpl> menuItems = new ArrayList<MenuItemImpl>();

	private int currentContextHover = 0;
	
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

	public void draw(Drawer draw)
	{
		if (list.hasChanged(this))
			synchronized (this)
			{
				initContextMenu();
			}

		if (currentContextHover >= 0
				&& currentContextHover < contextTriangles.size())
		{
			draw.setAttribute(contextHover);

			draw.drawShape(getLocation(), contextTriangles
					.get(currentContextHover));

			draw.setAttribute(null);
		}
		for (int i=0; i<components.size(); i++)
		{
			ButtonComponent component = components.get(i);
			if(i == currentContextHover)
				component.setSelected(true);
			else
				component.setSelected(false);
			component.draw(draw);
		}

	}

	private void initContextMenu()
	{

		int angularSeparation = 0;
		if (list.contentSize() > 3)
			angularSeparation = 360 / list.contentSize();
		else
			angularSeparation = 120;

		int i = (list.contentSize() > 3) ? list.contentSize() : 3;

		generateContextTriangles(angularSeparation, i);
		generateComponents(angularSeparation);
		generateMenuItems();
		
		setBoundsWithContextTriangles();

		moveComponents();
	}

	private void generateContextTriangles(int angle, int number)
	{
		int currentAngle = 0;

		contextTriangles.clear();

		for (int i = 0; i < number; i++)
		{
			Polygon poly = new Polygon();

			poly.addPoint(0, 0);

			poly.addPoint((int) (getContextMenuRadius() * Math.cos(Math
					.toRadians(currentAngle))), (int) (getContextMenuRadius() * Math
					.sin(Math.toRadians(currentAngle))));

			currentAngle += angle;

			poly.addPoint((int) (getContextMenuRadius() * Math.cos(Math
					.toRadians(currentAngle))), (int) (getContextMenuRadius() * Math
					.sin(Math.toRadians(currentAngle))));

			contextTriangles.add(poly);

		}

	}
	
	private int getContextMenuRadius()
	{
		return 250;
	}
	
	private int getComponentSize()
	{
		if(components.size() < 8)
			return 50;
		else return (components.size()-8)*5 + 50;
	}
	
	private int getHighlightedComponentSize()
	{
		if(components.size() < 12)
			return (int) (50*1.7);
		else
			return (int) (50*1.7 + (components.size() - 7)*5*1.7);
	}

	private void generateComponents(int angle)
	{
		int currentAngle = 0;

		int i = 0;

		ButtonComponent selected = null;
		for (ButtonComponent bc: components)
			if (bc.isSelected())
				selected = bc;
		
		components.clear();

		SortedSet<ListElement> sortedList = new TreeSet<ListElement>(
				new Comparator<ListElement>()
				{
					public int compare(ListElement a, ListElement b)
					{
						// Move "Close" to the front of the list
						if ("Close".equals(a.getLabel()))
							return -1;
						return a.getLabel().compareToIgnoreCase(b.getLabel());
					}
				});
		for (ListElement element : list)
			sortedList.add(element);

		int currentIdx = 0;
		for (ListElement element : sortedList)
		{
			String label = element.getLabel();
			Color color, toggledColor;
			if(element.getCommand() != null)
			{
				label += " (" + (currentIdx++) + ")";
				color = buttonColor;
				toggledColor = buttonToggledColor;
			}
			else
			{
				color = disabledButtonColor;
				toggledColor = disabledButtonToggledColor;
			}
			ButtonComponent button = new ButtonComponent(label);
			button.setCommand(element.getCommand());
			button.setUntoggledColor(color);
			button.setToggledColor(toggledColor);

			Polygon poly = new Polygon();

			poly.addPoint(0, 0);

			poly.addPoint((int) (getComponentSize() * Math.cos(Math
					.toRadians(currentAngle))), (int) (getComponentSize() * Math
					.sin(Math.toRadians(currentAngle))));

			button.setLocation(getX()
					+ (int) (getComponentSize() * Math.cos(Math
							.toRadians(currentAngle + angle / 2))), getY()
					+ (int) (getComponentSize() * Math.sin(Math
							.toRadians(currentAngle + angle / 2))));

			currentAngle += angle;

			poly.addPoint((int) (getComponentSize() * Math.cos(Math
					.toRadians(currentAngle))), (int) (getComponentSize() * Math
					.sin(Math.toRadians(currentAngle))));

			button.setBounds(poly);

			components.add(button);
			i++;
		}

		if (i < 3)
		{

			int num = 3 - i;

			for (int j = 0; j < num; j++)
			{
				ButtonComponent button = new ButtonComponent();
				button.setCommand(null);
				button.setUntoggledColor(buttonColor);
				button.setToggledColor(buttonToggledColor);

				Polygon poly = new Polygon();

				poly.addPoint(0, 0);

				poly.addPoint((int) (getComponentSize() * Math.cos(Math
						.toRadians(currentAngle))), (int) (getComponentSize() * Math
						.sin(Math.toRadians(currentAngle))));

				button.setLocation(getX()
						+ (int) (getComponentSize() * Math.cos(Math
								.toRadians(currentAngle + angle / 2))), getY()
						+ (int) (getComponentSize() * Math.sin(Math
								.toRadians(currentAngle + angle / 2))));

				currentAngle += angle;

				poly.addPoint((int) (getComponentSize() * Math.cos(Math
						.toRadians(currentAngle))), (int) (getComponentSize() * Math
						.sin(Math.toRadians(currentAngle))));

				button.setBounds(poly);

				components.add(button);
				i++;
			}

		}

		if (selected != null)
			selected.setSelected(true);
	}

	private void generateMenuItems()
	{
		this.menuItems.clear();
		for (int i = 0; i < components.size(); ++i)
			menuItems.add(new MenuItemImpl(i));
	}
	
	private void moveComponents()
	{
		int angle = 0;
		if (list.contentSize() > 3)
			angle = 360 / list.contentSize();
		else
			angle = 120;

		int currentAngle = 0;

		synchronized (this)
		{

			for (ButtonComponent component : components)
			{
				int mySize = getComponentSize();
				if (component.isSelected())
					mySize = getHighlightedComponentSize();
				component.setLocation(getX()
						+ (int) (mySize * Math.cos(Math.toRadians(currentAngle
								+ angle / 2))), getY()
						+ (int) (mySize * Math.sin(Math.toRadians(currentAngle
								+ angle / 2))));

				currentAngle += angle;
			}
		}
	}

	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);

		moveComponents();
	}

	public void setLocation(Point p)
	{
		super.setLocation(p);

		moveComponents();
	}

	private void setBoundsWithContextTriangles()
	{
		Area area = new Area();

		for (Polygon poly : contextTriangles)
			area.add(new Area(poly));

		this.setBounds(area);
	}

	protected boolean processMouseEvent(MouseEvent event)
	{
		int i = 0;

		synchronized (this)
		{
			for (Polygon s : contextTriangles)
			{
				if (s.contains(event.getX() - this.getX(), event.getY()
						- this.getY()))
				{
					currentContextHover = i;
					return components.get(i).processMouseEvent(event);
				}

				i++;
			}
		}

		return true;
	}

	protected boolean processMouseMotionEvent(MouseEvent event)
	{
		int i = 0;

		synchronized (this)
		{
			for (Polygon s : contextTriangles)
			{
				if (s.contains(event.getX() - this.getX(), event.getY()
						- this.getY()))
				{
					if (i == currentContextHover)
						break;
					if (currentContextHover >= 0
							&& currentContextHover < components.size())
						components.get(currentContextHover).setSelected(false);
					currentContextHover = i;
					if (i < components.size() && i >= 0)
						components.get(i).setSelected(true);
					moveComponents();
					break;
				}

				i++;
			}
		}

		return true;
	}
	
	public List<? extends MenuItem> getItems() {
		return Collections.unmodifiableList(menuItems);
	}

	public MenuItem getSelectedItem() {
		return menuItems.get(currentContextHover);
	}
	
	private class MenuItemImpl implements MenuItem {
		private final int index;

		private MenuItemImpl(int index) {
			this.index = index;
		}
		
		public void activate() {
			components.get(index).executeCommand();
		}

		public boolean isSelected() {
			return (index == currentContextHover);
		}

		public void select() {
			components.get(currentContextHover).setSelected(false);
			currentContextHover = index;
			components.get(index).setSelected(true);
		}
	}
}
