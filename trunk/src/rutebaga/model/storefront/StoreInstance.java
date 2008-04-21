package rutebaga.model.storefront;

import java.util.Collection;
import rutebaga.model.item.Item;

public interface StoreInstance {
	
	public Collection<Item> getItems();
	
	public double priceOf(Item item);
	
	public boolean canSell(Item item);
	
	public boolean canBuy(Item item);
	
	public void buy(Item item);
	
	public void sell(Item item);
	
	public double sellPriceOf(Item item);
	
	public void setUserBargainSkill(double skill);
	
	public void setStoreInstanceBargainSkill(double skill);

	public void end();
	
}
