package rutebaga.model.environment;

import rutebaga.commons.Vector;

public class InternalContainer
{
	protected static class Location
	{
		private Vector coordinate;
		private Vector tile;
		private Environment environment;

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

	protected static class PhysicsContainer
	{
		private Vector momentum;
		private Vector appliedImpulse;
		private Vector velocity;

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
