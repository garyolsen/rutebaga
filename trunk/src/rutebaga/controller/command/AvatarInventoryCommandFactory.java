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
	
	public AvatarInventoryCommandFactory(final Entity<?> charEntity, final UserInterfaceFacade uiFacade) {
		this.avatar = charEntity;
		this.facade = uiFacade;
	}
	
	public ElementalList getCommandListFor(Item item) {
		ConcreteElementalList list = new ConcreteElementalList();
		if (facade != null)
			list.add("Stats", new DisplayItemStatsCommand(facade, item));
		list.add("Drop", new DropCommand(item));
		if (item.isEquippable())
			list.add("Equip", new EquipCommand(item));
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
