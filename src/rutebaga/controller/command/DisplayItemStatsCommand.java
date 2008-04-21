package rutebaga.controller.command;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.model.item.Item;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;

public class DisplayItemStatsCommand implements Command {
	
	private Item item;
	private UserInterfaceFacade facade;
	
	public DisplayItemStatsCommand(UserInterfaceFacade facade, Item item) {
		this.item = item;
		this.facade = facade;
	}
	
	public boolean isFeasible() {
		return true;
	}
	
	public void execute() {
		ConcreteElementalList itemStats = new ConcreteElementalList();
		itemStats.setLabel(item.getName() + " stats:");
		itemStats.add("This is where info");
		itemStats.add("about the item");
		itemStats.add("should eventually go");
		facade.createScrollMenu(itemStats, 5);
	}
}
