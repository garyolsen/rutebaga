package rutebaga.test.model.environment;

import rutebaga.commons.Bounds;
import rutebaga.commons.RectBounds;
import rutebaga.commons.Vector;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Rect2DTileConvertor;

public class MovementTest
{
	public static void main(String... args)
	{
		TestInstance first = new TestInstance("Alice");
		Environment environment = new Environment(new Rect2DTileConvertor());
		boolean success = environment.add(first, new Vector(6, 6));

		if (!success)
			System.out.println("Failed");

		Bounds monitoredBounds = new RectBounds(new Vector(-4, -4), new Vector(
				2, 2));
		BoundsTracker tracker = new BoundsTracker(monitoredBounds, first);

		TestInstance second = new TestInstance("Alice");
		success = environment.add(second, new Vector(0, 0));

		if (!success)
			System.out.println("Failed");

		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		System.out.println("applying impulse");
		second.applyImpulse(new Vector(1, 1));
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		System.out.println("applying momentum");
		second.applyMomentum(new Vector(1.0, 1.0));
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		first.applyMomentum(new Vector(1, 1));
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
	}

	public static void printEnvironment(Environment environment)
	{
		System.out.println("Environment:");
		for (Instance instance : environment.getInstances())
		{
			System.out.println(">> " + instance + " at "
					+ instance.getCoordinate());
		}
		System.out.println();
	}

	public static void printTracker(BoundsTracker tracker)
	{
		System.out.println("Tracker:");
		if (tracker.getInstances().isEmpty())
		{
			System.out.println("\tNothing to see here.");
		}
		for (Instance instance : tracker.getInstances())
		{
			System.out.println(">> " + instance);
		}
		System.out.println();
	}
}
