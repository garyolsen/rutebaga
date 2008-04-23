package rutebaga.game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import rutebaga.appearance.AnimatedAppearanceDef;
import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.appearance.EntityAppearanceManager;
import rutebaga.appearance.MountAppearanceManager;
import rutebaga.appearance.StaticAppearanceDef;
import rutebaga.commons.math.BidirectionalValueProvider;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.ConvertorValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.GameInitializer;
import rutebaga.game.testing.Gary;
import rutebaga.game.testing.Nick;
import rutebaga.model.convertors.EntityStatsConvertor;
import rutebaga.model.effect.AreaEffectType;
import rutebaga.model.entity.AbilityCategory;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.Mount;
import rutebaga.model.entity.MountType;
import rutebaga.model.entity.Vehicle;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCType;
import rutebaga.model.entity.stats.StatValueProvider;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;
//import rutebaga.model.environment.ChainedMatrixConvertor;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Hex2DTileConvertor;
//import rutebaga.model.environment.MatrixTileConvertor;
import rutebaga.model.environment.MovementAttributeSet;
//import rutebaga.model.environment.PolarTileConvertor;
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.environment.World;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.item.ItemType;
import rutebaga.model.item.SlotType;
import rutebaga.model.map.River;
import rutebaga.model.map.RiverType;
import rutebaga.model.map.TerrainType;
import rutebaga.model.map.Tile;
import rutebaga.model.map.TileType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.MapBuilder;
import sun.security.krb5.Config;

public class CopyOfAgabaturNewGameInitializer implements GameInitializer
{
	private Entity<?> avatar1, avatar2, avatar3;
	private World world;
	private MasterScaffold scaffold;
	private Collection<StatisticId> displayedStats;

	private static Environment environment;
	private static double angle;

	public static Entity trackedEntity;

	public CopyOfAgabaturNewGameInitializer(MasterScaffold scaffold)
	{
		this.scaffold = scaffold;
	}

	public void build()
	{
		angle = 1;
//		MatrixTileConvertor rotate = new MatrixTileConvertor(Math.cos(angle),
//				-Math.sin(angle), Math.sin(angle), Math.cos(angle));
//		MatrixTileConvertor shear = new MatrixTileConvertor(1, -1, 0, 1);
//		double u = 1, v = 1;
//		MatrixTileConvertor orth = new MatrixTileConvertor(u * u, -u * v, -u
//				* v, v * v, 1 / (u * u + v * v));
//		MatrixTileConvertor parr = new MatrixTileConvertor(0, 0, 0.5, 1);
//		MatrixTileConvertor scale = new MatrixTileConvertor(1, 0, 0, 1);

		Properties config = (Properties) scaffold.get("confGame");

//		if (config.get("useHex").equals("true"))
//			environment = new Environment(new ChainedMatrixConvertor(
//					new Hex2DTileConvertor(), scale));
//		else
//			environment = new Environment(new ChainedMatrixConvertor(
//					new Rect2DTileConvertor(), scale));

//		if ("true".equals(config.get("crazy")))
//		{
//			new Thread()
//			{
//
//				@Override
//				public void run()
//				{
//					try
//					{
//						while (true)
//						{
//							Thread.sleep(100);
//							angle += 0.05;
//							double factor = (Math.sin(angle) + 2);
//							TileConverter scale = new ChainedMatrixConvertor(
//									new ChainedMatrixConvertor(
//											new Hex2DTileConvertor(),
//											new MatrixTileConvertor(factor, 0,
//													0, factor)),
//									new MatrixTileConvertor(Math.cos(angle),
//											-Math.sin(angle), Math.sin(angle),
//											Math.cos(angle)));
//							environment.setTileConvertor(scale);
//						}
//					}
//					catch (Exception e)
//					{
//
//					}
//				}
//
//			}.start();
//		}
//
//		new Thread()
//		{
//
//			@Override
//			public void run()
//			{
//				try
//				{
//					while (true)
//					{
//						Thread.sleep(10000);
//						System.out
//								.println(AgabaturNewGameInitializer.trackedEntity
//										.getMass());
//					}
//				}
//				catch (Exception e)
//				{
//
//				}
//			}
//
//		}.start();

		
		environment = (Environment) scaffold.get("mapRoom");

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
		AreaEffectType mover = (AreaEffectType) scaffold.get("aoeSpeeder");
		AreaEffectType damager = (AreaEffectType) scaffold.get("aoeTeleport");
		AreaEffectType harm = (AreaEffectType) scaffold.get("aoeHarm");

		AreaEffectType oneshotMP = (AreaEffectType) scaffold
				.get("aoeOneShotMana");
		AreaEffectType oneshotHP = (AreaEffectType) scaffold
				.get("aoeOneShotHealth");

		ItemType<?> sword = (ItemType<?>) scaffold.get("itemSword");
		ItemType<?> mushroom = (ItemType<?>) scaffold.get("itemMushroom");
		ItemType<?> coin = (ItemType<?>) scaffold.get("itemStarman");
		ItemType<?> fireflower = (ItemType<?>) scaffold.get("itemFireFlower");
		ItemType<?> bananapeel = (ItemType<?>) scaffold.get("itemBananaPeel");
		ItemType<?> starman = (ItemType<?>) scaffold.get("itemStarman");

		StatisticId hp = (StatisticId) scaffold.get("statHp");
		StatisticId mana = (StatisticId) scaffold.get("statMp");
		StatisticId exp = (StatisticId) scaffold.get("statExperience");

		displayedStats = new ArrayList<StatisticId>();
		displayedStats.add(hp);
		displayedStats.add(mana);
		displayedStats.add(exp);

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
				if ((x == 0 && y == 0) || random.nextDouble() < grassTileProb)
				{
					tile = grass.makeInstance();

					if (random.nextInt(15) == 0)
					{
						switch (random.nextInt(5))
						{
						case 0:
							environment.add(healer.makeInstance(), location);
							break;
						case 1:
							environment.add(damager.makeInstance(), location);
							break;
						case 2:
							environment.add(oneshotHP.makeInstance(), location);
							break;
						case 3:
							environment.add(oneshotMP.makeInstance(), location);
							break;
						case 4:
							environment.add(harm.makeInstance(), location);
							break;
						}
					}
					else if (random.nextInt(15) == 0)
					{
						switch (random.nextInt(6))
						{
						case 0:
							environment.add(sword.makeInstance(), location);
							break;
						case 1:
							environment.add(starman.makeInstance(), location);
							break;
						case 2:
							environment.add(mushroom.makeInstance(), location);
							break;
						case 3:
							environment.add(coin.makeInstance(), location);
							break;
						case 4:
							environment
									.add(fireflower.makeInstance(), location);
							break;
						case 5:
							environment
									.add(bananapeel.makeInstance(), location);
							break;
						}
					}

				}
				else if (random.nextDouble() < waterTileProb)
				{
					tile = water.makeInstance();
				}
				else
				{
					tile = mtn.makeInstance();
				}
//				environment.add(tile, location);
			}
		}

		RiverType river = (RiverType) scaffold.get("rivDefaultRiver");
//		river.make().addToEnvironment(environment);

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

		for (String key : scaffold.getKeys())
		{
			Object obj = scaffold.get(key);
			if (obj == null)
				continue;
		}

		avatar1 = ((EntityType<?>) scaffold.get("entityMario")).makeInstance();
		avatar2 = ((EntityType<?>) scaffold.get("entityWetMario"))
				.makeInstance();
		avatar3 = ((EntityType<?>) scaffold.get("entityMario")).makeInstance();
		EntityAppearanceManager wetManager1 = new EntityAppearanceManager(
				avatar1);
		EntityAppearanceManager wetManager2 = new EntityAppearanceManager(
				avatar2);
		EntityAppearanceManager wetManager3 = new EntityAppearanceManager(
				avatar3);

		SneakAbility ability = new SneakAbility(movement, avatar1);
		ability.setMovement(movement);
		ability.setCategory((AbilityCategory) scaffold.get("abcatAttacks"));
		ability.setName("Sneak");
		avatar1.addAbility(ability);
		/*
		 * wetManager.setStanding(((EntityType<?>)
		 * scaffold.get("entityWetMario")) .getStanding());
		 * wetManager.setWalking(((EntityType<?>)
		 * scaffold.get("entityWetMario")) .getWalking());
		 * avatar.setAppearanceManager(wetManager);
		 */

		SlotType hand = (SlotType) scaffold.get("slotHand");

		avatar1.getInventory().addSlotAllocation(hand, 4);
		avatar2.getInventory().addSlotAllocation(hand, 4);
		avatar3.getInventory().addSlotAllocation(hand, 4);

		while (!avatar1.existsInUniverse())
		{
			environment.add(avatar1, new Vector2D(0, 0));
		}

//		while (!avatar2.existsInUniverse())
//		{
//			environment.add(avatar2, new Vector2D(random.nextInt(mapBounds[1]
//					- mapBounds[0])
//					+ mapBounds[0], random.nextInt(mapBounds[3] - mapBounds[2])
//					+ mapBounds[2]));
//		}
//
//		while (!avatar3.existsInUniverse())
//		{
//			environment.add(avatar3, new Vector2D(random.nextInt(mapBounds[1]
//					- mapBounds[0])
//					+ mapBounds[0], random.nextInt(mapBounds[3] - mapBounds[2])
//					+ mapBounds[2]));
//		}

		MountType mountType = new MountType();
		mountType.setMass(1.0);
		mountType.setVehicle(new Vehicle());
		mountType.setRadius(7);

		Mount mount = mountType.create();
		mountType.initialize(mount);
		mount.blackListTerrainTypes();
		mount.setMass(1.0);
		mount.setVehicle(new Vehicle());
		MountAppearanceManager mountManager = new MountAppearanceManager(mount);
		mountManager.setMountedStanding(getAnimatedAppearances(
				"imgMarioMounted", scaffold));
		mountManager.setMountedWalking(getAnimatedAppearances(
				"imgMarioMounted", scaffold));
		mountManager.setStanding(getAnimatedAppearances("imgYoshi", scaffold));
		mountManager.setWalking(getAnimatedAppearances("imgYoshi", scaffold));
		mount.setAppearanceManager(mountManager);

		int xRng = mapBounds[1] - mapBounds[0];
		int yRng = mapBounds[3] - mapBounds[2];
		int xMin = mapBounds[0];
		int yMin = mapBounds[2];

		while (!mount.existsInUniverse())
		{
			environment.add(mount, new Vector2D(random.nextInt(mapBounds[1]
					- mapBounds[0])
					+ mapBounds[0], random.nextInt(mapBounds[3] - mapBounds[2])
					+ mapBounds[2]));
		}

		// for (int i = 0; i < numNpcs; i++)
		// {
		// NPCEntity<?> npc1 = new NPCEntity(null);
		// npc1.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));
		//
		// EntityAppearanceManager manager = new EntityAppearanceManager(npc1);
		// manager.setStanding(((EntityType<?>) scaffold.get("entityDefault"))
		// .getStanding());
		// manager.setWalking(((EntityType<?>) scaffold.get("entityDefault"))
		// .getWalking());
		// npc1.setAppearanceManager(manager);
		//
		// npc1.whiteListTerrainTypes(grassTerrain, waterTerrain);
		// npc1.setTarget(avatar);
		//
		// Vector2D location = new Vector2D(random.nextInt(xRng) + xMin,
		// random.nextInt(yRng) + yMin);
		// rutebaga.commons.Log.log("placing npc at " + location);
		// environment.add(npc1, location);
		// }

		for (int i = 0; i < numNpcs; i++)
		{
			NPCEntity<?> npc1 = ((NPCType<?>) scaffold.get("npcBlueNPC"))
					.makeInstance();

			npc1.setWallet(new BidirectionalValueProvider<Entity>()
			{

				@Override
				public double addTo(Entity t, double value)
				{
					return Double.POSITIVE_INFINITY;
				}

				@Override
				public double getValue(Entity t)
				{
					return Double.POSITIVE_INFINITY;
				}

			});
			
//			npc1
//					.setMovementSpeedStrat(new ConvertorValueProvider<Entity, Stats>(
//							new StatValueProvider((StatisticId) scaffold
//									.get("statMovement")),
//							new EntityStatsConvertor()));
			
			npc1.setMovementSpeedStrat((ValueProvider<Entity>) scaffold.get("vpEntityMovementSpeed"));
			EntityAppearanceManager manager = new EntityAppearanceManager(npc1);

			switch (random.nextInt(3))
			{
			case 0:
				manager.setStanding(((EntityType<?>) scaffold
						.get("entityNPCGoomba")).getStanding());
				manager.setWalking(((EntityType<?>) scaffold
						.get("entityNPCGoomba")).getWalking());
				break;
			case 1:
				manager.setStanding(((EntityType<?>) scaffold
						.get("entityNPCKoopa")).getStanding());
				manager.setWalking(((EntityType<?>) scaffold
						.get("entityNPCKoopa")).getWalking());
				break;
			case 2:
				manager.setStanding(((EntityType<?>) scaffold
						.get("entityNPCShyguy")).getStanding());
				manager.setWalking(((EntityType<?>) scaffold
						.get("entityNPCShyguy")).getWalking());
				break;
			}
			npc1.setAppearanceManager(manager);

			npc1.whiteListTerrainTypes(grassTerrain, waterTerrain);
			npc1.setTarget(avatar1);

			Vector2D location = new Vector2D(random.nextInt(xRng) + xMin,
					random.nextInt(yRng) + yMin);
			environment.add(npc1, location);
		}

		world = new World();
		world.add(environment);

		// Gary.run(environment, scaffold, avatar1);

		// Nick.run(environment, scaffold, avatar2);

		// Make sure avatar's stats are all initialized
		Collection<?> stats = (Collection<?>) scaffold
				.get("globalAllStatsList");
		for (Object stat : stats)
		{
			avatar1.getStats().getStatObject((StatisticId) stat);
			avatar2.getStats().getStatObject((StatisticId) stat);
			avatar3.getStats().getStatObject((StatisticId) stat);
		}

	}

	public Entity[] getAvatars()
	{
		return new Entity[]
		{ avatar1, avatar2, avatar3 };
	}

	public World getWorld()
	{
		return world;
	}

	public Collection<StatisticId> getDisplayedStats()
	{
		return displayedStats;
	}

	private static String[] directionStrings =
	{ "North", "NorthEast", "SouthEast", "South", "SouthWest", "NorthWest" };

	private Appearance[][] getAnimatedAppearances(String name,
			MasterScaffold scaffold)
	{
		List<Appearance[]> list = new ArrayList<Appearance[]>();
		for (String dirString : directionStrings)
		{
			list.add(getAppearancesForDirection(name, dirString, scaffold));
		}
		return list.toArray(new Appearance[list.size()][]);
	}

	private Appearance[] getAppearancesForDirection(String name,
			String direction, MasterScaffold scaffold)
	{
		boolean stop = false;
		ArrayList<Appearance> list = new ArrayList<Appearance>();
		for (int i = 1; !stop; i++)
		{
			String nStr = String.valueOf(i);
			if (nStr.length() == 1)
				nStr = "0" + nStr;
			String scaffId = name + direction + nStr;
			if (scaffold.contains(scaffId))
			{
				list.add(new Appearance((Image) scaffold.get(scaffId)));
			}
			else
				stop = true;
		}
		return list.toArray(new Appearance[list.size()]);
	}

}
