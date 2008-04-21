package rutebaga.model.storefront;

import java.util.Collection;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;

public class EntityStoreInstance implements StoreInstance {

	private EntityStoreFront storeFront;
	private Entity user;
	private Entity seller;
	
	public EntityStoreInstance(EntityStoreFront storefront, Entity user, Entity seller) {
		this.storeFront = storefront;
		this.user = user;
		this.seller = seller;
	}

	public void end() {
		// TODO Auto-generated method stub
		
	}

	public Collection<Item> getItems() {
		return storeFront.getItems();
	}

	public double priceOf(Item item) {
		return item.getDefaultPrice();
	}

	public void sell(Item item) {
		if (userHasSufficientFunds(sellPriceOf(item)))
		{
			if (seller.getInventory().remove(item))
			{
				removeFundsFromUser(sellPriceOf(item));
				item.giveTo(user.getInventory());
				depositFundsInSeller(sellPriceOf(item));
			}
		}
	}

	private void depositFundsInSeller(double sellPriceOf) {
		seller.addToMoney(sellPriceOf);
	}

	public double sellPriceOf(Item item) {
		return Math.max( priceOf(item)*0.1, ((seller.getBargainSkill().getValue(seller) - user.getBargainSkill().getValue(user)) * 1.0) + priceOf( item ) );
	}

	public void setStoreInstanceBargainSkill(double skill) {
		//
	}

	public void setUserBargainSkill(double skill) {
		//
	}
	
	private boolean userHasSufficientFunds(double amount)
	{
		return ( user.getWallet().getValue(user) >= amount );
	}
	
	private void removeFundsFromUser(double amount)
	{
		user.getWallet().addTo(user, -amount);
	}

}
