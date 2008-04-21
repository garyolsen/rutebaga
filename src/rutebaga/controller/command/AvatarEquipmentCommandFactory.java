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
	private CommandQueue queue;
	
	public AvatarEquipmentCommandFactory(Entity<?> avatar, UserInterfaceFacade facade, CommandQueue queue) {
		this.avatar = avatar;
		this.facade = facade;
		this.queue = queue;
	}
	
	public void setAvatar(CharEntity avatar) {
		this.avatar = avatar;
	}
	
	public void setFacade(ViewFacade facade) {
		this.facade = facade;
	}
	
	public ElementalList getCommandListFor(Item item) {
		ConcreteElementalList list = new ConcreteElementalList();
		list.add("Unequip", QueueCommand.makeForQueue(new UnequipCommand(item), queue));
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
