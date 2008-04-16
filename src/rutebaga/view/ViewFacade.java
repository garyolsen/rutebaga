package rutebaga.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import rutebaga.commons.math.Vector;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.CharEntity;
import rutebaga.view.game.*;
import rutebaga.view.rwt.ContextMenu;
import rutebaga.view.rwt.TextFieldListener;
import rutebaga.view.rwt.View;

/**
 * Provides a unified interface to manipulate the View Subsystem without
 * intimate knowledge of how the View Subsystem works using the Facade pattern.
 * <p>
 * Responsibilities are to
 * <ul>
 * <li>Initialize the View system for rendering (includes setting up key, mouse
 * and mouseMotion listeners)
 * <li>Construct and tear down the view when transitioning between different
 * states of the game (Title, AvatarCreation, GamePlay)
 * <li>Manage the ContextMenu stack
 * <li>Render the View for this current frame
 * </ul>
 * 
 * @see rutebaga.view.drawer.Drawer
 * @see rutebaga.view.rwt.ContextMenu
 * @see rutebaga.view.rwt.ViewComponent
 * @see rutebaga.view.rwt.EventDispatcher
 * 
 */
public class ViewFacade
{

	private View view;

	/**
	 * Passes the parameters to the {@link rutebaga.view.rwt.View View}
	 * Constructor.
	 * 
	 * @param width
	 *            Width of the game.
	 * @param height
	 *            Height of the game.
	 * @see rutebaga.view.rwt.View#View(int, int)
	 */
	public void constructView(int width, int height)
	{
		view = new View(width, height);
	}

	/**
	 * Calls the {@link rutebaga.view.rwt.View View} constructor with the defaul
	 * parameters (800,600) and then sets the View to full screen mode. Default
	 * parameters are used to ensure the proportions of the View.
	 * 
	 * @see rutebaga.view.rwt.View#View(int, int)
	 */
	public void constructFullscreenView()
	{
		view = new View(800, 600);
		view.setFullscreen();
	}

	/**
	 * If the {@link rutebaga.view.rwt.View View} has been initialized, this
	 * will call {@link rutebaga.view.rwt.View#renderFrame()}. Otherwise
	 * nothing is done.
	 */
	public void renderFrame()
	{
		if (view != null)
			view.renderFrame();
	}

	/**
	 * Shows a title screen based on this ElementalList.
	 * 
	 * @param list
	 *            A list of choises for the player.
	 */
	public void createTitleScreen(ElementalList list)
	{
		clearView();
		
		view.addViewComponent(new TitleScreen(list, view.getWidth(), view.getHeight()));
			
	}

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
	 *            The {@link Command} for what to do on cancelation.
	 */
	public void createAvatarCreationScreen(TextFieldListener listener,
			ElementalList list, Command accept, Command cancel)
	{
		clearView();
		
		view.addViewComponent(new AvatarCreationScreen(view.getWidth(), view.getHeight(), listener, list, accept, cancel) );
	}
	
	
	public void createGamePlayScreen(CharEntity avatar) {
		clearView();
		
		view.addViewComponent(new MapComponent(avatar, view.getWidth(), view.getHeight()));
	}

	/**
	 * Spawns a ContextMenu at the {@link Vector} location provided.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @param vector
	 *            The location to spawn the menu.
	 * @return The ContextMenu that was created.
	 */
	public ContextMenu createRootContextMenu(ElementalList list, Vector vector)
	{
		return null;
	}

	/**
	 * Opens up a ContextMenu when a button in an existing ContextMenu is
	 * pressed.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @return The ContextMenu that was created.
	 */
	public ContextMenu createSubContextMenu(ElementalList list)
	{
		return null;
	}

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
	public ContextMenu createScrollMenu(ElementalList list, int pageSize)
	{
		return null;
	}

	/**
	 * Spawns a DialogMenu at the provided {@link Vector} location.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @param vector
	 *            The location at which to spawn this menu.
	 * @return The ContextMenu that was created.
	 */
	public ContextMenu createDialogMenu(ElementalList list, Vector vector)
	{
		return null;
	}

	/**
	 * Shows a warning to the player.
	 * 
	 * @param list
	 *            A list of items to be presented to the player.
	 */
	public void createWarningBox(ElementalList list)
	{

	}

	/**
	 * Closes a {@link ContextMenu}. This could either be the root, or
	 * somewhere in the menu heirarchy.
	 * 
	 * @param menu
	 *            A ContextMenu to be closed.
	 */
	public void closeContextMenu(ContextMenu menu)
	{

	}
	
	
	private void clearView() {
		view.removeAllViewComponents(view.getViewComponents());
	}
	
	public void addKeyListener(KeyListener kl ) {
		view.addKeyListener(kl);
	}
	
	public void removeKeyListener(MouseListener ml) {
		view.removeMouseListener(ml);
	}
	
	public void addMouseListener(MouseListener ml) {
		view.addMouseListener(ml);
	}
	
	public void removeMouseListener(MouseListener ml) {
		view.removeMouseListener(ml);
	}
}
