package rutebaga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Set;

import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.World;
import rutebaga.view.ViewFacade;

public class GamePlayActionInterpreter implements UserActionInterpreter {

	private World world;
	
	private CharEntity avatar;
	
	public GamePlayActionInterpreter(World world, CharEntity avatar) {
		this.world = world;
		this.avatar = avatar;
		//TODO: get rid of this
		tracker = new BoundsTracker(new EllipseBounds2D(new Vector2D(3, 3)), avatar);
	}
	
	public boolean eventsFallThrough() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reactivateActionInterpreter() {
		// TODO Auto-generated method stub
		
	}

	public void installActionInterpreter(GameDaemon daemon, Game game, ViewFacade facade) {
		facade.createGamePlayScreen(avatar); 
	}

	public void tick() {
		if(world != null) world.tick();
	}

	public void uninstallActionInterpreter() {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0) {
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
			if(instance.getSetIdentifier().equals(InstanceSetIdentifier.ENTITY))
			{
				MutableVector2D direction = new MutableVector2D(instance.getCoordinate().getX(), instance.getCoordinate().getY());
				direction.detract(avatar.getCoordinate());
				direction.multiplyBy((3-direction.getMagnitude())/3);
				instance.applyMomentum(direction);
			}
		}
	}

	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
