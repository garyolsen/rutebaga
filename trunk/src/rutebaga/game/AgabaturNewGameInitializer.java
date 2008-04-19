package rutebaga.game;

import java.awt.Image;
import java.util.Properties;
import java.util.Random;

import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.GameInitializer;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Hex2DTileConvertor;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.World;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.model.map.Tile;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.model.ability.CheeseArrowAbilityType;
import temporary.Bumper;
import temporary.WindTunnel;

public class AgabaturNewGameInitializer implements GameInitializer
{
	private Entity<?> avatar;
	private World world;
	private MasterScaffold scaffold;

	public AgabaturNewGameInitializer(MasterScaffold scaffold)
	{
		this.scaffold = scaffold;
	}

	public void build()
	{
		Environment environment = new Environment(new Hex2DTileConvertor());

		Properties config = (Properties) scaffold.get("confGame");

		int size = Integer.parseInt(config.getProperty("size"));
		int mapBounds[] =
		{ 0, size, 0, size };

		double tileProb = Double.parseDouble(config.getProperty("tileProb"));

		boolean showTreasure = Boolean.parseBoolean(config
				.getProperty("showTreasure"));

		int numNpcs = Integer.parseInt(config.getProperty("npcQty"));

		Image cheese = (Image) scaffold.get("imgCheese");
		Image grass = (Image) scaffold.get("imgHextile");
		Image treasure = (Image) scaffold.get("imgYoshiEgg01");

		Image[] tileImages = new Image[6];
		for(int i=1; i<=6; i++)
			tileImages[i-1] = (Image) scaffold.get("imgHextile0" + i);
		Appearance[] tileApps = new Appearance[12];
		for(int i=0; i<6; i++)
		{
			tileApps[i] = tileApps[12-i-1] = new Appearance(tileImages[i]);
			tileApps[i].setOrientation(Orientation.C);
		}

		Random random = new Random();

		for (int x = mapBounds[0]; x < mapBounds[1]; x++)
		{
			for (int y = mapBounds[2]; y < mapBounds[3]; y++)
			{
				if (random.nextDouble() < tileProb)
				{
					Vector2D location = new Vector2D(x, y);
					Tile tile = new Tile(null);
					Appearance water = new Appearance();
					water.setOrientation(Orientation.C);
					water.setImage(grass);
					tile
							.setAppearanceManager(new AnimatedAppearanceManager(
									tileApps, 2));
					environment.add(tile, location);
				}
			}
		}

		if (showTreasure)
		{
			for (int x = mapBounds[0] + 4; x < mapBounds[1] - 4; x++)
			{
				for (int y = mapBounds[2] + 4; y < mapBounds[3] / 2 - 4; y++)
				{
					Vector2D location = new Vector2D(x, y);
					if (x % 8 == 0 && y % 8 == 0)
					{
						WindTunnel tunnel = new WindTunnel();
						Appearance chest = new Appearance();
						chest.setImage(treasure);
						tunnel
								.setAppearanceManager(new StaticAppearanceManager(
										chest));
						environment.add(tunnel, location);
					}
				}
			}

			for (int x = mapBounds[0] + 4; x < mapBounds[1] - 4; x++)
			{
				for (int y = mapBounds[3] / 2 + 4; y < mapBounds[3] - 4; y++)
				{
					Vector2D location = new Vector2D(x, y);
					if (x % 8 == 0 && y % 8 == 0)
					{
						Instance instance = new Bumper();
						Appearance chest = new Appearance();
						chest.setImage(treasure);
						instance
								.setAppearanceManager(new StaticAppearanceManager(
										chest));
						environment.add(instance, location);
					}
				}
			}
		}

		avatar = ((EntityType<?>) scaffold.get("entityDefault")).makeInstance();
		
		avatar.addAbility(new CheeseArrowAbilityType().makeAbility());

		environment.add(avatar, new Vector2D(0, 0));

		int xRng = mapBounds[1] - mapBounds[0];
		int yRng = mapBounds[3] - mapBounds[2];
		int xMin = mapBounds[0];
		int yMin = mapBounds[2];

		for (int i = 0; i < numNpcs; i++)
		{
			NPCEntity<?> npc1 = new NPCEntity(null);
			npc1.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));
			Appearance npcAppearance1 = new Appearance();
			npcAppearance1.setImage(cheese);
			npc1.setAppearanceManager(new StaticAppearanceManager(
					npcAppearance1));

			npc1.setTarget(avatar);

			Vector2D location = new Vector2D(random.nextInt(xRng) + xMin,
					random.nextInt(yRng) + yMin);
			rutebaga.commons.Log.log("placing npc at " + location);
			environment.add(npc1, location);
		}

		world = new World();
		world.add(environment);

	}

	public Entity getAvatar()
	{
		return avatar;
	}

	public World getWorld()
	{
		return world;
	}

}
