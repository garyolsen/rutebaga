package rutebaga.test.model.environment;

import rutebaga.commons.Vector;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.RectTileConvertor;

public class MovementTest
{
	public static void main(String ... args)
	{
		TestInstance instance = new TestInstance("Alice");
		Environment environment = new Environment(new RectTileConvertor());
		boolean success = environment.add(instance, new Vector(2.4, 2.4));
		
		if(!success) System.out.println("Failed");
		
		instance = new TestInstance("Alice");
		success = environment.add(instance, new Vector(0, 0));
		
		if(!success) System.out.println("Failed");
		
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		System.out.println("applying impulse");
		instance.applyImpulse(new Vector(0.1, 0.1));
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		System.out.println("applying momentum");
		instance.applyMomentum(new Vector(0.4, 0.4));
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
		environment.tick();
		printEnvironment(environment);
	}
	
	public static void printEnvironment(Environment environment)
	{
		System.out.println("Environment:");
		for(Instance instance : environment.getInstances())
		{
			System.out.println(">> " + instance + " at " + instance.getCoordinate());
		}
		System.out.println();
	}
}
