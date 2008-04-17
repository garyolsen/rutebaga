package rutebaga.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import rutebaga.commons.math.Vector;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.environment.World;
import rutebaga.model.map.Tile;
import rutebaga.scaffold.MasterScaffold;

public class NewGameInitializer implements GameInitializer {

	private static int N_NPCS = 0;
	private static double TILE_PROB = 1;

	private static int[] MAP_BOUNDS =
	{ 0, 30, 0, 30 };

	private static boolean USE_VOLATILE_IMAGES = false;

	
	private CharEntity avatar;
	private World world;
	
	public NewGameInitializer(MasterScaffold scaffold) {
		
	}

	public void build() {
		/*
		 * For now, we are ignoring the scaffold and just creating things manually.
		 */
	
		Environment environment = new Environment(new Rect2DTileConvertor());
		
		java.awt.GraphicsConfiguration gc = java.awt.Frame.getWindows()[0].getGraphicsConfiguration();
		
		try
		{
			VolatileImage tmp;

			Image cheese = ImageIO.read(new File("TestImages/cheese.png"));
			tmp = gc.createCompatibleVolatileImage(cheese.getWidth(null), cheese
					.getHeight(null));
			Graphics g = tmp.getGraphics();
			g.drawImage(cheese, 0, 0, null);
			g.dispose();
			if (USE_VOLATILE_IMAGES)
				cheese = tmp;
			cheese.setAccelerationPriority(1.0f);

			Image grass = ImageIO.read(new File("TestImages/grass.jpg"));
			tmp = gc.createCompatibleVolatileImage(grass.getWidth(null), grass
					.getHeight(null));
			g = tmp.getGraphics();
			g.drawImage(grass, 0, 0, null);
			g.dispose();
			if (USE_VOLATILE_IMAGES)
				grass = tmp;
			grass.setAccelerationPriority(1.0f);

			Image treasure = ImageIO.read(new File("TestImages/treasure.png"));
			treasure.setAccelerationPriority(1.0f);
			tmp = gc.createCompatibleVolatileImage(treasure.getWidth(null), treasure
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
						Vector location = new Vector(x, y);
						Tile tile = new Tile()
						{

							@Override
							public double getFriction()
							{
								return 0.1;
							}

						};
						Appearance water = new Appearance(tile);
						water.setImage(grass);
						tile.setAppearance(water);
						environment.add(tile, location);
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

			
			Appearance appearance = new Appearance(avatar);
			appearance.setImage(cheese);
			avatar.setAppearance(appearance);

			environment.add(avatar, new Vector(0, 0));

			int xRng = MAP_BOUNDS[1] - MAP_BOUNDS[0];
			int yRng = MAP_BOUNDS[3] - MAP_BOUNDS[2];
			int xMin = MAP_BOUNDS[0];
			int yMin = MAP_BOUNDS[2];

			for (int i = 0; i < N_NPCS; i++)
			{
				NPCEntity npc1 = new NPCEntity();
				Appearance npcAppearance1 = new Appearance(npc1);
				npcAppearance1.setImage(cheese);
				npc1.setAppearance(npcAppearance1);

				npc1.setTarget(avatar);

				environment.add(npc1, new Vector(random.nextInt(xRng) + xMin,
						random.nextInt(yRng) + yMin));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		world = new World();
		world.add(environment);
	}

	public CharEntity getAvatar() {
		return avatar;
	}

	public World getWorld() {
		return world;
	}
}
