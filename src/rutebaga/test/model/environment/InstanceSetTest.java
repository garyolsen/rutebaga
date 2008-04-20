package rutebaga.test.model.environment;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.environment.InstanceFilter;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.item.Item;
import rutebaga.model.map.Tile;

public class InstanceSetTest
{
	/*
	public static void main(String ... args)
	{
		ConcreteInstanceSet instanceSet = new ConcreteInstanceSet();
		
		instanceSet.add(new CharEntity());
		instanceSet.add(new CharEntity());
		instanceSet.add(new CharEntity());
		instanceSet.add(new CharEntity());
		
		instanceSet.add(new Tile());
		instanceSet.add(new Tile());
		instanceSet.add(new Tile());
		
		instanceSet.add(new Item(null){

			@Override
			public boolean blocks(Instance other)
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public double getFriction()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getLayer()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getMass()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void tick()
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		

		instanceSet.add(new Item(null){

			@Override
			public boolean blocks(Instance other)
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public double getFriction()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getLayer()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getMass()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void tick()
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		
		rutebaga.commons.Log.log(instanceSet.size());
		
		for(Instance instance : instanceSet)
		{
			rutebaga.commons.Log.log(instance.getClass());
		}
		
		rutebaga.commons.Log.log();
		
		InstanceFilter filter =  new InstanceFilter(InstanceSetIdentifier.ENTITY, InstanceSetIdentifier.TILE);
		
		InstanceSet filtered = filter.filter(instanceSet);
		
		rutebaga.commons.Log.log(filtered.size());
		
		for(Instance instance : filtered)
		{
			rutebaga.commons.Log.log(instance.getClass());
		}
		
		rutebaga.commons.Log.log();
		
		for(Instance instance : instanceSet)
		{
			rutebaga.commons.Log.log(instance.getClass());
		}
	}
	*/
}
