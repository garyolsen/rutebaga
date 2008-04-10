package rutebaga.controller;

/**
 * @author may
 *
 * A CommandQueue maintains a queue of commands that may be executed at a later
 * time.  Commands that manipulate the model should always be queued so they can
 * be executed in sequential order at an appropriate time, in order to avoid
 * race conditions.
 * 
 * @see QueueCommand
 */
public interface CommandQueue {
	public void queueCommand(Command command);
}
