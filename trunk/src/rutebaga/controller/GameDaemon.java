package rutebaga.controller;

import java.util.Queue;
import java.util.LinkedList;


public class GameDaemon implements CommandQueue, InterpreterStack {

	private Queue<Command> commandQueue;
	
	public GameDaemon() {
		commandQueue = new LinkedList<Command>();
	}
	
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
