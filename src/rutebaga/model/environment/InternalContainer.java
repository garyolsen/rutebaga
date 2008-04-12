package rutebaga.model.environment;

import rutebaga.commons.ObjectUtils;
import rutebaga.commons.Vector;

public class InternalContainer
{
	protected static class Location
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
			if (!ObjectUtils.equals(this.tile, tile))
			{
				this.tile = tile;
			}
		}

	}

	protected static class PhysicsContainer
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
			this.velocity = momentum.plus(appliedImpulse).times(1/mass);
			appliedImpulse = appliedImpulse.times(0);
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
