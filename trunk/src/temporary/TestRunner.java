package temporary;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import legacy.GraphicsManager;
import legacy.KeyBuffer;
import rutebaga.commons.math.Vector;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.EntityType;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.map.Tile;

/*
public class TestRunner
{
	public static double MOVE_AMT = 0.1;
	public static int SCREEN_WIDTH = 640;
	public static int SCREEN_HEIGHT = 480;

	public static Entity avatar;

	public static void main(String... args)
	{
		GraphicsManager manager = new GraphicsManager();
		TestImageConverter.manager = manager;
		manager.init(SCREEN_WIDTH, SCREEN_HEIGHT);
		Vector screen = new Vector(SCREEN_WIDTH, SCREEN_HEIGHT);
		manager.registerKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				switch (e.getKeyChar())
				{
				case 'q':
					System.exit(0);
					break;
				}

			}

			public void keyReleased(KeyEvent e)
			{

			}

			public void keyTyped(KeyEvent e)
			{

			}
		});

		KeyBuffer buffer = new KeyBuffer();
		manager.registerKeyListener(buffer);

		Environment environment = new Environment(new Rect2DTileConvertor());

		for (int x = -5; x < 35; x++)
		{
			for (int y = -5; y < 75; y++)
			{
				Vector location = new Vector(x, y);
				Tile tile = new Tile()
				{

					@Override
					public double getFriction()
					{
						return 0.1;
					}

				};
				environment.add(tile, location);
			}
		}

		for (int x = 0; x < 30; x++)
		{
			for (int y = 0; y < 30; y++)
			{
				Vector location = new Vector(x, y);
				if (x % 8 == 0 && y % 8 == 0)
				{
					WindTunnel tunnel = new WindTunnel();
					environment.add(tunnel, location);
				}
			}
		}

		for (int x = 0; x < 30; x++)
		{
			for (int y = 40; y < 70; y++)
			{
				Vector location = new Vector(x, y);
				if (x % 8 == 0 && y % 8 == 0)
				{
					Instance instance = new Bumper();
					environment.add(instance, location);
				}
			}
		}

		avatar = new CharEntity()
		{

			@Override
			public double getFriction()
			{
				return 0;
			}

			@Override
			public double getMass()
			{
				return 1;
			}

			@Override
			public Object accept(EntityEffect e)
			{
				return null;
			}

		};

		environment.add(avatar, new Vector(10, 10));

		EntityRenderer renderer = new EntityRenderer(avatar);

		long frame = 0;
		long time, sleepTime;
		long desiredTime = 1000 / 30;

		while (true)
		{
			time = System.currentTimeMillis();
			process(buffer);
			environment.tick();
			Graphics2D g = manager.getGraphicsContext();
			renderer.draw(g, avatar.getCoordinate().minus(
					screen.times(0.5).times(1.0 / 32.0)));
			g.dispose();
			manager.draw();
			// rutebaga.commons.Log.log();
			// rutebaga.commons.Log.log(avatar.getCoordinate() + "; v="
			// + avatar.getVelocity() + "; p=" + avatar.getMomentum());

			sleepTime = desiredTime - System.currentTimeMillis() + time;
			if (sleepTime > 0)
				try
				{
					Thread.sleep(sleepTime);
				}
				catch (InterruptedException e)
				{
					throw new RuntimeException(e);
				}

			frame++;
			rutebaga.commons.Log.log(frame + "\t"
					+ (1000 / (System.currentTimeMillis() - time)));
		}
	}

	private static void process(KeyBuffer buffer)
	{
		buffer.poll();

		if (buffer.isPressedOnce(KeyEvent.VK_SHIFT))
			MOVE_AMT = MOVE_AMT * 3;

		if (buffer.isPressed(KeyEvent.VK_A))
			avatar.applyMomentum(new Vector(-MOVE_AMT, 0));
		if (buffer.isPressed(KeyEvent.VK_S))
			avatar.applyMomentum(new Vector(0, MOVE_AMT));
		if (buffer.isPressed(KeyEvent.VK_D))
			avatar.applyMomentum(new Vector(MOVE_AMT, 0));
		if (buffer.isPressed(KeyEvent.VK_W))
			avatar.applyMomentum(new Vector(0, -MOVE_AMT));

		if (buffer.isPressed(KeyEvent.VK_J))
			avatar.applyImpulse(new Vector(-MOVE_AMT * 3, 0));
		if (buffer.isPressed(KeyEvent.VK_K))
			avatar.applyImpulse(new Vector(0, MOVE_AMT * 3));
		if (buffer.isPressed(KeyEvent.VK_L))
			avatar.applyImpulse(new Vector(MOVE_AMT * 3, 0));
		if (buffer.isPressed(KeyEvent.VK_I))
			avatar.applyImpulse(new Vector(0, -MOVE_AMT * 3));
	}
}
*/
