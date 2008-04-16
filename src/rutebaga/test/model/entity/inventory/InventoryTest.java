package rutebaga.test.model.entity.inventory;

import java.util.LinkedList;
import java.util.List;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.effect.StatEffect;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.item.Item;

public class InventoryTest
{
	public static final StatisticId strengthId = new StatisticId("Strength");

	public static void main(String... args)
	{
		Entity entity = new CharEntity();
		Item sword = new Item(null)
		{

			@Override
			public List<EntityEffect> getPermanentEquipEffects()
			{
				return new LinkedList<EntityEffect>();
			}

			@Override
			public List<ReversibleEntityEffect> getReversibleEquipEffects()
			{
				LinkedList<ReversibleEntityEffect> rval = new LinkedList<ReversibleEntityEffect>();
				rval.add(new StatEffect(strengthId, 2));
				return rval;
			}

			@Override
			public boolean blocks(Instance other)
			{
				return false;
			}

			@Override
			public double getFriction()
			{
				return 0;
			}

			@Override
			public double getMass()
			{
				// TODO Auto-generated method stub
				return 0.1;
			}

			@Override
			public void tick()
			{

			}

			@Override
			public double getLayer()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			protected InstanceSetIdentifier getSetIdentifier()
			{
				return InstanceSetIdentifier.ITEM;
			}

		};

		System.out.println("before: " + entity.getStats().getValue(strengthId));

		entity.getInventory().equip(sword);

		System.out.println("after equip, before tick: "
				+ entity.getStats().getValue(strengthId));

		entity.tick();

		System.out.println("after tick: "
				+ entity.getStats().getValue(strengthId));

		entity.getInventory().unequip(sword);

		System.out.println("after unequip, before tick: "
				+ entity.getStats().getValue(strengthId));

		entity.tick();

		System.out.println("after tick: "
				+ entity.getStats().getValue(strengthId));
	}
}
