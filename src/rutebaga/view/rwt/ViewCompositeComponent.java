package rutebaga.view.rwt;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rutebaga.view.drawer.Drawer;

/**
 * Provides the ability for {@link ViewComponent ViewComponents} to be collected
 * together in a group. The group can share their state information (visibility,
 * hasKeyFocus, etc). Additionally, the ViewCompositeComponent redefines the
 * screen coordinate system such that the coordinates for the
 * ViewCompositeComponent are the origin of the system and all ViewComponents'
 * coordinates are relative to the ViewCompositeComponent's origin.
 * <p>
 * Responsibilities
 * <ul>
 * <li>Maintain the set of ViewComponents within this composition.
 * <li>Set all of the ViewCompositeComponent's children to itself.
 * <li>Forward draw calls to its children.
 * <li>Redefine the screen coordinate origin for the children.
 * <li>Toggle visibility of the entire set of ViewComponents.
 * <li>Ensure the {@link rutebaga.commons.Bounds} for the
 * ViewCompositeComponent encompasses all of its children via dirtyBounds().
 * 
 * @author Ryan
 */
public class ViewCompositeComponent extends ViewComponent
{

	private Set<ViewComponent> components;

	/**
	 * Constructs a new empty ViewCompositeComponent.
	 */
	public ViewCompositeComponent()
	{
		components = new HashSet<ViewComponent>();
	}

	/**
	 * Adds another ViewComponent to this ViewCompositeComponent.
	 * 
	 * @param component
	 *            The ViewComponent to be added.
	 */
	public void addChild(ViewComponent component)
	{
		components.add(component);
	}

	/**
	 * Removes a ViewComponent from this ViewCompositeComponent.
	 * 
	 * @param component
	 *            The ViewComponent to be removed.
	 * @return The removed ViewComponent.
	 */
	public ViewComponent removeChild(ViewComponent component)
	{
		return components.remove(component) ? component : null;
	}

	/**
	 * Returns all of the ViewComponents that currently make up this
	 * ViewCompositeComponent.
	 * 
	 * @return A set of ViewComponents
	 */
	public Set<ViewComponent> getChildren()
	{
		return Collections.unmodifiableSet(components);
	}

	/**
	 * Forewards the draw call to each of its children.
	 * 
	 * @see rutebaga.view.rwt.ViewComponent#draw(rutebaga.view.drawer.Drawer)
	 */
	@Override
	public void draw(Drawer draw)
	{
		for (ViewComponent component : components)
			component.draw(draw);
	}

	public Shape getBounds() {
		if(!isDirty())
			return super.getBounds();
		
		
		Area bounds = new Area();
		
		for(ViewComponent vc : components)
			bounds.add(new Area(vc.getBounds()));
		
		super.setBounds(bounds);
		
		return super.getBounds();
	}
	
	
}
