package rutebaga.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import rutebaga.appearance.EntityAppearanceManager;
import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.GameInitializer;
import rutebaga.model.effect.AreaEffectType;
import rutebaga.model.entity.AbilityCategory;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.MountType;
import rutebaga.model.entity.Vehicle;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.World;
import rutebaga.model.item.ItemType;
import rutebaga.model.item.SlotType;
import rutebaga.model.map.TerrainType;
import rutebaga.scaffold.MasterScaffold;
import sun.security.jca.GetInstance.Instance;

public class AgabaturNewGameInitializer implements GameInitializer
{
	private Entity<?> avatar1;
	private World world;
	private MasterScaffold scaffold;
	private Collection<StatisticId> displayedStats;

	private static Environment environment;

	public static Entity trackedEntity;

	public AgabaturNewGameInitializer(MasterScaffold scaffold)
	{
		this.scaffold = scaffold;
	}

	public void build()
	{
		Random rand = new Random();

		environment = (Environment) scaffold.get("mapRoom");

		StatisticId hp = (StatisticId) scaffold.get("statHp");
		StatisticId mana = (StatisticId) scaffold.get("statMp");
		StatisticId exp = (StatisticId) scaffold.get("statExperience");
		StatisticId level = (StatisticId) scaffold.get("statLevel");

		displayedStats = new ArrayList<StatisticId>();
		displayedStats.add(hp);
		displayedStats.add(mana);
		displayedStats.add(exp);
		displayedStats.add(level);

		StatisticId movement = (StatisticId) scaffold.get("statMovement");

		avatar1 = ((EntityType<?>) scaffold.get("entityMario")).makeInstance();

		SneakAbility ability = new SneakAbility(movement, avatar1);
		ability.setMovement(movement);
		ability.setCategory((AbilityCategory) scaffold.get("abcatAttacks"));
		ability.setName("Sneak");
		avatar1.addAbility(ability);

		SlotType hand = (SlotType) scaffold.get("slotHand");

		avatar1.getInventory().addSlotAllocation(hand, 4);

		environment.add(avatar1, new Vector2D(3, 3));

		for (int i = 0; i < 20; i++)
		{
			rutebaga.model.environment.Instance instance = null;
			
			if(rand.nextBoolean()) {
				instance = ((InstanceType) scaffold.get("npcBlueNPC")).makeInstance();
			} else {
				instance = ((InstanceType) scaffold.get("npcBlueNPC2")).makeInstance();
			}
			Vector2D location = new Vector2D(i, 14);
			environment.add(instance, location);
		}

		ItemType key = (ItemType) scaffold.get("itemKey");
		environment.add(key.makeInstance(), new Vector2D(10, 10));

		AreaEffectType healer = (AreaEffectType) scaffold.get("aoeHealer");
		environment.add(healer.makeInstance(), new Vector2D(3, 10));

		AreaEffectType teleport = (AreaEffectType) scaffold.get("aoeTeleport");
		environment.add(teleport.makeInstance(), new Vector2D(4, 10));
		
		world = new World();
		world.add(environment);

		Collection<?> stats = (Collection<?>) scaffold
				.get("globalAllStatsList");
		for (Object stat : stats)
		{
			avatar1.getStats().getStatObject((StatisticId) stat);
		}

	}

	public Entity[] getAvatars()
	{
		return new Entity[]
		{ avatar1 };
	}

	public World getWorld()
	{
		return world;
	}

	public Collection<StatisticId> getDisplayedStats()
	{
		return displayedStats;
	}

}
