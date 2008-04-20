package rutebaga.controller;

import java.util.Set;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.CommandQueue;
import rutebaga.controller.command.CreateContextMenuCommand;
import rutebaga.controller.command.FixedLabelDeterminer;
import rutebaga.controller.command.QueueCommand;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ConcreteListElementSource;
import rutebaga.controller.command.list.DynamicElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElementFactory;
import rutebaga.controller.command.list.ListElementSource;
import rutebaga.controller.command.list.StatsListElementFactory;
import rutebaga.controller.command.list.StatsListElementSource;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.item.Item;
import rutebaga.model.map.Tile;
import rutebaga.view.UserInterfaceFacade;

/**
 * TODO: resolve all the references here once the referenced classes actually
 * exist Given a set of Instances, ActionDeterminer produces a list of named
 * Commands in the form of an {@link ElementalList}. ActionDeterminer exposes a
 * single operation, {@link #determineActions()}, which uses a {@link Filter}
 * to determine which {@link Instance} in a set of Instances should be targeted.
 * Once it determines the target, it determines which actions are appropriate
 * for the targeted Instance.
 * 
 * For instance, if an {@link rutebaga.model.entity.Entity Entity} is present at
 * a {@link Location}, it will be targeted and {@link Command}s encapsulating
 * actions appropriate for an Entity, such as attacking or talking, will be
 * generated.
 * 
 * @author Matthew Chuah
 * @see ElementalList
 * @see Filter
 * @see rutebaga.model.environment.Instance
 * @see rutebaga.controller.command.Command
 */
public class ActionDeterminer
{
	private Entity<?> avatar;
	private UserInterfaceFacade facade;
	
	public ActionDeterminer(Entity<?> avatar) {
		this.avatar = avatar;
	}
	
	public void setUIFacade(UserInterfaceFacade facade) {
		this.facade = facade;
	}
	
	/**
	 * @param instances
	 * @return
	 */
	public ElementalList determineActions(Instance<?> target, CommandQueue queue) {
		ConcreteElementalList list = new ConcreteElementalList();
		for (Ability<?> ability: avatar.getAbilities())
			if (ability.canActOn(target))
				list.add(ability.toString(), QueueCommand.makeForQueue(new AbilityCommand(ability, target), queue));
		if (avatar == target)
			addSelfCommands(list);
		return list;
	}
	
	public Instance<?> determineTarget(InstanceSet instances) {
		Set<Entity> entities = instances.getEntities();
		if (entities.size() != 0)
			return entities.iterator().next();
		Set<Item> items = instances.getItems();
		if (items.size() != 0)
			return items.iterator().next();
		Set<Tile> tiles = instances.getTiles();
		if (tiles.size() != 0)
			return tiles.iterator().next();
		return null;
	}
	
	private void addSelfCommands(ConcreteElementalList list) {
		/*
		 * inventory
		 * stats
		 * abilties
		 */
		CreateContextMenuCommand command;
		
		command = new CreateInventoryMenuCommand(avatar);
		command.setUIFacade(facade);
		list.add("Inventory", command);
		
		command = new CreateStatsDisplayCommand(avatar);
		command.setUIFacade(facade);
		list.add("Stats", command);
	}
	
	private class CreateInventoryMenuCommand extends CreateContextMenuCommand {
		private Entity target;
		public CreateInventoryMenuCommand(Entity target) {
			this.target = target;
		}
		@Override
		public void execute() {
			/*
			ConcreteListElementSource source = new ConcreteListElementSource<Item>(target.getInventory());
			*/
		}
	}
	
	private class CreateStatsDisplayCommand extends CreateContextMenuCommand {
		private Entity target;
		public CreateStatsDisplayCommand(Entity target) {
			this.target = target;
		}
		@Override
		public void execute() {
			StatsListElementSource source = new StatsListElementSource();
			source.setStats(target.getStats());
			source.setLabel(new FixedLabelDeterminer(target.getName()));
			StatsListElementFactory factory = new StatsListElementFactory(); 
			DynamicElementalList<StatValue> list = new DynamicElementalList<StatValue>(source, factory);
			facade.createScrollMenu(list, 10);
		}
	}
	
	private class AbilityCommand<T> implements Command {

		private Ability<T> ability;
		private T target;
		
		public AbilityCommand(Ability<T> ability, T target ) {
			this.ability = ability;
			this.target = target;
		}
		
		public void execute() {
			ability.act(target);
		}

		public boolean isFeasible() {
			return ability.isFeasible();
		}
	}
}
