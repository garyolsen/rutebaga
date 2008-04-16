package rutebaga.controller;

import java.util.Set;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.item.Item;
import rutebaga.model.map.Tile;

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
	/**
	 * @param instances
	 * @return
	 */
	public ElementalList determineActions(InstanceSet instances) {
		Set<Entity> entities = instances.getEntities();
		if (entities.size() != 0)
			return actionsForEntity(entities.iterator().next());
		Set<Item> items = instances.getItems();
		if (items.size() != 0)
			return actionsForItem(items.iterator().next());
		Set<Tile> tiles = instances.getTiles();
		if (tiles.size() != 0)
			return actionsForTile(tiles.iterator().next());
		return null;
	}
	
	private ElementalList actionsForEntity(Entity entity) {
		return null;
	}

	private ElementalList actionsForItem(Item item) {
		return null;
	}
	
	private ElementalList actionsForTile(Tile tile) {
		return null;
	}
	
}
