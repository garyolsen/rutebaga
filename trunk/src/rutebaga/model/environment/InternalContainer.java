package rutebaga.model.environment;

import rutebaga.commons.Vector;

/**
 * This class is used as a workaround to Java's lack of the "internal" keyword.
 * 
 * By containing Location and PhysicsContainer (and declaring them final), any
 * class outside of the environment package are prevented from interacting with
 * instances of these classes.
 * 
 * More specifically, subclasses of Instance are prevented from interacting with
 * the container components of the Instance, despite the fact that their
 * accessors are available (due to protected semantics).
 * 
 * @author Gary LosHuertos
 * 
 */
public final class InternalContainer
{
	/**
	 * Container class for an instance's location properties.
	 * 
	 * @author Gary LosHuertos
	 * 
	 */
	protected static final class Location
	{
		private Vector coordinate;
		private Vector tile;
		private Environment environment;
		private final Instance instance;

		public Location(Instance instance)
		{
			this.instance = instance;
		}

		protected Instance getInstance()
		{
			return instance;
		}

		protected Vector getCoordinate()
		{
			return coordinate;
		}

		protected Environment getEnvironment()
		{
			return environment;
		}

		protected Vector getTile()
		{
			return tile;
		}

		protected void setCoordinate(Vector coordinate)
		{
			this.coordinate = coordinate;
		}

		protected void setEnvironment(Environment environment)
		{
			this.environment = environment;
		}

		protected void setTile(Vector tile)
		{
			this.tile = tile;
		}

	}

	/**
	 * Container class for an instance's Newtonian properties.
	 * 
	 * @author Gary LosHuertos
	 * 
	 */
	protected static final class PhysicsContainer
	{
		private Vector momentum = new Vector(0, 0);
		private Vector appliedImpulse = new Vector(0, 0);
		private Vector velocity = new Vector(0, 0);
		private final Instance instance;

		public PhysicsContainer(Instance instance)
		{
			this.instance = instance;
		}

		protected Instance getInstance()
		{
			return instance;
		}

		protected void update(double friction)
		{
			double mass = instance.getMass();
			double frictionCoeff = 0.1;
			this.momentum = this.momentum.times(1-frictionCoeff);
			this.appliedImpulse = this.appliedImpulse.times(1-frictionCoeff);
			this.velocity = momentum.plus(appliedImpulse).times(1 / mass);
			appliedImpulse = appliedImpulse.times(0.7);
			//TODO add REAL support for friction
		}

		public void applyImpulse(Vector impulse)
		{
			this.appliedImpulse = this.appliedImpulse.plus(impulse);
		}

		public void applyMomentum(Vector momentum)
		{
			this.momentum = this.momentum.plus(momentum);
		}

		protected Vector getAppliedImpulse()
		{
			return appliedImpulse;
		}

		protected Vector getMomentum()
		{
			return momentum;
		}

		protected Vector getVelocity()
		{
			return velocity;
		}

		protected void setAppliedImpulse(Vector appliedImpulse)
		{
			this.appliedImpulse = appliedImpulse;
		}

		protected void setMomentum(Vector momentum)
		{
			this.momentum = momentum;
		}

		protected void setVelocity(Vector velocity)
		{
			this.velocity = velocity;
		}
	}
}
