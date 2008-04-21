package rutebaga.controller.command;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.item.Item;
import rutebaga.model.storefront.StoreInstance;
import rutebaga.view.UserInterfaceFacade;

public class ShopkeeperInventoryCommandFactory implements CommandFactory<Item> {

	StoreInstance store;
	
	public ShopkeeperInventoryCommandFactory(StoreInstance store,
			UserInterfaceFacade facade, CommandQueue queue) {
		this.store = store;
	}

	public ElementalList getCommandListFor(Item item) {
		ConcreteElementalList list = new ConcreteElementalList();
		list.add("Appraise", new AppraiseCommand(item));
		list.add("Buy", new BuyCommand(item));
		return list;
	}

	private class AppraiseCommand implements Command {
		
		private Item item;
		
		private AppraiseCommand(Item item) {
			this.item = item;
		}
		
		public boolean isFeasible() {
			return true;
		}
		
		public void execute() {
			
		}
	}
	
	private class BuyCommand implements Command {
		
		private Item item;
		
		private BuyCommand(Item item) {
			this.item = item;
		}
		
		public boolean isFeasible() {
			return true;
		}
		
		public void execute() {
			
		}
	}

}
