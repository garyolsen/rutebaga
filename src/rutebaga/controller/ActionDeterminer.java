package rutebaga.controller;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.ElementalList;
import rutebaga.model.environment.InstanceSet;

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
		return null;
	}
}
