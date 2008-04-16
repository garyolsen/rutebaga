package rutebaga.test.model.environment;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet;

public class InstanceSetTest
{
	public static void main(String ... args)
	{
		InstanceSet instanceSet = new InstanceSet();
		
		Entity e = new CharEntity();
		
		System.out.println(instanceSet.add(e));
		
		System.out.println(instanceSet.size());
		
		for(Instance instance : instanceSet)
		{
			System.out.println(instance.getClass());
		}
	}
}
