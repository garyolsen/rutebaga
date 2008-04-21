package rutebaga.model.storefront;

import java.util.Collection;

import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;

public class VendingMachineInstance implements StoreInstance {

	private VendingMachine vendingMachine;
	private Entity entity;
	private double storeBargainSkill = 0.0;
	private double userBargainSkill = 0.0;
	
	public VendingMachineInstance(VendingMachine vendingMachine, Entity entity)
	{
		this.vendingMachine = vendingMachine;
		this.entity = entity;
	}

	public void end() {
	}

	public Collection<Item> getItems() {
		return vendingMachine.getItems();
	}
	
	/**
	 * Returns the default price of the item
	 */
	public double priceOf(Item item) {
		return item.getDefaultPrice();
	}

	public void sell(Item item) {
		if (canSell(item))
		{
			removeFunds(sellPriceOf(item));
			item.giveTo(entity.getInventory());
		}
	}
	
	public boolean canSell(Item item ) {
		return userHasSufficientFunds(sellPriceOf(item));
	}

	public double sellPriceOf(Item item) {
		return Math.max( priceOf(item)*0.1, ((storeBargainSkill - userBargainSkill) * 1.0) + priceOf( item ) );
	}

	public void setUserBargainSkill(double skill) {
		this.userBargainSkill = skill;
	}

	public void setStoreInstanceBargainSkill(double skill) {
		this.storeBargainSkill = skill;
	}
	
	private boolean userHasSufficientFunds(double amount)
	{
		return ( entity.getWallet().getValue(entity) >= amount );
	}
	
	private void removeFunds(double amount)
	{
		entity.getWallet().addTo(entity, -amount);
	}
}
