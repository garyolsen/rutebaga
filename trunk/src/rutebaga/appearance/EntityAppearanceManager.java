package rutebaga.appearance;

import java.awt.Component;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;

public class EntityAppearanceManager extends AppearanceManager
{
	private class Standing extends State
	{
		private int currentFrame;
		private int directions = standing.length;

		@Override
		Appearance getAppearance()
		{
			Appearance dirApps[] = standing[getDirectionOrdinal(directions)];
			return dirApps[currentFrame % dirApps.length];
		}

		@Override
		void tick()
		{
			if (entity.isWalking())
			{
				changeState(new Walking());
				currentState.tick();
			}
			// currentFrame++;
		}

	}

	private abstract class State
	{
		void changeState(State state)
		{
			EntityAppearanceManager.this.currentState = state;
		}

		abstract Appearance getAppearance();

		abstract void tick();
	}

	private class Walking extends State
	{
		private int currentFrame;
		private int directions = standing.length;
		int wait = 0;

		@Override
		Appearance getAppearance()
		{
			Appearance dirApps[] = walking[getDirectionOrdinal(directions)];
			return dirApps[(currentFrame / (getWalkingWait() + 1))
					% dirApps.length];
		}

		@Override
		void tick()
		{
			if (!entity.isWalking())
			{
				changeState(new Standing());
				currentState.tick();
			}
			currentFrame++;
		}

	}

	private Entity entity;

	private Appearance[][] walking;

	private Appearance[][] standing;
	private State currentState;

	public EntityAppearanceManager(Entity entity)
	{
		super();
		this.entity = entity;
	}

	@Override
	public Appearance getAppearance()
	{
		if (currentState == null)
			tick();
		return currentState.getAppearance();
	}

	public Entity getEntity()
	{
		return entity;
	}

	public Appearance[][] getStanding()
	{
		return standing;
	}

	public Appearance[][] getWalking()
	{
		return walking;
	}

	public void setStanding(Appearance[][] standing)
	{
		this.standing = standing;
	}

	public void setWalking(Appearance[][] walking)
	{
		this.walking = walking;
	}

	@Override
	public void tick()
	{
		if (currentState != null)
			currentState.tick();
		else
			currentState = new Standing();
	}

	private int getDirectionOrdinal(int total)
	{
		double offset = 0.75;
		TileConverter conv = entity.getEnvironment().getTileConvertor();
		Vector2D direction = conv.toRect(entity.getFacing().plus(entity.getCoordinate())).minus(conv.toRect(entity.getCoordinate()));
		double angle = direction.getAngle() + (2 + offset) * Math.PI;
		angle -= 2 * Math.PI * (int) (angle * 0.5 / Math.PI);
		double proportion = angle * 0.5 / Math.PI;
		return (int) (proportion * total);
	}

	private int getWalkingWait()
	{
		Vector2D velocity = entity.getVelocity();
		double mag = velocity.getMagnitude();
		return (int) (0.5 / mag);
	}

}
