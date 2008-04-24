package rutebaga.view;

import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Stack;

import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.environment.Instance;
import rutebaga.view.game.*;
import rutebaga.view.rwt.ContextMenu;
import rutebaga.view.rwt.Menu;
import rutebaga.view.rwt.ScrollDecorator;
import rutebaga.view.rwt.StatRibbon;
import rutebaga.view.rwt.TextFieldListener;
import rutebaga.view.rwt.TextLabelComponent;
import rutebaga.view.rwt.View;
import rutebaga.view.rwt.ViewComponent;
import rutebaga.view.rwt.WarningBox;

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
public class ViewFacade implements UserEventSource, UserInterfaceFacade {

	private View view;

	private Stack<ViewComponent> contextStack = new Stack<ViewComponent>();

	private WarningBox warningBox;
	
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
	public void constructView(int width, int height) {
		view = new View(width, height);
	}

	/**
	 * Calls the {@link rutebaga.view.rwt.View View} constructor with the defaul
	 * parameters (800,600) and then sets the View to full screen mode. Default
	 * parameters are used to ensure the proportions of the View.
	 * 
	 * @see rutebaga.view.rwt.View#View(int, int)
	 */
	public void constructFullscreenView() {
		view = new View(1024, 768);
		//view.setFullscreen();
	}

	/**
	 * If the {@link rutebaga.view.rwt.View View} has been initialized, this
	 * will call {@link rutebaga.view.rwt.View#renderFrame()}. Otherwise
	 * nothing is done.
	 */
	public void renderFrame() {
		if (view != null)
			view.renderFrame();
	}

	/**
	 * Shows a title screen based on this ElementalList.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 */
	public void createTitleScreen(ElementalList list) {
		clearView();

		view.addViewComponent(new TitleScreen(list, view.getWidth(), view
				.getHeight()));
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
			ElementalList list, Command accept, Command cancel) {
		clearView();

		Rectangle dimensions = new Rectangle(view.getWidth(), view.getHeight());
		view.addViewComponent(new AvatarCreationScreen(dimensions, listener, list, accept, cancel));
	}

	public void createGamePlayScreen(Entity avatar,
			TargetInstanceObservable observable, Collection<StatValue> stats) {
		clearView();

		view.addViewComponent(new MapComponent(observable, avatar, view.getWidth(), view.getHeight()));
		
		/*FPSTextComponent fps = new FPSTextComponent();
		fps.setFontColor(Color.RED);
		fps.setLocation(100, 100);
		view.addViewComponent(fps);*/
		

		StatRibbon ribbon = new StatRibbon(stats);
		view.addViewComponent(ribbon);
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
	public Menu createRootContextMenu(ElementalList list, Vector2D vector) {
		clearMenuStack();
		ContextMenu cm = new ContextMenu(list);
		cm.setLocation(vector.getX().intValue(), (int) vector.getY()
						.intValue());

		view.addViewComponent(cm);

		contextStack.push(cm);

		return cm;
	}

	public Menu createRootContextMenu(ElementalList list) {
		return createRootContextMenu(list, new Vector2D(view.getWidth() / 2,
				view.getHeight() / 2));
	}

	/**
	 * Opens up a ContextMenu when a button in an existing ContextMenu is
	 * pressed.
	 * 
	 * @param list
	 *            A list of choices for the player.
	 * @return The ContextMenu that was created.
	 */
	public Menu createSubContextMenu(ElementalList list, Vector2D vector) {
			
		ContextMenu cm = new ContextMenu(list);
		cm.setLocation(vector.getX().intValue(), (int)vector.getY().intValue());
		
		view.addViewComponent(cm);
		
		
		prepareContextStack();
		contextStack.push(cm);
		
		return cm;
	}

	public Menu createSubContextMenu(ElementalList list) {
		return createSubContextMenu(list, new Vector2D(view.getWidth() / 2,
				view.getHeight() / 2));
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
	public void createScrollMenu(ElementalList list, int pageSize, Vector2D vector) {

		ViewCompositeComponentWrapper vcc = new ViewCompositeComponentWrapper(list);

		ScrollDecorator scroll = new ScrollDecorator(vcc, pageSize * 20, 50);

		scroll.setLocation(vector.getX().intValue() - scroll.getWidth()/2, vector.getY().intValue() - scroll.getHeight()/2);

		view.addViewComponent(scroll);

		prepareContextStack();
		contextStack.add(scroll);
	}

	public void createScrollMenu(ElementalList list, int pageSize) {
		createScrollMenu(list, pageSize, new Vector2D(
				view.getWidth() / 2, view.getHeight() / 2));
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
	public void createDialogMenu(String dialog, Entity avatar, Instance speaker) {
		TextLabelComponent text = new TextLabelComponent(dialog, TextLabelComponent.CENTER_ALIGNMENT);
		
		DialogDecorator decorator = new DialogDecorator(text, avatar, speaker, view);
		
		prepareContextStack();
		contextStack.push(decorator);
		
		view.addViewComponent(decorator);
	}

	/**
	 * Shows a warning to the player.
	 * 
	 * @param list
	 *            A list of items to be presented to the player.
	 */
	public void createWarningBox(ElementalList list, boolean blocking) {
		int width = 400;
		int height = 200;
		createWarningBox(list, new Vector2D((view.getWidth() - width) / 2, (view.getHeight() - height) / 2), new Vector2D(400, 200), blocking);
	}

	public void createWarningBox(ElementalList list, Vector2D location, boolean blocking) {
		createWarningBox(list, location, new Vector2D(view.getWidth() / 2, view.getHeight() / 2), blocking);
	}

	public void createWarningBox(ElementalList list, Vector2D location, Vector2D visibleDimensions, boolean blocking) {
		clearWarningBox();
		Vector2D blockingDimensions = blocking ? new Vector2D(view.getWidth(), view.getHeight()) : visibleDimensions;
		warningBox = new WarningBox(list, visibleDimensions, blockingDimensions);
		if (blocking)
			warningBox.setLocation(0, 0);
		else
			warningBox.setLocation(location.getX().intValue(), location.getY().intValue());
		view.addViewComponent(warningBox);
	}

	public void clearWarningBox() {
		if (warningBox != null) {
			view.removeViewComponent(warningBox);
			warningBox = null;
		}
	}
	
	/**
	 * Closes a {@link ContextMenu}. This could either be the root, or
	 * somewhere in the menu hierarchy.
	 * 
	 * @param menu
	 *            A ContextMenu to be closed.
	 */
	public void closeMenu(Menu menu) {
		if (contextStack.contains(menu))
			while (!contextStack.isEmpty()) {
				ViewComponent vc = contextStack.pop();
				view.removeViewComponent(vc);
				if (menu.equals(vc))
					break;
			}
				
	}

	public void clearMenuStack() {
		while (!contextStack.isEmpty()) {
			ViewComponent vc = contextStack.pop();
			view.removeViewComponent(vc);
		}
	}

	public void popMenu() {
		view.removeViewComponent(contextStack.pop());
		if (contextStack.size() > 0)
			view.addViewComponent(contextStack.peek());
	}
	
	public Menu getActiveMenu()
	{
		if (!contextStack.isEmpty()) {
			ViewComponent vc = contextStack.peek();
			if (vc instanceof Menu) // FIXME: so I don't use instanceof
				return (Menu) vc;
		}
		return null;
	}

	private void prepareContextStack() {
		if (!contextStack.isEmpty())
			view.removeViewComponent(contextStack.peek());
	}

	public View getView() {
		return view;
	}

	private void clearView() {
		view.removeAllViewComponents(view.getViewComponents());
	}

	public void addKeyListener(KeyListener kl) {
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

}
