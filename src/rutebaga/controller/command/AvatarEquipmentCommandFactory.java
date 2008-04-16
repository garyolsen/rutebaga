package rutebaga.controller.command;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.item.Item;
import rutebaga.view.ViewFacade;

public class AvatarEquipmentCommandFactory implements CommandFactory<Item> {
	
	private CharEntity avatar;
	private ViewFacade facade;
	
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
		list.add("Equip", new UnequipCommand(item));
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
			
		}
	}
}
