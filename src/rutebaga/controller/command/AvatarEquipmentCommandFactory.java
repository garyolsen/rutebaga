package rutebaga.controller.command;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;

public class AvatarEquipmentCommandFactory implements CommandFactory<Item> {
	
	private Entity<?> avatar;
	private UserInterfaceFacade facade;
	
	public AvatarEquipmentCommandFactory(Entity<?> avatar, UserInterfaceFacade facade) {
		this.avatar = avatar;
		this.facade = facade;
	}
	
	public void setAvatar(CharEntity avatar) {
		this.avatar = avatar;
	}
	
	public void setFacade(ViewFacade facade) {
		this.facade = facade;
	}
	
	public ElementalList getCommandListFor(Item item) {
		ConcreteElementalList list = new ConcreteElementalList();
		if (facade != null)
			list.add("Stats", new DisplayItemStatsCommand(facade, item));
		list.add("Unequip", new UnequipCommand(item));
		return list;
	}
	
	private class UnequipCommand implements Command {
		protected Item item;
		
		private UnequipCommand(Item item) {
			this.item = item;
		}
		
		public boolean isFeasible() {
			return true;
		}
		
		public void execute() {
			avatar.getInventory().unequip(item);
			facade.clearContextMenuStack();
		}
	}
}
