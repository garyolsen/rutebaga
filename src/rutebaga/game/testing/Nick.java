package rutebaga.game.testing;

import java.util.Random;

import rutebaga.commons.math.Vector2D;
import rutebaga.game.AgabaturNewGameInitializer;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.DecalType;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.scaffold.MasterScaffold;

public class Nick {
	static Environment environment;
	static Entity avatar;
	static Random random = new Random();

	public static void run(Environment environment, MasterScaffold scaffold,
			Entity avatar)
	{
		Nick.environment = environment;
		Nick.avatar = avatar;

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
						Thread.sleep(2000);
						System.out.println("NPC STATS");
						System.out.println(AgabaturNewGameInitializer.trackedEntity.getStats());
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
