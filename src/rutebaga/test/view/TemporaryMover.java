package rutebaga.test.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

import legacy.KeyBuffer;

import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public class TemporaryMover implements KeyListener
{

	private CharEntity avatar;

	private static final double MOVE_SPEED = 0.03;

	private static final Vector2D SOUTH = new Vector2D(MOVE_SPEED
			/ Math.sqrt(2), MOVE_SPEED / Math.sqrt(2));
	private static final Vector2D NORTH = new Vector2D(-MOVE_SPEED
			/ Math.sqrt(2), -MOVE_SPEED / Math.sqrt(2));
	private static final Vector2D SOUTHEAST = new Vector2D(MOVE_SPEED, 0);
	private static final Vector2D NORTHEAST = new Vector2D(0, -MOVE_SPEED);
	private static final Vector2D SOUTHWEST = new Vector2D(0, MOVE_SPEED);
	private static final Vector2D NORTHWEST = new Vector2D(-MOVE_SPEED, 0);

	private BoundsTracker tracker;

	private KeyBuffer keyBuffer = new KeyBuffer();

	public TemporaryMover(CharEntity avatar)
	{
		this.avatar = avatar;
		tracker = new BoundsTracker(new EllipseBounds2D(new Vector2D(3, 3)),
				avatar);
	}

	public void keyPressed(KeyEvent e)
	{
		keyBuffer.keyPressed(e);
	}

	public void keyReleased(KeyEvent arg0)
	{
		keyBuffer.keyReleased(arg0);
	}

	public void keyTyped(KeyEvent arg0)
	{
		keyBuffer.keyTyped(arg0);
	}

	public void tick()
	{
		keyBuffer.poll();
		MutableVector2D direction = new MutableVector2D(0, 0);
		if (keyBuffer.isPressed(KeyEvent.VK_W))
			direction.accumulate(NORTH);
		if (keyBuffer.isPressed(KeyEvent.VK_A))
			direction.accumulate(SOUTHWEST);
		if (keyBuffer.isPressed(KeyEvent.VK_Q))
			direction.accumulate(NORTHWEST);
		if (keyBuffer.isPressed(KeyEvent.VK_S))
			direction.accumulate(SOUTH);
		if (keyBuffer.isPressed(KeyEvent.VK_D))
			direction.accumulate(SOUTHEAST);
		if (keyBuffer.isPressed(KeyEvent.VK_E))
			direction.accumulate(NORTHEAST);
		if (keyBuffer.isPressed(KeyEvent.VK_SPACE))
			explode();
		if (keyBuffer.isPressedOnce(KeyEvent.VK_ENTER))
			avatar.getAbilities().get(0).act(avatar);
		double magnitude = direction.getMagnitude();
		if (magnitude > 0.001)
		{
			avatar.walk(direction);
		}
	}

	private void explode()
	{
		Set<Instance> set = tracker.getInstances();
		for (Instance instance : set)
		{
			if (instance.getSetIdentifier()
					.equals(InstanceSetIdentifier.ENTITY))
			{
				Vector2D coord = instance.getCoordinate();
				MutableVector2D direction = new MutableVector2D(coord.getX(),
						coord.getY());
				direction.minus(avatar.getCoordinate());
				direction.times((3 - direction.getMagnitude()) / 3);
				instance.applyImpulse(direction);
			}
		}
	}

}
