package rutebaga.controller.command;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.item.Item;
import rutebaga.model.storefront.StoreInstance;
import rutebaga.view.UserInterfaceFacade;

public class ShopkeeperInventoryCommandFactory implements CommandFactory<Item> {

	StoreInstance store;
	CommandQueue queue;
	UserInterfaceFacade facade;
	
	public ShopkeeperInventoryCommandFactory(StoreInstance store,
			UserInterfaceFacade facade, CommandQueue queue) {
		this.store = store;
		this.queue = queue;
		this.facade = facade;
	}

	public ElementalList getCommandListFor(Item item) {
		ConcreteElementalList list = new ConcreteElementalList();
		if(store.getItems().contains(item))
			list.add("Buy: " + item.getName(), QueueCommand.makeForQueue(new BuyCommand(item), queue));
		return list;
	}
	
	private class BuyCommand implements Command {
		
		private Item item;
		
		private BuyCommand(Item item) {
			this.item = item;
		}
		
		public boolean isFeasible() {
			return store.canSell(item);
		}
		
		public void execute() {
			store.sell(item);
			facade.popContextMenu();
		}
	}

}
