package rutebaga.view.rwt;

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
public class ContextMenu extends ComponentDecorator
{

	/**
	 * Contructs a ContextMenu as a decorator of the specified ViewComponent.
	 * 
	 * @param decorated
	 *            The ViewComponent to be decorated.
	 */
	public ContextMenu(ViewComponent decorated)
	{
		super(decorated);
		// TODO Auto-generated constructor stub
	}

}
