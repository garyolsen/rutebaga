package rutebaga.controller.command;

/**
 * 
 * A CommandQueue maintains a queue of {@link Command}s that may be executed at
 * a later time. Commands that manipulate the model should always be queued so
 * they can be executed in sequential order at an appropriate time, in order to
 * avoid race conditions.
 * 
 * @author Matthew Chuah
 * @see QueueCommand
 * @see Command
 */
public interface CommandQueue
{
	/**
	 * Add a {@link Command} to this CommandQueue to be executed at a later
	 * time.
	 * 
	 * @param command
	 *            the {@link Command} to add to this queue
	 * @see Command
	 */
	public void queueCommand(Command command);
}
