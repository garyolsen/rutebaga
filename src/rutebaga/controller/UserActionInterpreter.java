package rutebaga.controller;

import java.awt.event.KeyListener;
import rutebaga.view.ViewFacade;

/**
 * @author may
 *
 * UserActionInterpreters are the core of the Controller subsystem. Interpreters receive
 * user input from the View, and feed Commands to the View to be displayed to the user.
 * Interpreters are components of the GameDaemon. When the daemon activates a
 * UserActionInterpreter, it calls installActionInterpreter on the interpreter. The
 * uninstallActionIterpreter operation is invoked when an interpreter is deactivated.
 *
 * UserActionInterpreters must offer a tick method, which will be invoked by the
 * GameDaemon whenever the interpreter is active. The eventsFallThrough attribute
 * signifies to the daemon whether or not this interpreter is consuming ticks. If
 * eventsFallThrough is false, when this interpreter receives a tick invocation, it
 * should be the final interpreter to receive that notification during that tick. When
 * eventsFallThrough is true, the daemon is free to notify other active interpreters
 * of the tick after notifying this one.
 *
 * All interpreters may also accept KeyEvents from the View, which will push these
 * events to the Controller if they are not consumed by the user interface. This allows
 * interpreters to implement functionality such as key-based movement during game play.
 * KeyEvents "bubble down" in the same manner that ticks do—when eventsFallThrough is
 * false, KeyEvents are consumed.
 */
public interface UserActionInterpreter extends KeyListener {

	/**
	 * Signifies whether events should "fall through" this UserActionInterpreter on the
	 * GameDaemon stack.  That is, eventsFallThrough determines whether, when this
	 * interpreter receives an event (such as a tick or key press), it should be the last
	 * interpreter to be notified of the event (i.e., the GameDaemon should not pass it
	 * down the stack).  
	 * @return false if this interpreter consumes events
	 */
	public boolean eventsFallThrough();
	
	/**
	 * Notifies this interpreter of a game tick.  Interpreters may deal with tick
	 * notifications as they see fit. 
	 */
	public void tick();
	
	/**
	 * Activates this interpreter, giving it a reference to the daemon and view.
	 * The GameDaemon typically invokes this operation when activating an interpreter
	 * in the controller.  Calling installActionInterpreter is a precondition for
	 * invoking other methods on this interpreter.
	 * @param daemon a reference to the active GameDaemon
	 * @param facade a reference to the active ViewFacade
     */
	public void installActionInterpreter(GameDaemon daemon, ViewFacade facade);

	/**
	 * Deactivates this interpreter.  This operation may only be invoked once after
	 * each time installActionInterpreter is invoked, otherwise behavior is undefined. 
	 */
	public void uninstallActionInterpreter();

}
