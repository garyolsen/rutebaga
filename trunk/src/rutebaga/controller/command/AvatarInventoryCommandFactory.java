package rutebaga.controller.command;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;

public class AvatarInventoryCommandFactory implements CommandFactory<Item> {
	
	private final Entity<?> avatar;
	private final UserInterfaceFacade facade;
	private CommandQueue queue;
	
	public AvatarInventoryCommandFactory(Entity<?> avatar, UserInterfaceFacade facade, CommandQueue queue) {
		this.avatar = avatar;
		this.facade = facade;
		this.queue = queue;
	}
	
	public ElementalList getCommandListFor(Item item) {
		ConcreteElementalList list = new ConcreteElementalList();
		//if (facade != null) //Stats of an item aren't viewable !
		//	list.add("Stats", new DisplayItemStatsCommand(facade, item));
		list.add("Drop", QueueCommand.makeForQueue(new DropCommand(item), queue));
		if (item.isEquippable())
			list.add("Equip", QueueCommand.makeForQueue(new EquipCommand(item), queue));
		return list;
	}
	
	private class DropCommand implements Command {
		private Item item;
		
		public DropCommand(Item item) {
			this.item = item;
		}
		
		public boolean isFeasible() {
			return true;
		}
		
		public void execute() {
			avatar.getInventory().drop(item);
			facade.clearContextMenuStack();
		}
	}
	
	private class EquipCommand implements Command {

		private Item item;
		
		public EquipCommand(Item item) {
			this.item = item;
		}
		
		public boolean isFeasible() {
			return true;
		}
		
		public void execute() {
			avatar.getInventory().equip(item);
			facade.clearContextMenuStack();
		}
	}
}
