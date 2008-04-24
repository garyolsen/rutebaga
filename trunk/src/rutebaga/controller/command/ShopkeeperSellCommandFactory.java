package rutebaga.controller.command;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.model.storefront.StoreInstance;
import rutebaga.view.UserInterfaceFacade;

public class ShopkeeperSellCommandFactory implements CommandFactory<Item> {

	Entity<?> avatar, target;
	CommandQueue queue;
	UserInterfaceFacade facade;
	
	public ShopkeeperSellCommandFactory(Entity<?> avatar, Entity<?> target,
			UserInterfaceFacade facade, CommandQueue queue) {
		
		this.avatar = avatar;
		this.target = target;
		this.queue = queue;
		this.facade = facade;
	}

	public ElementalList getCommandListFor(Item element) {
		ConcreteElementalList list = new ConcreteElementalList();
		
		if(avatar.getInventory().getUnequipped().contains(element))
			list.add("Sell: " + element.getName(), QueueCommand.makeForQueue(new SellItemCommand(avatar, target, element), queue));
		
		return list;
	}
	
	private class SellItemCommand implements Command {
		private Entity<?> avatar, target;
		private Item item;
		
		public SellItemCommand(Entity avatar, Entity target, Item item) {
			this.avatar = avatar;
			this.target = target;
			this.item = item;
		}

		public void execute() {
			StoreInstance instance = target.getStoreFront().getInstance(avatar);
			instance.buy(item);

			facade.clearMenuStack();
		}

		public boolean isFeasible() {
			return target.getStoreFront().getInstance(avatar).canBuy(item);
		}
		
	}

}
