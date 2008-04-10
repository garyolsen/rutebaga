package rutebaga.controller;

/**
 * @author may
 * 
 * QueueCommand is a convenience implementation of Command that queues another
 * Command on the CommandQueue when its execute operation is invoked. This
 * component Command, as well as the CommandQueue to enqueue the command in,
 * must be specified when the QueueCommand is created. QueueCommand instances
 * are immutable. The isFeasible operation forwards to the component Command.
 *
 * (The CommandQueue is expected to execute the component Command at a later
 * time, but QueueCommand does not guarantee that its component Command's
 * execute operation will ever be invoked.)
 */
public class QueueCommand implements Command {

	private final CommandQueue queue;
	private final Command command;
	
	/**
	 * Create a new QueueCommand that will queue the specified Command on the
	 * given CommandQueue when this QueueCommand is executed.
	 * @param queue the CommandQueue to queue on
	 * @param command the Command to queue when this QueueCommand is executed
	 */
	private QueueCommand(CommandQueue queue, Command command) {
		if (queue == null || command == null)
			throw new NullPointerException("Parameters to QueueCommand's constructor cannot be null");
		this.queue = queue;
		this.command = command;
	}
	
	/**
	 * Create a new QueueCommand that will queue the specified Command on the
	 * given CommandQueue when this QueueCommand is executed.
	 * 
	 * @param command the CommandQueue to queue on
	 * @param queue the CommandQueue to queue on
	 * @return a QueueCommand that will place the Command on the Queue when executed 
	 */
	public static QueueCommand makeForQueue(Command command, CommandQueue queue) {
		return new QueueCommand(queue, command);
	}
	
	/**
	 * Queues this QueueCommand's component Command on the CommandQueue this
	 * QueueCommand was initialized with.
	 */
	public void execute() {
		queue.queueCommand(command);
	}

	/**
	 * Returns whether this QueueCommand's delayed Command is feasible at this
	 * time or not.
	 * @return the feasibility status of this QueueCommand's component Command
	 */
	public boolean isFeasible() {
		return command.isFeasible();
	}
}
