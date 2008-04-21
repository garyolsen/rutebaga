package rutebaga.controller.command;

import java.util.List;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.item.Item;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;

public class DisplayItemStatsCommand implements Command {
	
	private Item<?> item;
	private UserInterfaceFacade facade;
	
	public DisplayItemStatsCommand(UserInterfaceFacade facade, Item item) {
		this.item = item;
		this.facade = facade;
	}
	
	public boolean isFeasible() {
		return false;
	}
	
	public void execute() {
		ConcreteElementalList itemStats = new ConcreteElementalList();
		
		//facade.createSubContextMenu(itemStats);
	}
}
