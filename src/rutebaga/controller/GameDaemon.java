package rutebaga.controller;

import java.util.Queue;
import java.util.LinkedList;

/**
 * 
 * The GameDaemon manages {@link UserActionInterpreter UserActionInterpreters},
 * controlling when they are active. This class also controls when actions
 * occur, including processing {@link Command Commands} and rendering the View.
 * The timing mechanism is delegated to a TimeManager component.
 * 
 * The GameDaemon maintains a stack of
 * {@link UserActionInterpreter UserActionInterpreters}, where the top
 * interpreter receives tick notifications, and has the option of allowing ticks
 * to "bubble down" the stack by asserting eventsFallThrough. When activated,
 * interpreters are given references to the GameDaemon and
 * {@link rutebaga.view.ViewFacade ViewFacade}.
 * 
 * The GameDaemon also maintains a queue of {@link Command Commands}. Commands that manipulate
 * the model should always be queued so they can be executed in sequential order
 * at an appropriate time.
 * 
 * @see Command
 * @see rutebaga.view.ViewFacad
 * @author may
 * @see UserActionInterpreter
 */
public class GameDaemon implements CommandQueue, InterpreterStack {

	private Queue<Command> commandQueue;

	/**
	 * Constructs a new GameDaemon.
	 */
	public GameDaemon() {
		commandQueue = new LinkedList<Command>();
	}

	/**
	 * Allows clients to automatically queue a {@link Command}.
	 * @param command The {@link Command} to queue.
	 * @see rutebaga.controller.CommandQueue#queueCommand(rutebaga.controller.Command)
	 * @see Command
	 */
	public void queueCommand(Command command) {
		commandQueue.offer(command);
	}

	protected void processCommandQueue() {
		while (!commandQueue.isEmpty()) {
			Command command = commandQueue.poll();
			command.execute();
		}
	}
}
