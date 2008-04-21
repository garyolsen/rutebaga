package rutebaga.game.testing;

import java.util.Random;

import rutebaga.scaffold.MasterScaffold;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.DecalType;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;

public class Gary
{
	static DecalType fire;
	static Environment environment;
	static Entity avatar;
	static Random random = new Random();

	public static void run(Environment environment, MasterScaffold scaffold,
			Entity avatar)
	{
		Gary.environment = environment;
		Gary.avatar = avatar;

		fire = (DecalType) scaffold.get("decFire");
		for (int x = 0; x < 40; x++)
			for (int y = 0; y < 40; y++)
				environment.add(fire.makeInstance(), new Vector2D(x, y));

		new Thread()
		{

			@Override
			public void run()
			{
				try
				{
					Thread.sleep(10000);
					while (true)
					{
						Thread.sleep(1000);
						System.out.println("something flag: " + Gary.avatar.getFlag("something"));
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

			}

		}.start();
	}
}
