package rutebaga.game;

import java.awt.Image;
import java.util.Properties;
import java.util.Random;

import rutebaga.appearance.EntityAppearanceManager;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.GameInitializer;
import rutebaga.model.effect.AreaEffectType;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.effect.StatEffect;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.BlackList;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Hex2DTileConvertor;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.MatrixTileConvertor;
import rutebaga.model.environment.MovementAttributes;
import rutebaga.model.environment.PolarTileConvertor;
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.environment.World;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.model.map.River;
import rutebaga.model.item.SlotType;
import rutebaga.model.map.TerrainType;
import rutebaga.model.map.Tile;
import rutebaga.model.map.TileType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.model.ability.CheeseArrowAbilityType;
import rutebaga.test.model.examples.EntityAreaEffect;

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
		double theta = Math.PI / 4;
		TileConverter rotate = new MatrixTileConvertor(Math.cos(theta), -Math
				.sin(theta), Math.sin(theta), Math.cos(theta));
		TileConverter shear = new MatrixTileConvertor(1, -1, 0, 1);
		double u = 1, v = 1;
		TileConverter orth = new MatrixTileConvertor(u*u, -u*v, -u*v, v*v, 1/(u*u+v*v));
		TileConverter parr = new MatrixTileConvertor(0, 0, 0.5, 1);
		Environment environment = new Environment(new Hex2DTileConvertor());

		Properties config = (Properties) scaffold.get("confGame");

		int size = Integer.parseInt(config.getProperty("size"));
		int mapBounds[] =
		{ 0, size, 0, size };

		double grassTileProb = Double.parseDouble(config
				.getProperty("grassTileProb"));
		double waterTileProb = Double.parseDouble(config
				.getProperty("waterTileProb"));

		boolean showTreasure = Boolean.parseBoolean(config
				.getProperty("showTreasure"));

		int numNpcs = Integer.parseInt(config.getProperty("npcQty"));

		AreaEffectType healer = (AreaEffectType) scaffold.get("aoeHealer");
		AreaEffectType harmer = (AreaEffectType) scaffold.get("aoeHarm");

		StatisticId hp = (StatisticId) scaffold.get("statHp");
		StatisticId movement = (StatisticId) scaffold.get("statMovement");

		TerrainType grassTerrain = (TerrainType) scaffold.get("terrGrass");
		TerrainType mtnTerrain = (TerrainType) scaffold.get("terrMountain");
		TerrainType waterTerrain = (TerrainType) scaffold.get("terrWater");

		TileType<?> grass = (TileType) scaffold.get("tileGrass");
		TileType<?> water = (TileType) scaffold.get("tileWater");
		TileType<?> mtn = (TileType) scaffold.get("tileMountain");

		Random random = new Random();

		for (int x = mapBounds[0]; x < mapBounds[1]; x++)
		{
			for (int y = mapBounds[2]; y < mapBounds[3]; y++)
			{
				Vector2D location = new Vector2D(x, y);
				Tile tile = null;
				if (random.nextDouble() < grassTileProb)
				if ((x == 0 && y == 0) || random.nextDouble() < grassTileProb)
				{
					tile = grass.makeInstance();
					if (random.nextDouble() < 0.1)
						environment.add(healer.makeInstance(), location);
					else if (random.nextDouble() < 0.1)
						environment.add(harmer.makeInstance(), location);
				}
				else if (random.nextDouble() < waterTileProb)
				{
					tile = water.makeInstance();
				}
				else
				{
					tile = mtn.makeInstance();
				}
				environment.add(tile, location);
			}
		}

		// Rivers are broken!
		 River river = new River();
		 river.setLocation(new Vector2D(3,3));
		 river.addNodeAtTail(1.0, new Vector2D(-1,-1));
		 river.addNodeAtTail(1.0, new Vector2D(-1,-1));
		 river.addNodeAtTail(1.0, new Vector2D(-1,-1));
		 river.addNodeAtTail(1.0, new Vector2D(-1,-1));
		 river.addToEnvironment(environment);

		if (true)
		{
			for (int x = mapBounds[0] + 4; x < mapBounds[1] - 4; x++)
			{
				for (int y = mapBounds[2] + 4; y < mapBounds[3] / 2 - 4; y++)
				{
					// Vector2D location = new Vector2D(x, y);
					// if (x % 8 == 0 && y % 8 == 0)
					// {
					// WindTunnel tunnel = new WindTunnel();
					// Appearance chest = new Appearance();
					// chest.setImage(treasure);
					// tunnel
					// .setAppearanceManager(new StaticAppearanceManager(
					// chest));
					// environment.add(tunnel, location);
					// }
				}
			}

			for (int x = mapBounds[0] + 4; x < mapBounds[1] - 4; x++)
			{
				for (int y = mapBounds[3] / 2 + 4; y < mapBounds[3] - 4; y++)
				{
					Vector2D location = new Vector2D(x, y);
					if (x % 8 == 0 && y % 8 == 0)
					{
						// Instance instance = new Bumper();
						// Appearance chest = new Appearance();
						// chest.setImage(treasure);
						// instance
						// .setAppearanceManager(new StaticAppearanceManager(
						// chest));
						// environment.add(instance, location);
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
		avatar.whiteListTerrainTypes(grassTerrain);

		while (!avatar.existsInUniverse())
		{
			environment.add(avatar, new Vector2D(random.nextInt(mapBounds[1]
					- mapBounds[0])
					+ mapBounds[0], random.nextInt(mapBounds[3] - mapBounds[2])
					+ mapBounds[2]));
		}

		int xRng = mapBounds[1] - mapBounds[0];
		int yRng = mapBounds[3] - mapBounds[2];
		int xMin = mapBounds[0];
		int yMin = mapBounds[2];

		for (int i = 0; i < numNpcs; i++)
		{
			NPCEntity<?> npc1 = new NPCEntity(null);
			npc1.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));

			EntityAppearanceManager manager = new EntityAppearanceManager(npc1);
			manager.setStanding(((EntityType<?>) scaffold.get("entityDefault"))
					.getStanding());
			manager.setWalking(((EntityType<?>) scaffold.get("entityDefault"))
					.getWalking());
			npc1.setAppearanceManager(manager);

			npc1.whiteListTerrainTypes(grassTerrain, waterTerrain);
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
