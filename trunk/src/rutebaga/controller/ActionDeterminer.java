package rutebaga.controller;

import java.awt.Point;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import rutebaga.commons.logic.Rule;
import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.AvatarAbilityCommandFactory;
import rutebaga.controller.command.AvatarEquipmentCommandFactory;
import rutebaga.controller.command.AvatarInventoryCommandFactory;
import rutebaga.controller.command.CloseContextMenuCommand;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.CommandQueue;
import rutebaga.controller.command.CreateContextMenuCommand;
import rutebaga.controller.command.FixedLabelDeterminer;
import rutebaga.controller.command.LabelDeterminer;
import rutebaga.controller.command.QueueCommand;
import rutebaga.controller.command.ShopkeeperInventoryCommandFactory;
import rutebaga.controller.command.ShopkeeperSellCommandFactory;
import rutebaga.controller.command.list.AbilityListElementSource;
import rutebaga.controller.command.list.AbilityUseListElementFactory;
import rutebaga.controller.command.list.BackedListElementFactory;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.CollectionListElementSource;
import rutebaga.controller.command.list.DynamicElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElementFactory;
import rutebaga.controller.command.list.StatsListElementFactory;
import rutebaga.controller.command.list.StatsListElementSource;
import rutebaga.controller.command.list.TargetSource;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.Mount;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.npc.Speech;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.item.Item;
import rutebaga.model.map.Tile;
import rutebaga.model.storefront.StoreInstance;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.game.MapComponent;

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
public class ActionDeterminer implements TargetSource
{
	private Entity<?> avatar;
	private UserInterfaceFacade facade;
	private TargetSource targetSource;
	
	public ActionDeterminer(Entity<?> avatar, TargetSource targetSource) {
		this.avatar = avatar;
		this.targetSource = targetSource;
	}
	
	public void setUIFacade(UserInterfaceFacade facade) {
		this.facade = facade;
	}
	
	public int getTileWidth()
	{
		return avatar.getEnvironment().getAppearanceAttr().getTileWidth();
	}
	
	public int getTileHeight()
	{
		return avatar.getEnvironment().getAppearanceAttr().getTileWidth();
	}
	
	/**
	 * @param instances
	 * @return
	 */
	public ElementalList determineActions(Instance<?> target, CommandQueue queue) {
		ConcreteElementalList list = new ConcreteElementalList();
		if (avatar == target || avatar.getMount() == target) {
			addSelfCommands(list, queue);
			addAbilityCommands(list, avatar, queue);
			if(avatar.getMount() != null) {
				QueueCommand qC = QueueCommand.makeForQueue(new DismountCommand(avatar.getMount(), avatar), queue);
				list.add("Dismount", qC);
			}
			
		}  else {
			
			InstanceSet instanceType = new ConcreteInstanceSet();
			instanceType.add(target);
			
			if(instanceType.getEntities().size() != 0 && avatar != target) {
				Entity<?> entity = (Entity<?>) target;
				
				addTargetCommands(list, entity, queue);
			}
		}
		list.add("Close", new CloseContextMenuCommand(facade));
		
		return list;
	}
	
	public Instance<?> determineTarget(InstanceSet instances) {
		Set<Entity> entities = instances.getEntities();
		if (entities.size() != 0)
			return entities.iterator().next();
		/* No more targeting items or tiles kthxbye
		Set<Item> items = instances.getItems();
		if (items.size() != 0)
			return items.iterator().next();
		Set<Tile> tiles = instances.getTiles();
		if (tiles.size() != 0)
			return tiles.iterator().next();
		*/
		return null;
	}
	
	private void addSelfCommands(ConcreteElementalList list, CommandQueue queue) {
		CreateContextMenuCommand command;
		
		command = new CreateInventoryMenuCommand(avatar, queue);
		command.setUIFacade(facade);
		list.add("Inventory", command);
		
		command = new CreateEquipmentMenuCommand(avatar, queue);
		command.setUIFacade(facade);
		list.add("Equipment", command);
		
		command = new CreateStatsDisplayCommand(avatar);
		command.setUIFacade(facade);
		list.add("Stats", command);
	}
	
	private void addTargetCommands(ConcreteElementalList list, Entity<?> target, CommandQueue queue) {
		CreateContextMenuCommand command;
		addAbilityCommands(list, target, queue);
		
		command = new CreateStatsDisplayCommand(avatar);
		command.setUIFacade(facade);
		list.add("Stats", command);
		
		if(target.getStoreFront() != null) {
			command = new CreateStoreBuyMenuCommand(target, queue);
			list.add("Buy", command);
			
			command = new CreateStoreSellMenuCommand(avatar, target, queue);
			list.add("Sell", command);
		}
		
		list.add("Talk", QueueCommand.makeForQueue(new TalkCommand(avatar, target), queue));		
		
		if(target instanceof Mount) { //TODO GET RID OF THIS!!
			
			QueueCommand qC = QueueCommand.makeForQueue(new MountCommand((Mount) target, avatar), queue);
			list.add("Mount", qC);
		}
	}
	
	private void addAbilityCommands(ConcreteElementalList list, Instance<?> target, CommandQueue queue) {
		CreateContextMenuCommand command;
		
		command = new CreateAbilitiesMenuCommand(target, queue);
		command.setUIFacade(facade);
		list.add("Abilities", command);
	}
	
	private ElementalList createCloseableList(ElementalList list) {
		ConcreteElementalList concreteList = new ConcreteElementalList();
		concreteList.add(list);
		concreteList.add("Close", new CloseContextMenuCommand(facade));
		
		return concreteList;
	}
	
	private class CreateInventoryMenuCommand extends CreateContextMenuCommand {
		private Entity target;
		private CommandQueue queue;
		public CreateInventoryMenuCommand(Entity target, CommandQueue queue) {
			this.target = target;
			this.queue = queue;
		}
		@Override
		public void execute() {
			Inventory inventory = target.getInventory();
			LabelDeterminer label = new FixedLabelDeterminer(target.getName() + "'s Inventory");
			CollectionListElementSource<Item> source = new CollectionListElementSource<Item>(label, inventory.getUnequipped());
			AvatarInventoryCommandFactory commands = new AvatarInventoryCommandFactory(avatar, facade, queue);
			BackedListElementFactory<Item> factory = new BackedListElementFactory<Item>(commands, facade);
			DynamicElementalList<Item> list = new DynamicElementalList<Item>(source, factory);
			facade.createSubContextMenu(createCloseableList(list));
		}
	}
	
	private class CreateEquipmentMenuCommand extends CreateContextMenuCommand {
		private Entity target;
		private CommandQueue queue;
		
		public CreateEquipmentMenuCommand(Entity target, CommandQueue queue) {
			this.target = target;
			this.queue = queue;
		}
		@Override
		public void execute() {
			Inventory inventory = target.getInventory();
			LabelDeterminer label = new FixedLabelDeterminer(target.getName() + "'s Equipment");
			CollectionListElementSource<Item> source = new CollectionListElementSource<Item>(label, inventory.getEquipped());
			AvatarEquipmentCommandFactory commands = new AvatarEquipmentCommandFactory(avatar, facade, queue);
			BackedListElementFactory<Item> factory = new BackedListElementFactory<Item>(commands, facade);
			DynamicElementalList<Item> list = new DynamicElementalList<Item>(source, factory);
			facade.createSubContextMenu(createCloseableList(list));
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
			source.setLabel(new FixedLabelDeterminer(target.getName() + "'s Stats"));
			StatsListElementFactory factory = new StatsListElementFactory(); 
			DynamicElementalList<StatValue> list = new DynamicElementalList<StatValue>(source, factory);
			facade.createSubContextMenu(createCloseableList(list));
		}
	}
	
	private class CreateAbilitiesMenuCommand extends CreateContextMenuCommand {
		private Instance target;
		private CommandQueue queue;
		
		public CreateAbilitiesMenuCommand(Instance target, CommandQueue queue) {
			this.target = target;
			this.queue = queue;
		}
		@Override
		public void execute() {
			AbilityListElementSource source = new AbilityListElementSource(avatar.getAbilities(), ActionDeterminer.this);
			ListElementFactory<Ability> factory;
			if (avatar == target) {
				AvatarAbilityCommandFactory commands = new AvatarAbilityCommandFactory(avatar, target, facade, queue);
				factory = new BackedListElementFactory<Ability>(commands, facade);
			}
			else
				factory = new AbilityUseListElementFactory(target, queue);
			DynamicElementalList<Ability> list = new DynamicElementalList<Ability>(source, factory);
			facade.createSubContextMenu(createCloseableList(list));
		}
	}
	
	private class CreateStoreBuyMenuCommand extends CreateContextMenuCommand {

		private Entity<?> target;
		private CommandQueue queue;
		
		public CreateStoreBuyMenuCommand(Entity<?> target, CommandQueue queue) {
			this.target = target;
			this.queue = queue;
		}
		
		public void execute() {
			StoreInstance store = target.getStoreFront().getInstance(avatar);
			LabelDeterminer label = new FixedLabelDeterminer(target.getName() + "'s Store");
			CollectionListElementSource<Item> source = new CollectionListElementSource<Item>(label, store.getItems());
			ShopkeeperInventoryCommandFactory commands = new ShopkeeperInventoryCommandFactory(store, facade, queue);
			BackedListElementFactory<Item> factory = new BackedListElementFactory<Item>(commands, facade);
			DynamicElementalList<Item> list = new DynamicElementalList<Item>(source, factory);
			facade.createSubContextMenu(createCloseableList(list));
		}
		
	}
	
	private class CreateStoreSellMenuCommand extends CreateContextMenuCommand {

		private Entity<?> avatar, target;
		private CommandQueue queue;
		
		public CreateStoreSellMenuCommand(Entity<?> avatar, Entity<?> target, CommandQueue queue) {
			this.avatar = avatar;
			this.target = target;
			this.queue = queue;
		}
		
		public void execute() {
			StoreInstance store = target.getStoreFront().getInstance(avatar);
			LabelDeterminer label = new FixedLabelDeterminer(target.getName() + "'s Store");
			CollectionListElementSource<Item> source = new CollectionListElementSource<Item>(label, avatar.getInventory().getUnequipped());
			ShopkeeperSellCommandFactory commands = new ShopkeeperSellCommandFactory(avatar, target, facade, queue);
			BackedListElementFactory<Item> factory = new BackedListElementFactory<Item>(commands, facade);
			DynamicElementalList<Item> list = new DynamicElementalList<Item>(source, factory);
			facade.createSubContextMenu(createCloseableList(list));
		}
		
	}
	
	
	private class MountCommand implements Command {

		private Mount mount;
		private Entity entity;
		
		public MountCommand(Mount mount, Entity avatar) {
			this.mount = mount;
			this.entity = avatar;
		}
		
		public void execute() {
			entity.mount(mount);
			facade.clearContextMenuStack();
		}

		public boolean isFeasible() {
			return true;
		}
		
	}
	
	private class DismountCommand implements Command {

		private Mount mount;
		private Entity entity;
		
		public DismountCommand(Mount mount, Entity avatar) {
			this.mount = mount;
			this.entity = avatar;
		}
		
		public void execute() {
			entity.dismount(mount);
			facade.clearContextMenuStack();
		}

		public boolean isFeasible() {
			return true;
		}
		
	}
	
	private class TalkCommand implements Command {

		private Entity<?> avatar, target;
		
		public TalkCommand(Entity avatar, Entity target) {
			this.avatar = avatar;
			this.target = target;
		}
		
		public void execute() {
			target.speak(avatar);
			
			Collection<Speech> speeches = avatar.getSpeech();
			
			if(!speeches.isEmpty()) {
				
				for(Speech speech : speeches) {
					facade.createDialogMenu(speech.getSpeech(), avatar, target);
				break;
				}
			}
		}

		public boolean isFeasible() {
			return true;
		}
		
	}

	public Instance getTarget()
	{
		return targetSource.getTarget();
	}
}
