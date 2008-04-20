package rutebaga.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rutebaga.commons.math.Vector;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElement;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.view.game.*;
import rutebaga.view.rwt.ButtonComponent;
import rutebaga.view.rwt.ContextMenu;
import rutebaga.view.rwt.ScrollDecorator;
import rutebaga.view.rwt.TextFieldListener;
import rutebaga.view.rwt.View;
import rutebaga.view.rwt.ViewComponent;
import rutebaga.view.rwt.ViewCompositeComponent;

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
public class ViewFacade implements UserEventSource, UserInterfaceFacade
{

	private View view;
	
	private Stack<ViewComponent> contextStack = new Stack<ViewComponent>();

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
		//view.setFullscreen();
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
	 *            A list of choices for the player.
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
	 *            The {@link Command} for what to do on cancellation.
	 */
	public void createAvatarCreationScreen(TextFieldListener listener,
			ElementalList list, Command accept, Command cancel)
	{
		clearView();
		
		view.addViewComponent(new AvatarCreationScreen(view.getWidth(), view.getHeight(), listener, list, accept, cancel) );
	}
	
	
	public void createGamePlayScreen(Entity avatar, TargetInstanceObservable observable) {
		clearView();
		
		view.addViewComponent(new MapComponent(observable, avatar, view.getWidth(), view.getHeight()));
		
		FPSTextComponent fps = new FPSTextComponent();
		fps.setFontColor(Color.RED);
		fps.setLocation(100, 100);
		
		view.addViewComponent(fps);
		
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
	public int createRootContextMenu(ElementalList list, Vector2D vector)
	{
		clearContextMenuStack();
		
		List<String> names = new ArrayList<String>();
		List<Command> commands = new ArrayList<Command>();
		
		for(ListElement element : list) {
			commands.add(element.getCommand());
			names.add(element.getLabel());
		}
			
		ContextMenu cm = new ContextMenu(commands, names);
		cm.setLocation(vector.getX().intValue(), (int)vector.getY().intValue());
		
		view.addViewComponent(cm);
		
		
		contextStack.push(cm);
		
		return contextStack.size();
	}

	/**
	 * Opens up a ContextMenu when a button in an existing ContextMenu is
	 * pressed.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @return The ContextMenu that was created.
	 */
	public int createSubContextMenu(ElementalList list, Vector2D vector)
	{
		return 0;
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
	public int createScrollMenu(ElementalList list, int pageSize, Vector2D vector)
	{
		ViewCompositeComponent vcc = new ViewCompositeComponent();
		
		for(ListElement element : list ) {
			ButtonComponent button = new ButtonComponent(element.getLabel());
			button.setCommand(element.getCommand());
			vcc.addChild(button);
		}
		
		ScrollDecorator scroll = new ScrollDecorator(vcc, pageSize * 10, 50);

		scroll.setLocation(vector.getX().intValue(), vector.getY().intValue());
		
		view.addViewComponent(scroll);
		
		prepareContextStack();
		contextStack.add(scroll);
		
		return contextStack.size();
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
	public int createDialogMenu(ElementalList list, Vector2D vector)
	{
		return 0;
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
	public void closeContextMenu(int menu)
	{

	}
	
	public void clearContextMenuStack() {
		while(!contextStack.isEmpty()) {
			ViewComponent vc = contextStack.pop();
			view.removeViewComponent(vc);
		}
	}
	
	public void popContextMenu() {
		view.removeViewComponent(contextStack.pop());
		if(contextStack.size() > 0)
			view.addViewComponent(contextStack.peek());
	}
	
	private void prepareContextStack() {
		view.removeViewComponent(contextStack.peek());
	}
	
	public View getView() {
		return view;
	}
	
	
	private void clearView() {
		view.removeAllViewComponents(view.getViewComponents());
	}
	
	public void addKeyListener(KeyListener kl ) {
		view.addKeyListener(kl);
	}
	
	public void removeKeyListener(KeyListener kl) {
		view.removeKeyListener(kl);
	}
	
	public void addMouseListener(MouseListener ml) {
		view.addMouseListener(ml);
	}
	
	public void removeMouseListener(MouseListener ml) {
		view.removeMouseListener(ml);
	}
	
	
	private class AbilityCommand implements Command{ //TODO REMOVE THIS ONCE WE HAVE REAL COMMANDS

		private Entity<?> avatar;
		
		public AbilityCommand(Entity avatar) {
			this.avatar = avatar;
		}
		
		public void execute() {
			avatar.getAbilities().get(0).act(avatar);
		}

		public boolean isFeasible() {
			return true;
		}
		
	}
}
