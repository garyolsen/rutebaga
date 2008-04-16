package rutebaga.test.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

import legacy.KeyBuffer;

import rutebaga.commons.math.EllipseBounds;
import rutebaga.commons.math.MutableVector;
import rutebaga.commons.math.Vector;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public class TemporaryMover implements KeyListener{

	private CharEntity avatar;
	
	private static final double MOVE_SPEED = 0.2;
	
	private static final Vector WEST = new Vector(-MOVE_SPEED, 0.0);
	private static final Vector SOUTH = new Vector(0.0, MOVE_SPEED);
	private static final Vector NORTH = new Vector(0.0, -MOVE_SPEED);
	private static final Vector EAST = new Vector(MOVE_SPEED, 0.0);
	
	private BoundsTracker tracker;
	
	private KeyBuffer keyBuffer = new KeyBuffer();
	
	public TemporaryMover(CharEntity avatar ) {
		this.avatar = avatar;
		tracker = new BoundsTracker(new EllipseBounds(new Vector(3, 3)), avatar);
	}
	
	public void keyPressed(KeyEvent e) {
		keyBuffer.keyPressed(e);
	}

	public void keyReleased(KeyEvent arg0) {
		keyBuffer.keyReleased(arg0);
	}

	public void keyTyped(KeyEvent arg0) {
		keyBuffer.keyTyped(arg0);
	}
	
	public void tick()
	{
		keyBuffer.poll();
		if(keyBuffer.isPressed(KeyEvent.VK_W))
			avatar.applyImpulse(NORTH);
		if(keyBuffer.isPressed(KeyEvent.VK_A))
			avatar.applyImpulse(WEST);
		if(keyBuffer.isPressed(KeyEvent.VK_S))
			avatar.applyImpulse(SOUTH);
		if(keyBuffer.isPressed(KeyEvent.VK_D))
			avatar.applyImpulse(EAST);
		if(keyBuffer.isPressed(KeyEvent.VK_SPACE))
			explode();
	}
	
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

}
