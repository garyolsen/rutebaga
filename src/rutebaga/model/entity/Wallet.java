package rutebaga.model.entity;

import rutebaga.commons.math.BidirectionalValueProvider;

public class Wallet extends BidirectionalValueProvider<Entity> {

	@Override
	public double addTo(Entity entity, double value) {
		entity.addToMoney(value);
		return entity.getMoneyAmount();
	}

	@Override
	public double getValue(Entity entity) {
		return entity.getMoneyAmount();
	}

}
