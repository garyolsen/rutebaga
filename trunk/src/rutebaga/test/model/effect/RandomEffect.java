package rutebaga.test.model.effect;

import java.util.Set;

import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.DefaultLayers;
import rutebaga.model.effect.TargetableEffect;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public class RandomEffect extends TargetableEffect<RandomEffect, Instance> {

	public RandomEffect(ValueProvider<? super RandomEffect> impulse) {
		super(null, impulse);
	}

	@Override
	protected void tickLogic()
	{
		Set<Instance> set = getCoexistantInstances();
		if (set.contains(getTarget())) {
			Instance target = getTarget();
			if (target.getSetIdentifier().equals(InstanceSetIdentifier.ENTITY)) {
				MutableVector2D direction = new MutableVector2D(target
						.getCoordinate().getX(), target.getCoordinate()
						.getY());
				direction.detract(this.getCoordinate());
				direction.multiplyBy((3 - direction.getMagnitude()) / 3);
				target.applyMomentum(direction);
			}
		}
	}

	@Override
	public double getLayer() {
		return DefaultLayers.AIR.getLayer();
	}

	@Override
	public double getMass() {
		return 1;
	}

}
