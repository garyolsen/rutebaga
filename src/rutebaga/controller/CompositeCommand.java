package rutebaga.controller;

import rutebaga.commons.Rule;
import java.util.HashSet;
import java.util.Stack;
import java.util.Iterator;

/**
 * 
 * CompositeCommand is an implementation of {@link Command} that contains a collection
 * of Commands which may all be executed “simultaneously”. Specifically, when
 * CompositeCommand's {@link #execute()} operation is invoked, it invokes execute on all
 * Commands that have been added to it (in no guaranteed order).
 * 
 * Clients may also specify a {@link rutebaga.commons.Rule}, parameterized by Iterator&lt;Command&gt;, to
 * specify how the CompositeCommand should respond to {@link #isFeasible()} queries. The
 * default feasibility Rule returns true if and only if the set of Commands is
 * non-empty and all composited Commands' isFeasible operations return true.
 * 
 * CompositeCommand has "set-semantics"--you may add a unique Command to it only
 * once and there can be no duplicates. If you want a CompositeCommand that
 * executes a given Command more than once, implement it yourself.
 * @author may
 * @see rutebaga.commons.Rule
 * @see Command
 */
public class CompositeCommand implements Command {

	/**
	 * A private shared copy of the default feasibility Rule, which only returns
	 * true for {@link #isFeasible()} if this CompositeCommand's set of {@link Command}s is
	 * non-empty and all its Commands return true for {@link #isFeasible()}.
	 */
	private static final Rule<Iterator<Command>> defaultFeasibilityRule = new DefaultFeasibilityRule();

	/**
	 * The feasibility {@link Rule} used by this CompositeCommand.
	 */
	private Rule<Iterator<Command>> rule;

	/**
	 * The HashSet of Commands used by this CompositeCommand.
	 */
	private final HashSet<Command> commands;

	/**
	 * Create an empty CompositeCommand with the default feasibility {@link rutebaga.commons.Rule}.
	 * @see rutebaga.commons.Rule
	 */
	public CompositeCommand() {
		this(null);
	}

	/**
	 * Create an empty CompositeCommand with the specified feasibility {@link rutebaga.commons.Rule}.
	 * @see rutebaga.commons.Rule
	 */
	public CompositeCommand(Rule<Iterator<Command>> rule) {
		setFeasibilityRule(rule);
		commands = new HashSet<Command>();
	}

	/**
	 * Ensures that this CompositeCommand contains the specified Command.
	 * Equivalently, adds the specified Command from this CompositeCommand, if
	 * it has not previously been added.
	 * 
	 * @param command
	 *            the Command to add
	 */
	public void add(Command command) {
		commands.add(command);
	}

	/**
	 * Ensures that this CompositeCommand does not contain the specified
	 * Command. Equivalently, removes the specified Command from this
	 * CompositeCommand, if it was previously added.
	 * 
	 * @param command
	 *            the Command to remove
	 */
	public void remove(Command command) {
		commands.remove(command);
	}

	/**
	 * Changes the Rule used to determine the feasibility of this
	 * CompositeCommand.
	 * 
	 * @param rule
	 */
	public void setFeasibilityRule(Rule<Iterator<Command>> rule) {
		this.rule = rule == null ? defaultFeasibilityRule : rule;
	}

	/**
	 * Determines whether it is feasible to execute this CompositeCommand, using
	 * this CompositeCommand's Rule for determining feasibility.
	 * 
	 * @see CompositeCommand#setFeasibilityRule(Rule)
	 */
	public boolean isFeasible() {
		return rule.determine(commands.iterator());
	}

	/**
	 * Executes all the Commands contained by this CompositeCommand in no
	 * particular order. It is safe to add or remove Commands from this
	 * CompositeCommand while it is executing, but changes will not affect an
	 * execution already in progress.
	 * 
	 * A Command is only executed if its isFeasible method returns true.
	 * 
	 * (Consider this example: execute() is running on a CompositeCommand. One
	 * of the executed Commands removes another Command from this
	 * CompositeCommand. The removed command will still be executed by this
	 * invocation of execute(). However, a second invocation of execute() on
	 * this CompositeCommand which occurs after the Command is removed but
	 * before the first execution of this CompositeCommand's execute() completes
	 * will not execute the removed Command.)
	 */
	public void execute() {
		// Iterate over an array so it's OK to add/remove commands while
		// executing
		for (Command command : commands.toArray(new Command[commands.size()]))
			if (command.isFeasible())
				command.execute();
	}

	private static class DefaultFeasibilityRule implements
			Rule<Iterator<Command>> {
		/**
		 * The default feasibility Rule returns true if and only if the set of
		 * Commands is non-empty and all composited Commands' isFeasible
		 * operations return true.
		 * 
		 * @param commands
		 * @return true if there are Commands and all are feasible
		 */
		public boolean determine(Iterator<Command> commands) {
			boolean isFeasible = commands.hasNext();
			while (isFeasible) {
				Command command = commands.next();
				isFeasible = isFeasible && command.isFeasible();
			}
			return isFeasible;
		}
	}
}
