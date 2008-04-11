package rutebaga.controller.command;

/**
 * 
 * QueueCommand is a convenience implementation of {@link Command} that queues another
 * Command on the CommandQueue when its {@link #execute()} is invoked. This
 * component Command, as well as the CommandQueue to enqueue the command in,
 * must be specified when the QueueCommand is created. QueueCommand instances
 * are immutable. {@link #isFeasible()} forwards to the component Command.
 * 
 * (The CommandQueue is expected to execute the component Command at a later
 * time, but QueueCommand does not guarantee that its component Command's
 * execute operation will ever be invoked.)
 * @author may
 * @see Command
 */
public class QueueCommand implements Command {

	private final CommandQueue queue;

	private final Command command;

	/**
	 * Create a new QueueCommand that will queue the specified {@link Command} on the
	 * given CommandQueue when this QueueCommand is executed.
	 * @see Command
	 * 
	 * @param queue
	 *            the CommandQueue to queue on
	 * @param command
	 *            the {@link Command} to queue when this QueueCommand is executed
	 */
	private QueueCommand(CommandQueue queue, Command command) {
		if (queue == null || command == null)
			throw new NullPointerException(
					"Parameters to QueueCommand's constructor cannot be null");
		this.queue = queue;
		this.command = command;
	}

	/**
	 * Create a new QueueCommand that will queue the specified {@link Command} on the
	 * given CommandQueue when this QueueCommand is executed.
	 * 
	 * @param command
	 *            the CommandQueue to queue on
	 * @param queue
	 *            the CommandQueue to queue on
	 * @return a QueueCommand that will place the {@link Command} on the Queue when
	 *         executed
	 *         @see Command
	 */
	public static QueueCommand makeForQueue(Command command, CommandQueue queue) {
		return new QueueCommand(queue, command);
	}

	/**
	 * Queues this QueueCommand's component {@link Command} on the CommandQueue this
	 * QueueCommand was initialized with.
	 * @see Command
	 */
	public void execute() {
		queue.queueCommand(command);
	}

	/**
	 * Returns whether this QueueCommand's delayed {@link Command} is feasible at this
	 * time or not.
	 * @see Command
	 * 
	 * @return the feasibility status of this QueueCommand's component {@link Command}
	 */
	public boolean isFeasible() {
		return command.isFeasible();
	}
}
