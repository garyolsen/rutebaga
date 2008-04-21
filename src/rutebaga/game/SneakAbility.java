package rutebaga.game;

import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.ability.TemporaryEffectAction;
import rutebaga.model.entity.effect.StatEffect;
import rutebaga.model.entity.stats.StatModification;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.Instance;

public class SneakAbility extends Ability<Entity>
{
	private StatisticId movement;

	class MovementAction extends TemporaryEffectAction
	{

		public MovementAction()
		{
			super();
			this.setEffect(new StatEffect(movement, -10));
			this.setLifetime(1000);
		}

	}

	class VisionAction extends TemporaryEffectAction
	{

		public VisionAction()
		{
			super();
			this.setEffect(new VisionEffect());
			this.setLifetime(1000);
		}

	}

	class VisionEffect extends ReversibleEntityEffect
	{
		private Vector2D dimensions = new Vector2D(15, 15);

		@Override
		public EntityEffect getReverseEffect(Object id)
		{
			VisionEffect effect = new VisionEffect();
			effect.dimensions = new Vector2D(7, 7);
			return effect;
		}

		@Override
		protected void affect(Entity entity, Object id)
		{
			entity.setVisionBounds(new RectBounds2D(dimensions));
		}

	}

	public SneakAbility(StatisticId movement)
	{
		super();
		this.movement = movement;
		this.addAction(new VisionAction());
		this.addAction(new MovementAction());
	}

	@Override
	public boolean canActOn(Instance<?> i)
	{
		return true;
	}

	@Override
	public boolean exists()
	{
		return true;
	}

	@Override
	public boolean isFeasible()
	{
		return true;
	}

	public StatisticId getMovement()
	{
		return movement;
	}

	public void setMovement(StatisticId movement)
	{
		this.movement = movement;
	}
}
