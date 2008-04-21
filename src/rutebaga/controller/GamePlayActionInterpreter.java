package rutebaga.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.CreateRootContextMenuCommand;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.keyboard.ConcreteKeyBinding;
import rutebaga.controller.keyboard.KeyBinding;
import rutebaga.controller.keyboard.KeyBindingList;
import rutebaga.controller.keyboard.KeyCode;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.environment.World;
import rutebaga.model.map.Tile;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.game.MapComponent;
import rutebaga.view.game.TargetInstanceObservable;

public class GamePlayActionInterpreter extends MouseAdapter implements
		UserActionInterpreter, KeyListener {

	private KeyBinding<Command> rebindingState;
	private boolean allowRebinding;
	private KeyBindingList<Command> keyPressBindings;
	private KeyBindingList<Command> keyReleaseBindings;
	private UserInterfaceFacade facade;
	private GameDaemon daemon;
	private ActionDeterminer actionDeterminer;
	private boolean paused;

	private World world;
	private Entity<?> avatar;

	private TargetInstanceObservable observable = new TargetInstanceObservable();
	private Instance<?> target;

	private Vector2D viewCenter;

	public GamePlayActionInterpreter(World world, Entity<?> avatar) {
		this.world = world;
		this.avatar = avatar;
		// TODO: get rid of this
		tracker = new BoundsTracker(new EllipseBounds2D(new Vector2D(3, 3)),
				avatar);

		this.actionDeterminer = new ActionDeterminer(avatar);
		this.keyPressBindings = new KeyBindingList<Command>();
		this.keyReleaseBindings = new KeyBindingList<Command>();
		// TODO: redo the way movement bindings work
		MovementBindingSource movementBindings = MovementBindingSource.defaultBindings(avatar,
				this.keyPressBindings);
		initializeKeyBindings();
	}

	private void initializeKeyBindings() {
		keyPressBindings.set(KeyCode.get(KeyEvent.VK_SPACE), new Command() {
			public void execute() {
				explode();
			}

			public boolean isFeasible() {
				return true;
			}
		});

		keyPressBindings.set(KeyCode.get(KeyEvent.VK_ENTER), new Command() {
			public void execute() {
				GamePlayActionInterpreter.this.avatar
						.getAbilities()
						.get(0)
						.act(
								target != null ? target
										: GamePlayActionInterpreter.this.avatar);
			}

			public boolean isFeasible() {
				return true;
			}
		});

		keyReleaseBindings.set(KeyCode.get(KeyEvent.VK_SEMICOLON), new Command() {
			public void execute() {
				targetNextEntity();
			}

			public boolean isFeasible() {
				return true;
			}
		});

		keyReleaseBindings.set(KeyCode.get(KeyEvent.VK_ESCAPE), new Command() {
			public void execute() {
				paused = true;
				ConcreteElementalList list = new ConcreteElementalList();
				list.setLabel("Are you sure you want to quit?");
				list.add("Yes", new Command() {
					public void execute() {
						// TODO: save game state!!!OMGsdfsMOSFDAdsfafd
						System.exit(1);
					}
					public boolean isFeasible() {
						return true;
					}
				});
				list.add("No", new Command() {
					public void execute() {
						facade.clearWarningBox();
						paused = false;
					}
					public boolean isFeasible() {
						return true;
					}
				});
				facade.createWarningBox(list, true);
			}

			public boolean isFeasible() {
				return true;
			}
		});

	}

	public boolean eventsFallThrough() {
		// I'm a root interpreter!
		return false;
	}

	public void reactivateActionInterpreter() {
		// TODO Auto-generated method stub
	}

	public void installActionInterpreter(GameDaemon daemon, Game game,
			UserInterfaceFacade facade) {
		facade.createGamePlayScreen(avatar, observable);
		daemon.registerAsKeyListener(this);
		daemon.registerAsMouseListener(this);

		this.facade = facade;
		this.daemon = daemon;

		this.actionDeterminer.setUIFacade(facade);
		this.viewCenter = new Vector2D(facade.getView().getWidth() / 2, facade
				.getView().getHeight() / 2);
	}

	public void tick() {
		if (!paused) {
			if (world != null) {
				world.tick();
				updateTargetedTile();
			}
		}
	}

	public void uninstallActionInterpreter() {
		// TODO Auto-generated method stub

	}

	private boolean rebindEvent(KeyEvent e) {
		return (e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK;
	}

	public void keyPressed(KeyEvent e) {
		if (rebindingState != null) {
			completeRebinding(KeyCode.get(e.getKeyCode()));
			return;
		}
		allowRebinding = false;
		KeyBinding<Command> binding = this.keyPressBindings.get(KeyCode.get(e));
		if (binding != null) {
			if (rebindEvent(e))
				startRebinding(binding);
			else
				binding.getBinding().execute();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (rebindingState != null && e.getKeyCode() != KeyEvent.VK_SHIFT)
			return;
		allowRebinding = true;
		KeyBinding<Command> binding = this.keyReleaseBindings.get(KeyCode.get(e));
		if (binding != null)
			binding.getBinding().execute();
	}
	
	public void keyTyped(KeyEvent e) {

	}

	private void startRebinding(KeyBinding<Command> binding) {
		rebindingState = binding;
		paused = true;
		facade.createDialogMenu("Key Binding!lolz");
	}

	private void completeRebinding(KeyCode code) {
		if (allowRebinding) {
			facade.popContextMenu();
			keyPressBindings.remove(rebindingState);
			KeyBinding<Command> newBinding = new ConcreteKeyBinding<Command>(code,
					rebindingState.getBinding());
			keyPressBindings.set(newBinding);
			rebindingState = null;
			paused = false;
		}
	}

	private final BoundsTracker tracker;

	private void explode() {
		Set<Instance> set = tracker.getInstances();
		for (Instance instance : set) {
			if (instance.getSetIdentifier()
					.equals(InstanceSetIdentifier.ENTITY)
					|| instance.getSetIdentifier().equals(
							InstanceSetIdentifier.EFFECT)) {
				MutableVector2D direction = new MutableVector2D(instance
						.getCoordinate().getX(), instance.getCoordinate()
						.getY());
				direction.detract(avatar.getCoordinate());
				direction.multiplyBy((3 - direction.getMagnitude()) / 3);
				instance.applyMomentum(direction);
			}
		}
	}

	private InstanceSet determineTargetSet(Point screenLocation) {
		Environment environment = avatar.getEnvironment();
		TileConverter converter = environment.getTileConvertor();

		Vector2D vector = MapComponent.reverseCenter(avatar, screenLocation,
				facade.getView().getWidth(), facade.getView().getHeight());

		IntVector2D tileCoordinates = converter.tileOf(vector);

		if (avatar.canSee(tileCoordinates)) {
			InstanceSet set = new ConcreteInstanceSet();
			set.addAll(environment.instancesAt(tileCoordinates));
			return set;
		}

		return null;
	}

	private static Tile<?> findTileAt(Instance<?> instance) {
		InstanceSet set = new ConcreteInstanceSet();
		set.add(instance);
		set.addAll(instance.getCoexistantInstances());
		for (Tile tile : set.getTiles())
			if (tile != null)
				return tile;
		return null;
	}

	public void mouseClicked(MouseEvent event) {
		switch (event.getID()) {
		case MouseEvent.MOUSE_CLICKED:
			if (event.getButton() == MouseEvent.BUTTON1) {
				InstanceSet set = determineTargetSet(event.getPoint());
				if (set == null)
					target = null;
				else
					target = actionDeterminer.determineTarget(set);
			} else
				target = null;
			retarget();
			break;
		}
	}

	private void retarget() {
		facade.clearContextMenuStack();
		if (target != null) {
			ElementalList list = actionDeterminer.determineActions(target,
					daemon.getCommandQueue());
			if (list != null) {
				CreateRootContextMenuCommand command = new CreateRootContextMenuCommand(
						list);
				command.setUIFacade(facade);
				command.setLocation(viewCenter);
				daemon.getCommandQueue().queueCommand(command);
			}
		}
		refreshTargetObservable();
	}

	private void refreshTargetObservable() {
		observable.setChanged();
		if (target != null) {
			Tile<?> tile = findTileAt(target);
			observable.notifyAllObservers(tile);
		} else
			observable.notifyAllObservers(null);
	}

	private void targetNextEntity() {
		InstanceSet set = new ConcreteInstanceSet();
		set.addAll(avatar.getVision().getActiveSet());

		Instance<?> newTarget = null;
		boolean targetFound = false;

		for (Entity entity : set.getEntities()) {
			if (newTarget == null)
				newTarget = entity;
			if (entity == target)
				targetFound = true;
			else if (targetFound) {
				newTarget = entity;
				break;
			}
		}

		if (newTarget != target) {
			target = newTarget;
			retarget();
		}
	}

	private void updateTargetedTile() {
		if (target != null) {
			// FIXME: lol
			IntVector2D tileCoordinate = avatar.getEnvironment()
					.getTileConvertor().tileOf(target.getCoordinate());
			if (!avatar.canSee(tileCoordinate)) {
				target = null;
				retarget();
				return;
			}
		}
		refreshTargetObservable();
	}

}
