package rutebaga.view;

import rutebaga.commons.math.Vector;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.view.rwt.ContextMenu;
import rutebaga.view.rwt.TextFieldListener;

public interface UserInterfaceFacade {
	void createTitleScreen(ElementalList list);
	
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
	
	
	void createGamePlayScreen(Entity avatar);

	/**
	 * Spawns a ContextMenu at the {@link Vector} location provided.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @param vector
	 *            The location to spawn the menu.
	 * @return The ContextMenu that was created.
	 */
	ContextMenu createRootContextMenu(ElementalList list, Vector vector);

	/**
	 * Opens up a ContextMenu when a button in an existing ContextMenu is
	 * pressed.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @return The ContextMenu that was created.
	 */
	ContextMenu createSubContextMenu(ElementalList list);
	
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
	ContextMenu createScrollMenu(ElementalList list, int pageSize);

	/**
	 * Spawns a DialogMenu at the provided {@link Vector} location.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @param vector
	 *            The location at which to spawn this menu.
	 * @return The ContextMenu that was created.
	 */
	ContextMenu createDialogMenu(ElementalList list, Vector vector);
	
	/**
	 * Shows a warning to the player.
	 * 
	 * @param list
	 *            A list of items to be presented to the player.
	 */
	void createWarningBox(ElementalList list);

	/**
	 * Closes a {@link ContextMenu}. This could either be the root, or
	 * somewhere in the menu heirarchy.
	 * 
	 * @param menu
	 *            A ContextMenu to be closed.
	 */
	void closeContextMenu(ContextMenu menu);
}
