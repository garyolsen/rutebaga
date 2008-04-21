package rutebaga.model.environment;

import rutebaga.commons.math.GenericVector2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.Vector2D;

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
		private Vector2D coordinate;
		private IntVector2D tile;
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

		protected Vector2D getCoordinate()
		{
			return coordinate;
		}

		protected Environment getEnvironment()
		{
			return environment;
		}

		protected IntVector2D getTile()
		{
			return tile;
		}

		protected void setCoordinate(Vector2D coordinate)
		{
			this.coordinate = coordinate;
		}

		protected void setEnvironment(Environment environment)
		{
			this.environment = environment;
		}

		protected void setTile(IntVector2D tile)
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
		private MutableVector2D momentum = new MutableVector2D(0, 0);
		private MutableVector2D appliedImpulse = new MutableVector2D(0, 0);
		private MutableVector2D velocity = new MutableVector2D(0, 0);
		private final Instance instance;
		private boolean dirty = false;
		private boolean immobile = false;
		private double friction = 0;

		protected double getFriction()
		{
			return friction;
		}

		protected void setFriction(double friction)
		{
			if(Double.compare(friction, this.friction) == 0)
				return;
			this.friction = friction;
		}

		boolean isImmobile()
		{
			return immobile;
		}

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
			if(immobile) return;
			double mass = instance.getMass();
			double frictionCoeff = 0.4;
			this.momentum.multiplyBy(1 - frictionCoeff);
			this.appliedImpulse.multiplyBy(1 - frictionCoeff);
			this.velocity.multiplyBy(0);
			this.velocity.accumulate(momentum).accumulate(appliedImpulse).multiplyBy(1 / mass);
			appliedImpulse.multiplyBy(0.7);
			//XXX MEGA LoD violation
			if(appliedImpulse.getMagnitude() <= 0.001 && momentum.getMagnitude() <= 0.001)
			{
				this.appliedImpulse.multiplyBy(0);
				this.momentum.multiplyBy(0);
				this.velocity.multiplyBy(0);
				this.instance.getEnvironment().getDirtyPhysics().remove(this.instance);
				dirty = false;
			}
			// TODO add REAL support for friction
		}

		@SuppressWarnings("unchecked")
		public void applyImpulse(GenericVector2D impulse)
		{
			if(immobile) return;
			dirty();
			this.appliedImpulse = this.appliedImpulse.accumulate(impulse);
		}

		@SuppressWarnings("unchecked")
		public void applyMomentum(GenericVector2D momentum)
		{
			if(immobile) return;
			dirty();
			this.momentum = this.momentum.accumulate(momentum);
		}

		protected MutableVector2D getAppliedImpulse()
		{
			return appliedImpulse;
		}

		protected MutableVector2D getMomentum()
		{
			return momentum;
		}

		protected MutableVector2D getVelocity()
		{
			return velocity;
		}

		protected void setAppliedImpulse(MutableVector2D appliedImpulse)
		{
			this.appliedImpulse = appliedImpulse;
		}

		protected void setMomentum(MutableVector2D momentum)
		{
			this.momentum = momentum;
		}

		protected void setVelocity(MutableVector2D velocity)
		{
			this.velocity = velocity;
		}
		
		private void dirty()
		{
			if(this.dirty == true) return;
			this.dirty = true;
			this.instance.getEnvironment().getDirtyPhysics().add(this.instance);
		}

		void setImmobile(boolean immobile)
		{
			this.immobile = immobile;
		}
	}
}
