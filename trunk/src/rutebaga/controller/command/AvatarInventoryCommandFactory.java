package rutebaga.controller.command;

import rutebaga.controller.list.ConcreteElementalList;
import rutebaga.controller.list.ElementalList;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.item.Item;

public class AvatarInventoryCommandFactory implements CommandFactory<Item> {
	
	private CharEntity avatar;
	
	public void setAvatar(CharEntity avatar) {
		this.avatar = avatar;
	}
	
	public ElementalList getCommandListFor(Item item) {
		ConcreteElementalList list = new ConcreteElementalList();
		list.add("Stats", new ShowStatsCommand(item));
		list.add("Drop", new DropCommand(item));
		if (item.isEquippable())
			list.add("Equip", new EquipCommand(item));
		return list;
	}
	
	private abstract class ItemCommand implements Command {
		protected Item item;
		
		protected ItemCommand(Item item) {
			this.item = item;
		}
	}
	
	private class ShowStatsCommand extends ItemCommand {
		private ShowStatsCommand(Item item) {
			super(item);
		}
		
		public boolean isFeasible() {
			return false;
		}
		
		public void execute() {
			
		}
	}
	
	private class DropCommand extends ItemCommand {
		private DropCommand(Item item) {
			super(item);
		}
		
		public boolean isFeasible() {
			return false;
		}
		
		public void execute() {
			
		}
	}
	
	private class EquipCommand extends ItemCommand {
		
		private EquipCommand(Item item) {
			super(item);
		}
		
		public boolean isFeasible() {
			return true;
		}
		
		public void execute() {
			
		}
	}
}
