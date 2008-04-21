package rutebaga.game;

import java.awt.Image;
import java.util.Properties;
import java.util.Random;

import rutebaga.appearance.AnimatedAppearanceDef;
import rutebaga.appearance.EntityAppearanceManager;
import rutebaga.appearance.StaticAppearanceDef;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.GameInitializer;
import rutebaga.model.effect.AreaEffectType;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCType;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.ChainedMatrixConvertor;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Hex2DTileConvertor;
import rutebaga.model.environment.MatrixTileConvertor;
import rutebaga.model.environment.MovementAttributeSet;
import rutebaga.model.environment.PolarTileConvertor;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.environment.World;
import rutebaga.model.item.ItemType;
import rutebaga.model.item.SlotType;
import rutebaga.model.map.River;
import rutebaga.model.map.TerrainType;
import rutebaga.model.map.Tile;
import rutebaga.model.map.TileType;
import rutebaga.scaffold.MasterScaffold;

public class AgabaturNewGameInitializer implements GameInitializer
{
	private Entity<?> avatar;
	private World world;
	private MasterScaffold scaffold;

	private static Environment environment;
	private static double angle;

	public AgabaturNewGameInitializer(MasterScaffold scaffold)
	{
		this.scaffold = scaffold;
	}

	public void build()
	{
		angle = 1;
		MatrixTileConvertor rotate = new MatrixTileConvertor(Math.cos(angle),
				-Math.sin(angle), Math.sin(angle), Math.cos(angle));
		MatrixTileConvertor shear = new MatrixTileConvertor(1, -1, 0, 1);
		double u = 1, v = 1;
		MatrixTileConvertor orth = new MatrixTileConvertor(u * u, -u * v, -u
				* v, v * v, 1 / (u * u + v * v));
		MatrixTileConvertor parr = new MatrixTileConvertor(0, 0, 0.5, 1);
		MatrixTileConvertor scale = new MatrixTileConvertor(1, 0, 0, 1);
		environment = new Environment(new ChainedMatrixConvertor(
				new Hex2DTileConvertor(), scale));
		
		Properties config = (Properties) scaffold.get("confGame");

		if ("true".equals(config.get("fuckingCrazy")))
		{
			new Thread()
			{

				@Override
				public void run()
				{
					try
					{
						while (true)
						{
							Thread.sleep(100);
							angle += 0.05;
							double factor = (Math.sin(angle) + 2);
							TileConverter scale = new ChainedMatrixConvertor(
									new ChainedMatrixConvertor(
											new Hex2DTileConvertor(),
											new MatrixTileConvertor(factor, 0,
													0, factor)),
									new MatrixTileConvertor(Math.cos(angle),
											-Math.sin(angle), Math.sin(angle),
											Math.cos(angle)));
							environment.setTileConvertor(scale);
						}
					}
					catch (Exception e)
					{

					}
				}

			}.start();
		}

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
		AreaEffectType damager = (AreaEffectType) scaffold.get("aoeHarm");
		
		ItemType<?> sword = (ItemType<?>) scaffold.get("itemSword");

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
				if ((x == 0 && y == 0) || random.nextDouble() < grassTileProb)
				{
					tile = grass.makeInstance();

				
					if (random.nextInt(15) == 0){
						switch(random.nextInt(3)){
							case 0:
								environment.add(healer.makeInstance(), location);
							break;
							case 1:	
								environment.add(mover.makeInstance(), location);
							break;
							case 2:
								environment.add(damager.makeInstance(), location);
							break;
						}
					}
						
					if(random.nextDouble() < 0.1) {
						environment.add(sword.makeInstance(), location);
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
				environment.add(tile, location);
			}
		}

		for (int i = 0; i < 10; i++)
		{
			River river = new River();
			river.setAppearanceManager(new StaticAppearanceDef((Image) scaffold
					.get("imgLameEffect")));
			river.setLocation(new Vector2D(0, i));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addNodeAtTail(0.1, new Vector2D(1, 0));
			river.addToEnvironment(environment);
		}

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
		

		SlotType hand = (SlotType) scaffold.get("slotHand");
		
		avatar.getInventory().addSlotAllocation(hand, 4);

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

//		for (int i = 0; i < numNpcs; i++)
//		{
//			NPCEntity<?> npc1 = new NPCEntity(null);
//			npc1.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));
//
//			EntityAppearanceManager manager = new EntityAppearanceManager(npc1);
//			manager.setStanding(((EntityType<?>) scaffold.get("entityDefault"))
//					.getStanding());
//			manager.setWalking(((EntityType<?>) scaffold.get("entityDefault"))
//					.getWalking());
//			npc1.setAppearanceManager(manager);
//
//			npc1.whiteListTerrainTypes(grassTerrain, waterTerrain);
//			npc1.setTarget(avatar);
//
//			Vector2D location = new Vector2D(random.nextInt(xRng) + xMin,
//					random.nextInt(yRng) + yMin);
//			rutebaga.commons.Log.log("placing npc at " + location);
//			environment.add(npc1, location);
//		}

		for (int i = 0; i < numNpcs; i++)
		{
			NPCEntity<?> npc1 = ((NPCType<?>) scaffold.get("npcBlueNPC")).makeInstance();

			npc1.setMovementSpeedStrat(new ConstantValueProvider<Entity>(.09));

			EntityAppearanceManager manager = new EntityAppearanceManager(npc1);

			switch(random.nextInt(3)){
				case 0:
				manager.setStanding(((EntityType<?>) scaffold.get("entityNPCGoomba"))
							.getStanding());
					manager.setWalking(((EntityType<?>) scaffold.get("entityNPCGoomba"))
							.getWalking());
				break;
				case 1:
					manager.setStanding(((EntityType<?>) scaffold.get("entityNPCKoopa"))
								.getStanding());
						manager.setWalking(((EntityType<?>) scaffold.get("entityNPCKoopa"))
								.getWalking());
					break;
				case 2:
					manager.setStanding(((EntityType<?>) scaffold.get("entityNPCShyguy"))
								.getStanding());
						manager.setWalking(((EntityType<?>) scaffold.get("entityNPCShyguy"))
								.getWalking());
					break;
			}
			npc1.setAppearanceManager(manager);

			npc1.whiteListTerrainTypes(grassTerrain, waterTerrain);
			npc1.setTarget(avatar);

			Vector2D location = new Vector2D(random.nextInt(xRng) + xMin,
					random.nextInt(yRng) + yMin);
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
