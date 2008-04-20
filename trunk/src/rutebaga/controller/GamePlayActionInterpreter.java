package rutebaga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.CommandQueue;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.TileConvertor;
import rutebaga.model.environment.World;
import rutebaga.model.map.Tile;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;
import rutebaga.view.game.MapComponent;
import rutebaga.view.game.TargetInstanceObservable;

public class GamePlayActionInterpreter implements UserActionInterpreter, KeyListener, ActionListener {

	private World world;
	
	private Entity<?> avatar;
	
	private UserInterfaceFacade facade;
	
	private CommandQueueImpl commandQueue = new CommandQueueImpl();
	
	private TargetInstanceObservable observable = new TargetInstanceObservable();
	private Entity targetedEntity;
	
	public GamePlayActionInterpreter(World world, Entity<?> avatar) {
		this.world = world;
		this.avatar = avatar;
		//TODO: get rid of this
		tracker = new BoundsTracker(new EllipseBounds2D(new Vector2D(3, 3)), avatar);
	}
	
	public boolean eventsFallThrough() {
		// Root interpreter
		return false;
	}

	public void reactivateActionInterpreter() {
		// TODO Auto-generated method stub
	}

	public void installActionInterpreter(GameDaemon daemon, Game game, UserInterfaceFacade facade) {
		facade.createGamePlayScreen(avatar, observable);
		daemon.registerAsKeyListener(this);
		daemon.registerAsActionListener(this);
		
		this.facade = facade;
	}

	public void tick() {
		if (world != null) {
			world.tick();
			commandQueue.processQueue();
			
			updateTargeting();
		}
	}
	
	private void updateTargeting() {
		TileConvertor convertor = avatar.getEnvironment().getTileConvertor();
		
		if(targetedEntity != null) {
			IntVector2D tileCoord = convertor.tileOf(targetedEntity.getCoordinate());
			if(avatar.canSee(tileCoord)) {
				InstanceSet set = new ConcreteInstanceSet();
				set.addAll(avatar.getEnvironment().instancesAt(tileCoord));
				
				for(Tile tile : set.getTiles()) {
					if(tile != null) {
						observable.setChanged();
						observable.notifyAllObservers(tile); 
						break;
					}
				}
			} else {
				targetedEntity = null;
				facade.clearContextMenuStack();
				observable.setChanged();
				observable.notifyAllObservers(null);
			}
		}
	}
	
	private void queueCommand(Command command ) {
		commandQueue.queueCommand(command);
	}

	public void uninstallActionInterpreter() {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		
		final double MOVE_SPEED = 0.2;
		final Vector2D SOUTH = new Vector2D(MOVE_SPEED
				/ Math.sqrt(2), MOVE_SPEED / Math.sqrt(2));
		final Vector2D NORTH = new Vector2D(-MOVE_SPEED
				/ Math.sqrt(2), -MOVE_SPEED / Math.sqrt(2));
		final Vector2D SOUTHEAST = new Vector2D(MOVE_SPEED, 0);
		final Vector2D NORTHEAST = new Vector2D(0, -MOVE_SPEED);
		final Vector2D SOUTHWEST = new Vector2D(0, MOVE_SPEED);
		final Vector2D NORTHWEST = new Vector2D(-MOVE_SPEED, 0);		

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			avatar.walk(NORTH);
			break;
		case KeyEvent.VK_A:
			avatar.walk(SOUTHWEST);
			break;
		case KeyEvent.VK_S:
			avatar.walk(SOUTH);
			break;
		case KeyEvent.VK_D:
			avatar.walk(SOUTHEAST);
			break;
		case KeyEvent.VK_Q:
			avatar.walk(NORTHWEST);
			break;
		case KeyEvent.VK_E:
			avatar.walk(NORTHEAST);
			break;
		case KeyEvent.VK_SPACE:
			explode();
			break;
		case KeyEvent.VK_ENTER:
			avatar.getAbilities().get(0).act(avatar);
			break;
		}
	}

	private final BoundsTracker tracker;
	private void explode()
	{
		Set<Instance> set = tracker.getInstances();
		for(Instance instance : set)
		{
			if(instance.getSetIdentifier().equals(InstanceSetIdentifier.ENTITY) || instance.getSetIdentifier().equals(InstanceSetIdentifier.EFFECT))
			{
				MutableVector2D direction = new MutableVector2D(instance.getCoordinate().getX(), instance.getCoordinate().getY());
				direction.detract(avatar.getCoordinate());
				direction.multiplyBy((3-direction.getMagnitude())/3);
				instance.applyMomentum(direction);
			}
		}
	}

	
	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getID() == MouseEvent.MOUSE_CLICKED) {
			
			Environment environment = avatar.getEnvironment();
			TileConvertor converter = environment.getTileConvertor();
			
			MouseEvent mouseEvent = (MouseEvent) event.getSource();
			
			
			Vector2D vector = MapComponent.reverseCenter(converter, avatar.getCoordinate(), mouseEvent.getPoint(), facade.getView().getWidth(), facade.getView().getHeight());
			
			System.out.println("New Vector : " + vector.getX() + ", " + vector.getY());
			System.out.println("Avatar Vector : "  + avatar.getCoordinate().getX() + ", " + avatar.getCoordinate().getY());
			
			IntVector2D tileCoord = converter.tileOf(vector);
			
			System.out.println("Tile Coord of Click: " + tileCoord.getX() + ", " + tileCoord.getY());
			
			if(avatar.canSee(tileCoord)) {
				
				InstanceSet set = new ConcreteInstanceSet();
				set.addAll(environment.instancesAt(tileCoord));
				
				for(Entity entity : set.getEntities()) {
					
					if(entity != null) {
						ConcreteElementalList list = new ConcreteElementalList();
						
						for (Ability<?> a : avatar.getAbilities() ) {
							list.add(a.toString(), new ContextMenuCommand(new AbilityCommand(entity, a)));
						}
						
						targetedEntity = entity;
						
						for(Tile tile : set.getTiles()) {
							if(tile != null) {
								observable.setChanged();
								observable.notifyAllObservers(tile); 
								break;
							}
						
						}
						facade.clearContextMenuStack();
						facade.createRootContextMenu(list, new Vector2D(facade.getView().getWidth()/2, facade.getView().getHeight()/2));
						
						return;
					}
				}
			}
			
			
			

			targetedEntity = null;
			facade.clearContextMenuStack();
			
			observable.setChanged();
			observable.notifyAllObservers(null);

		}
	}
	
	private class ContextMenuCommand implements Command {

		private Command command;
		
		public ContextMenuCommand(Command command) {
			this.command = command;
		}
		
		public void execute() {
			queueCommand(command);
		}

		public boolean isFeasible() {
			return command.isFeasible();
		}
		
	}

	
	private class AbilityCommand implements Command {

		private Ability a;
		private Instance target;
		
		public AbilityCommand(Instance target, Ability a) {
			this.a = a;
			this.target = target;
		}
		
		public void execute() {
			a.act(target);
		}

		public boolean isFeasible() {
			return a.isFeasible();
		}
		
	}
	
	private static class CommandQueueImpl implements CommandQueue
	{
		/**
		 * The command queue.
		 */
		private Queue<Command> queue;

		public CommandQueueImpl()
		{
			queue = new LinkedList<Command>();
		}

		/**
		 * Allows clients to automatically queue a
		 * {@link rutebaga.controller.command.Command}.
		 * 
		 * @param command
		 *            The Command to queue.
		 * @see rutebaga.controller.CommandQueue#queueCommand(rutebaga.controller.Command)
		 */
		public void queueCommand(Command command)
		{
			queue.offer(command);
		}

		/**
		 * Iterates through the command queue, removing all commands and
		 * executing them.
		 */
		public void processQueue()
		{
			while (!queue.isEmpty())
			{
				Command command = queue.poll();
				command.execute();
			}
		}
	}

}
