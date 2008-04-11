package rutebaga.controller;

/**
 * TODO: resolve all the references here once the referenced classes actually exist
 * Given a set of Instances, ActionDeterminer produces a list of named Commands
 * in the form of an {@link ElementalList}. ActionDeterminer exposes a single operation,
 * {@link #determineActions()}, which uses a {@link Filter} to determine which {@link Instance} in a set of
 * Instances should be targeted. Once it determines the target, it determines
 * which actions are appropriate for the targeted Instance.
 * 
 * For instance, if an {@link Entity} is present at a {@link Location}, it will be targeted and
 * {@link Command}s encapsulating actions appropriate for an Entity, such as attacking
 * or talking, will be generated.
 * @author may
 * @see ElementalList
 * @see Filter
 * @see Instance
 * @see Command
 */
public class ActionDeterminer {
	/*
	 * TODO: Implement this class.
	 */
}
