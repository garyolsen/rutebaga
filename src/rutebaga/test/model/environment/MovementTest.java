package rutebaga.test.model.environment;

import rutebaga.commons.math.Bounds2D;
import rutebaga.commons.math.RectBounds;
import rutebaga.commons.math.Vector;
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
			rutebaga.commons.Log.log("Failed");

		Bounds2D monitoredBounds = new RectBounds(new Vector(-4, -4), new Vector(
				2, 2));
		BoundsTracker tracker = new BoundsTracker(monitoredBounds, first);

		TestInstance second = new TestInstance("Alice");
		success = environment.add(second, new Vector(0, 0));

		if (!success)
			rutebaga.commons.Log.log("Failed");

		printEnvironment(environment);
		printTracker(tracker);
		environment.tick();
		printEnvironment(environment);
		printTracker(tracker);
		rutebaga.commons.Log.log("applying impulse");
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
		rutebaga.commons.Log.log("applying momentum");
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
		rutebaga.commons.Log.log("Environment:");
		for (Instance instance : environment.getInstances())
		{
			rutebaga.commons.Log.log(">> " + instance + " at "
					+ instance.getCoordinate());
		}
		rutebaga.commons.Log.log();
	}

	public static void printTracker(BoundsTracker tracker)
	{
		rutebaga.commons.Log.log("Tracker:");
		if (tracker.getInstances().isEmpty())
		{
			rutebaga.commons.Log.log("\tNothing to see here.");
		}
		for (Instance instance : tracker.getInstances())
		{
			rutebaga.commons.Log.log(">> " + instance);
		}
		rutebaga.commons.Log.log();
	}
}
