package rutebaga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Set;

import rutebaga.commons.math.EllipseBounds;
import rutebaga.commons.math.MutableVector;
import rutebaga.commons.math.Vector;
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
		tracker = new BoundsTracker(new EllipseBounds(new Vector(3, 3)), avatar);
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
		world.tick();
	}

	public void uninstallActionInterpreter() {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		
		final double MOVE_SPEED = 0.2;
		final Vector WEST = new Vector(-MOVE_SPEED, 0.0);
		final Vector SOUTH = new Vector(0.0, MOVE_SPEED);
		final Vector NORTH = new Vector(0.0, -MOVE_SPEED);
		final Vector EAST = new Vector(MOVE_SPEED, 0.0);
		
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			avatar.applyImpulse(NORTH);
			break;
		case KeyEvent.VK_A:
			avatar.applyImpulse(WEST);
			break;
		case KeyEvent.VK_S:
			avatar.applyImpulse(SOUTH);
			break;
		case KeyEvent.VK_D:
			avatar.applyImpulse(EAST);
			break;
		case KeyEvent.VK_SPACE:
			explode();
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
				MutableVector direction = new MutableVector(instance.getCoordinate());
				direction.minus(avatar.getCoordinate());
				direction.times((3-direction.getMagnitude())/3);
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
