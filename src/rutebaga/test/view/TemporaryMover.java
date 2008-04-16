package rutebaga.test.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import legacy.KeyBuffer;

import rutebaga.commons.math.Vector;
import rutebaga.model.entity.CharEntity;

public class TemporaryMover implements KeyListener{

	private CharEntity avatar;
	
	private static final double MOVE_SPEED = 0.4;
	
	private static final Vector WEST = new Vector(-MOVE_SPEED, 0.0);
	private static final Vector SOUTH = new Vector(0.0, MOVE_SPEED);
	private static final Vector NORTH = new Vector(0.0, -MOVE_SPEED);
	private static final Vector EAST = new Vector(MOVE_SPEED, 0.0);
	
	private KeyBuffer keyBuffer = new KeyBuffer();
	
	public TemporaryMover(CharEntity avatar ) {
		this.avatar = avatar;
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
	}

}
