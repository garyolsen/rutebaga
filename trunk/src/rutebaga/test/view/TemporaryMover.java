package rutebaga.test.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rutebaga.commons.math.Vector;
import rutebaga.model.entity.CharEntity;

public class TemporaryMover implements KeyListener{

	private CharEntity avatar;
	
	private static final double MOVE_SPEED = 0.4;
	
	private static final Vector WEST = new Vector(-MOVE_SPEED, 0.0);
	private static final Vector SOUTH = new Vector(0.0, MOVE_SPEED);
	private static final Vector NORTH = new Vector(0.0, -MOVE_SPEED);
	private static final Vector EAST = new Vector(MOVE_SPEED, 0.0);
	
	public TemporaryMover(CharEntity avatar ) {
		this.avatar = avatar;
	}
	
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_W)
			avatar.applyImpulse(NORTH);
		if(e.getKeyCode() == KeyEvent.VK_A)
			avatar.applyImpulse(WEST);
		if(e.getKeyCode() == KeyEvent.VK_S)
			avatar.applyImpulse(SOUTH);
		if(e.getKeyCode() == KeyEvent.VK_D)
			avatar.applyImpulse(EAST);
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
