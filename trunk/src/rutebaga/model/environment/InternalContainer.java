package rutebaga.model.environment;

import rutebaga.commons.math.GeneralVector;
import rutebaga.commons.math.MutableVector;
import rutebaga.commons.math.Vector;

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
		private MutableVector momentum = new MutableVector(0, 0);
		private MutableVector appliedImpulse = new MutableVector(0, 0);
		private MutableVector velocity = new MutableVector(0, 0);
		private final Instance instance;
		private boolean dirty = false;

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
			this.momentum.times(1 - frictionCoeff);
			this.appliedImpulse.times(1 - frictionCoeff);
			this.velocity.times(0);
			this.velocity.plus(momentum).plus(appliedImpulse).times(1 / mass);
			appliedImpulse = appliedImpulse.times(0.7);
			//XXX MEGA LoD violation
			if(appliedImpulse.getMagnitude() <= 0.001 && momentum.getMagnitude() <= 0.001)
			{
				this.instance.getEnvironment().getDirtyPhysics().remove(this.instance);
				dirty = false;
			}
			// TODO add REAL support for friction
		}

		public void applyImpulse(Vector impulse)
		{
			dirty();
			this.appliedImpulse = this.appliedImpulse.plus(impulse);
		}

		public void applyMomentum(GeneralVector momentum)
		{
			dirty();
			this.momentum = this.momentum.plus(momentum);
		}

		protected MutableVector getAppliedImpulse()
		{
			return appliedImpulse;
		}

		protected MutableVector getMomentum()
		{
			return momentum;
		}

		protected MutableVector getVelocity()
		{
			return velocity;
		}

		protected void setAppliedImpulse(MutableVector appliedImpulse)
		{
			this.appliedImpulse = appliedImpulse;
		}

		protected void setMomentum(MutableVector momentum)
		{
			this.momentum = momentum;
		}

		protected void setVelocity(MutableVector velocity)
		{
			this.velocity = velocity;
		}
		
		private void dirty()
		{
			if(this.dirty == true) return;
			this.dirty = true;
			this.instance.getEnvironment().getDirtyPhysics().add(this.instance);
		}
	}
}
