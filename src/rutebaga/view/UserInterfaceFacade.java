package rutebaga.view;

import java.util.Collection;

import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.environment.Instance;
import rutebaga.view.game.TargetInstanceObservable;
import rutebaga.view.rwt.ContextMenu;
import rutebaga.view.rwt.Menu;
import rutebaga.view.rwt.TextFieldListener;
import rutebaga.view.rwt.View;

public interface UserInterfaceFacade {
	void createTitleScreen(ElementalList list);
	
	Menu getActiveMenu();
	
	/**
	 * Shows a screen where the user can create a player.
	 * 
	 * @param listener
	 *            A listener for the typed input.
	 * @param list
	 *            A list of choices for the player.
	 * @param accept
	 *            The {@link Command} for what to do on confirmation.
	 * @param cancel
	 *            The {@link Command} for what to do on cancellation.
	 */
	void createAvatarCreationScreen(TextFieldListener listener,
			ElementalList list, Command accept, Command cancel);
	
	
	void createGamePlayScreen(Entity avatar, TargetInstanceObservable observable, Collection<StatValue> stats);

	/**
	 * Spawns a ContextMenu at the {@link Vector} location provided.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @param vector
	 *            The location to spawn the menu.
	 * @return The ContextMenu that was created.
	 */
	Menu createRootContextMenu(ElementalList list, Vector2D vector);
	
	Menu createRootContextMenu(ElementalList list);

	/**
	 * Opens up a ContextMenu when a button in an existing ContextMenu is
	 * pressed.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @return The ContextMenu that was created.
	 */
	Menu createSubContextMenu(ElementalList list, Vector2D vector);
	
	Menu createSubContextMenu(ElementalList list);
	
	/**
	 * In a ContextMenu, shows a scrollable menu capable of displaying mass
	 * information like player statistics or inventory.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @param pageSize
	 *            The amount of information per scrollable page.
	 * @return The ContextMenu that was created.
	 */
	void createScrollMenu(ElementalList list, int pageSize, Vector2D vector);
	
	void createScrollMenu(ElementalList list, int pageSize);

	/**
	 * Spawns a DialogMenu at the provided {@link Vector} location.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @param vector
	 *            The location at which to spawn this menu.
	 * @return The ContextMenu that was created.
	 */
	void createDialogMenu(String dialog, Entity avatar, Instance speaker);
	
	/**
	 * Shows a warning to the player.
	 * 
	 * @param list
	 *            A list of items to be presented to the player.
	 */
	void createWarningBox(ElementalList list, boolean blocking);
	
	void createWarningBox(ElementalList list, Vector2D vector, boolean blocking);

	public void clearWarningBox();
	
	/**
	 * Closes a {@link Menu}. This could either be the root, or
	 * somewhere in the menu heirarchy.
	 * 
	 * @param menu
	 *            A Menu to be closed.
	 */
	void closeMenu(Menu menu);
	
	void clearMenuStack();
	
	void popMenu();
	
	View getView();
}
