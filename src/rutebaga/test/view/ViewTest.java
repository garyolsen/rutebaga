package rutebaga.test.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Hex2DTileConvertor;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.map.Tile;
import rutebaga.view.game.FPSTextComponent;
import rutebaga.view.game.MapComponent;
import rutebaga.view.rwt.*;
import temporary.Bumper;
import temporary.WindTunnel;

public class ViewTest
{

	private static Random random = new Random();

	private static int SCREENWIDTH = 640, SCREENHEIGHT = 480;

	private static int N_NPCS = 0;
	private static double TILE_PROB = 1;
	
	private static int SQMAP_MIN = 0;
	private static int SQMAP_MAX = 100;

	private static int[] MAP_BOUNDS =
	{ SQMAP_MIN, SQMAP_MAX, SQMAP_MIN, SQMAP_MAX };

	private static boolean TICK_ENVIRONMENT = true;
	private static boolean RENDER_MAP = true;
	private static boolean USE_VOLATILE_IMAGES = true;

	private static boolean SHOW_TREASURE = false;

	public static void main(String args[])
	{
		View view = new View(SCREENWIDTH, SCREENHEIGHT);
		view.setFullscreen();

		CharEntity avatar;

		Environment environment = new Environment(new Hex2DTileConvertor());

		try
		{
			Image tmp;

			Image cheese = ImageIO.read(new File("TestImages/cheese.png"));
			tmp = view.makeVolatileImage(cheese.getWidth(null), cheese
					.getHeight(null));
			Graphics g = tmp.getGraphics();
			g.drawImage(cheese, 0, 0, null);
			g.dispose();
			if (USE_VOLATILE_IMAGES)
				cheese = tmp;
			cheese.setAccelerationPriority(1.0f);

			Image grass = ImageIO.read(new File("TestImages/hextile.png"));
			tmp = view.makeVolatileImage(grass.getWidth(null), grass
					.getHeight(null));
			g = tmp.getGraphics();
			g.drawImage(grass, 0, 0, null);
			g.dispose();
			if (USE_VOLATILE_IMAGES)
				grass = tmp;
			grass.setAccelerationPriority(1.0f);

			Image treasure = ImageIO.read(new File("TestImages/treasure.png"));
			treasure.setAccelerationPriority(1.0f);
			tmp = view.makeVolatileImage(treasure.getWidth(null), treasure
					.getHeight(null));
			g = tmp.getGraphics();
			g.drawImage(treasure, 0, 0, null);
			g.dispose();
			if (USE_VOLATILE_IMAGES)
				treasure = tmp;
			treasure.setAccelerationPriority(1.0f);

			Random random = new Random();

			for (int x = MAP_BOUNDS[0]; x < MAP_BOUNDS[1]; x++)
			{
				for (int y = MAP_BOUNDS[2]; y < MAP_BOUNDS[3]; y++)
				{
					if (random.nextDouble() < TILE_PROB)
					{
						Vector2D location = new Vector2D(x, y);
						Tile tile = new Tile();
						Appearance water = new Appearance(tile);
						water.setImage(grass);
						tile.setAppearance(water);
						environment.add(tile, location);
					}
				}
			}

			if (SHOW_TREASURE)
			{
				for (int x = MAP_BOUNDS[0] + 4; x < MAP_BOUNDS[1] - 4; x++)
				{
					for (int y = MAP_BOUNDS[2] + 4; y < MAP_BOUNDS[3] / 2 - 4; y++)
					{
						Vector2D location = new Vector2D(x, y);
						if (x % 8 == 0 && y % 8 == 0)
						{
							WindTunnel tunnel = new WindTunnel();
							Appearance chest = new Appearance(tunnel);
							chest.setImage(treasure);
							tunnel.setAppearance(chest);
							environment.add(tunnel, location);
						}
					}
				}

				for (int x = MAP_BOUNDS[0] + 4; x < MAP_BOUNDS[1] - 4; x++)
				{
					for (int y = MAP_BOUNDS[3] / 2 + 4; y < MAP_BOUNDS[3] - 4; y++)
					{
						Vector2D location = new Vector2D(x, y);
						if (x % 8 == 0 && y % 8 == 0)
						{
							Instance instance = new Bumper();
							Appearance chest = new Appearance(instance);
							chest.setImage(treasure);
							instance.setAppearance(chest);
							environment.add(instance, location);
						}
					}
				}
			}

			avatar = new CharEntity();
			avatar.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));

			// npc = new NPCEntity();
			// Appearance npcAppearance = new Appearance(npc);
			// npcAppearance.setImage(treasure);
			// npc.setAppearance(npcAppearance);
			//		
			// environment.add(npc, new Vector2D(20, 20));

			Appearance appearance = new Appearance(avatar);
			appearance.setImage(cheese);
			avatar.setAppearance(appearance);

			environment.add(avatar, new Vector2D(0, 0));

			int xRng = MAP_BOUNDS[1] - MAP_BOUNDS[0];
			int yRng = MAP_BOUNDS[3] - MAP_BOUNDS[2];
			int xMin = MAP_BOUNDS[0];
			int yMin = MAP_BOUNDS[2];

			for (int i = 0; i < N_NPCS; i++)
			{
				NPCEntity npc1 = new NPCEntity();
				npc1.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));
				Appearance npcAppearance1 = new Appearance(npc1);
				npcAppearance1.setImage(cheese);
				npc1.setAppearance(npcAppearance1);

				npc1.setTarget(avatar);

				Vector2D location = new Vector2D(random.nextInt(xRng) + xMin,
						random.nextInt(yRng) + yMin);
				System.out.println("placing npc at " + location);
				environment.add(npc1, location);
			}

			if (RENDER_MAP)
			{
				MapComponent map = new MapComponent(avatar, view.getWidth(),
						view.getHeight());
				view.addViewComponent(map);
			}

			FPSTextComponent fps = new FPSTextComponent();
			fps.setLocation(100, 100);
			fps.setFontColor(Color.RED);

			view.addViewComponent(fps);

			TemporaryMover mover = new TemporaryMover(avatar);
			view.addKeyListener(mover);

			ViewCompositeComponent vcc = new ViewCompositeComponent();

			for (int i = 0; i < 20; i++)
				vcc.addChild(new ButtonComponent("test" + i));

			ScrollDecorator scroll = new ScrollDecorator(vcc, 600, 50);
			scroll.setLocation(300, 850);
			scroll.setScrollSpeed(10);

			view.addViewComponent(scroll);

			long time;
			long envStart;

			while (true)
			{
				time = System.currentTimeMillis();
				System.out.println("\n\n");
				mover.tick();
				view.renderFrame();
				envStart = System.currentTimeMillis();
				System.out.println("view render total: " + (envStart - time));
				if (TICK_ENVIRONMENT)
					environment.tick();
				System.out.println("environment total: "
						+ (System.currentTimeMillis() - envStart));
				System.out.println("total: "
						+ (System.currentTimeMillis() - time));

			}

		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static Vector2D createImpulse()
	{
		double xImpulse = 0.1;
		if (random.nextBoolean())
			xImpulse *= -1.0;

		xImpulse *= random.nextInt(4);

		double yImpulse = 0.1;
		if (random.nextBoolean())
			yImpulse *= -1.0;

		yImpulse *= random.nextInt(7);

		return new Vector2D(xImpulse, yImpulse);
	}
}
