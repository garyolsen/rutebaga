package rutebaga.game;

import java.awt.Image;
import java.util.Properties;
import java.util.Random;

import rutebaga.appearance.EntityAppearanceManager;
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
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.environment.World;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.model.map.GrassTerrain;
import rutebaga.model.map.MountainTerrain;
import rutebaga.model.map.Tile;
import rutebaga.model.map.WaterTerrain;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.model.ability.CheeseArrowAbilityType;

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

		double grassTileProb = Double.parseDouble(config.getProperty("grassTileProb"));
		double waterTileProb = Double.parseDouble(config.getProperty("waterTileProb"));

		boolean showTreasure = Boolean.parseBoolean(config
				.getProperty("showTreasure"));

		int numNpcs = Integer.parseInt(config.getProperty("npcQty"));

		//Image cheese = (Image) scaffold.get("imgCheese");
		Image grass = (Image) scaffold.get("imgHextile");
		Image water = (Image) scaffold.get("imgWaterTile01");
		Image mountain = (Image) scaffold.get("imgMountainTile01");
		Image treasure = (Image) scaffold.get("imgYoshiEgg01");

		Image[] grassTileImages = new Image[6];
		Image[] waterTileImages = new Image[6];
		Image[] mountainTileImages = new Image[6];
		for (int i = 1; i <= 6; i++)
		{
			grassTileImages[i - 1] = (Image) scaffold.get("imgHextile0" + i);
			waterTileImages[i - 1] = (Image) scaffold.get("imgWaterTile0" + i);
			mountainTileImages[i - 1] = (Image) scaffold.get("imgMountainTile0" + i);
		}
		Appearance[] grassTileApps = new Appearance[12];
		Appearance[] waterTileApps = new Appearance[12];
		Appearance[] mountainTileApps = new Appearance[12];
		for (int i = 0; i < 6; i++)
		{
			grassTileApps[i] = grassTileApps[12 - i - 1] = new Appearance(grassTileImages[i]);
			waterTileApps[i] = waterTileApps[12 - i - 1] = new Appearance(waterTileImages[i]);
			mountainTileApps[i] = mountainTileApps[12 - i - 1] = new Appearance(mountainTileImages[i]);
			grassTileApps[i].setOrientation(Orientation.C);
			waterTileApps[i].setOrientation(Orientation.C);
			mountainTileApps[i].setOrientation(Orientation.C);
		}

		Random random = new Random();

		for (int x = mapBounds[0]; x < mapBounds[1]; x++)
		{
			for (int y = mapBounds[2]; y < mapBounds[3]; y++)
			{
				if (random.nextDouble() < grassTileProb)
				{
					Vector2D location = new Vector2D(x, y);
					Tile tile = new Tile(null);
					tile.setTerrainType(new GrassTerrain());
					Appearance grassAppearance = new Appearance();
					grassAppearance.setOrientation(Orientation.C);
					grassAppearance.setImage(grass);
					tile.setAppearanceManager(new AnimatedAppearanceManager(
							grassTileApps, 2));
					environment.add(tile, location);
				} else if (random.nextDouble() < waterTileProb)
				{
					Vector2D location = new Vector2D(x, y);
					Tile tile = new Tile(null);
					tile.setTerrainType(new WaterTerrain());
					Appearance waterAppearance = new Appearance();
					waterAppearance.setOrientation(Orientation.C);
					waterAppearance.setImage(water);
					tile.setAppearanceManager(new AnimatedAppearanceManager(
							waterTileApps, 2));
					environment.add(tile, location);
				} else
				{
					Vector2D location = new Vector2D(x, y);
					Tile tile = new Tile(null);
					tile.setTerrainType(new MountainTerrain());
					Appearance mountainAppearance = new Appearance();
					mountainAppearance.setOrientation(Orientation.C);
					mountainAppearance.setImage(mountain);
					tile.setAppearanceManager(new AnimatedAppearanceManager(
							mountainTileApps, 2));
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
//					Vector2D location = new Vector2D(x, y);
//					if (x % 8 == 0 && y % 8 == 0)
//					{
//						WindTunnel tunnel = new WindTunnel();
//						Appearance chest = new Appearance();
//						chest.setImage(treasure);
//						tunnel
//								.setAppearanceManager(new StaticAppearanceManager(
//										chest));
//						environment.add(tunnel, location);
//					}
				}
			}

			for (int x = mapBounds[0] + 4; x < mapBounds[1] - 4; x++)
			{
				for (int y = mapBounds[3] / 2 + 4; y < mapBounds[3] - 4; y++)
				{
					Vector2D location = new Vector2D(x, y);
					if (x % 8 == 0 && y % 8 == 0)
					{
//						Instance instance = new Bumper();
//						Appearance chest = new Appearance();
//						chest.setImage(treasure);
//						instance
//								.setAppearanceManager(new StaticAppearanceManager(
//										chest));
//						environment.add(instance, location);
					}
				}
			}
		}

		avatar = ((EntityType<?>) scaffold.get("entityDefault")).makeInstance();
		Object[] arr = ((EntityAppearanceManager) avatar.getAppearanceManager())
				.getStanding();
		System.out.println(arr + ":"
				+ ((arr == null) ? "" : (arr.length + ":" + arr[0])));

		avatar.addAbility(new CheeseArrowAbilityType().makeAbility());
		avatar.whiteListTerrainTypes(new GrassTerrain());

		environment.add(avatar, new Vector2D(0, 0));

		int xRng = mapBounds[1] - mapBounds[0];
		int yRng = mapBounds[3] - mapBounds[2];
		int xMin = mapBounds[0];
		int yMin = mapBounds[2];

		for (int i = 0; i < numNpcs; i++)
		{
			NPCEntity<?> npc1 = new NPCEntity(null);
			npc1.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));
			
			EntityAppearanceManager manager = new EntityAppearanceManager(npc1);
			manager.setStanding(((EntityType<?>) scaffold.get("entityDefault")).getStanding());
			manager.setWalking(((EntityType<?>) scaffold.get("entityDefault")).getWalking());
			npc1.setAppearanceManager(manager);

			npc1.whiteListTerrainTypes(new GrassTerrain(), new WaterTerrain());
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
