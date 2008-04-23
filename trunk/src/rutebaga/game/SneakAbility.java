package rutebaga.game;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.math.Bounds2D;
import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.ability.TemporaryEffectAction;
import rutebaga.model.entity.effect.FlagEffect;
import rutebaga.model.entity.effect.StatEffect;
import rutebaga.model.entity.stats.StatModification;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.Instance;

public class SneakAbility extends Ability<Entity>
{
	private static Map<Object, Bounds2D> lookupTable = new HashMap<Object, Bounds2D>(); 
	
	class VisionEffect extends ReversibleEntityEffect
	{
		private Vector2D dimensions = new Vector2D(15, 15);

		@Override
		public EntityEffect getReverseEffect(Object id)
		{
			return new ReverseVisionEffect(id);
		}

		@Override
		protected void affect(Entity entity, Object id)
		{
			lookupTable.put(id, entity.getVisionBounds());
			entity.setVisionBounds(new EllipseBounds2D(dimensions));
		}

	}
	
	class ReverseVisionEffect extends EntityEffect
	{
		private Object id;

		public ReverseVisionEffect(Object id)
		{
			super();
			this.id = id;
		}

		@Override
		protected void affect(Entity entity, Object id)
		{
			entity.setVisionBounds(lookupTable.get(this.id));
		}
		
		
	}

	private StatisticId movement;

	private Entity entity;

	public SneakAbility(StatisticId movement, Entity entity)
	{
		super();
		this.movement = movement;
		this.addAction(new TemporaryEffectAction(new VisionEffect(), 1000));
		this.addAction(new TemporaryEffectAction(new StatEffect(movement, -5), 1000));
		this.addAction(new TemporaryEffectAction(new FlagEffect("sneak"), 1000));
		this.entity = entity;
	}

	@Override
	public boolean canActOn(Instance<?> i)
	{
		return i == this.entity;
	}

	@Override
	public boolean exists()
	{
		return true;
	}

	public StatisticId getMovement()
	{
		return movement;
	}

	@Override
	public boolean isFeasible()
	{
		return !entity.getFlag("sneak");
	}

	public void setMovement(StatisticId movement)
	{
		this.movement = movement;
	}
}
