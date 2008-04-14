package rutebaga.controller;

import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import rutebaga.view.ViewFacade;

/**
 * 
 * UserActionInterpreters are the core of the Controller subsystem. Interpreters
 * receive user input from the View, and feed {@link rutebaga.controller.command.Command Commands} to the
 * View to be displayed to the user. Interpreters are components of the
 * {@link GameDaemon}. When the daemon activates a UserActionInterpreter, it
 * calls {@link #installActionInterpreter(GameDaemon, ViewFacade)}on the
 * interpreter. The {@link #uninstallActionInterpreter()} operation is invoked
 * when an interpreter is deactivated.
 * 
 * UserActionInterpreters must offer a {@link #tick()} method, which will be
 * invoked by the GameDaemon whenever the interpreter is active. The
 * eventsFallThrough attribute signifies to the daemon whether or not this
 * interpreter is consuming ticks. If eventsFallThrough is false, when this
 * interpreter receives a tick invocation, it should be the final interpreter to
 * receive that notification during that tick. When eventsFallThrough is true,
 * the daemon is free to notify other active interpreters of the tick after
 * notifying this one.
 * 
 * All interpreters may also accept {@link java.awt.event.KeyEvent KeyEvents}
 * from the View, which will push these events to the Controller if they are not
 * consumed by the user interface. This allows interpreters to implement
 * functionality such as key-based movement during game play. KeyEvents "bubble
 * down" in the same manner that ticks do when eventsFallThrough is false,
 * KeyEvents are consumed.
 * 
 * UserActionInterpreters should also accept ActionEvents, which may come from
 * the View or Controller. This allows interpreters to implement functionality
 * such as click-based actions during game play. ActionEvents "bubble down" in
 * the same manner that KeyEvents do.
 * 
 * @see rutebaga.controller.command.Command
 * @see GameDaemon
 * @see java.awt.event.KeyEvent
 * @author may
 */
public interface UserActionInterpreter extends KeyListener, ActionListener {

	/**
	 * Signifies whether events should "fall through" this UserActionInterpreter
	 * on the {@link GameDaemon} stack. That is, eventsFallThrough determines
	 * whether, when this interpreter receives an event (such as a tick or key
	 * press), it should be the last interpreter to be notified of the event
	 * (i.e., the {@link GameDaemon} should not pass it down the stack).
	 * 
	 * @see GameDaemon
	 * @return false if this interpreter consumes events
	 */
	public boolean eventsFallThrough();

	/**
	 * Notifies this interpreter of a game tick. Interpreters may deal with tick
	 * notifications as they see fit.
	 */
	public void tick();

	/**
	 * Activates this interpreter, giving it a reference to the daemon and view.
	 * The {@link GameDaemon} typically invokes this operation when activating
	 * an interpreter in the controller. Calling installActionInterpreter is a
	 * precondition for invoking other methods on this interpreter.
	 * 
	 * @see GameDaemon
	 * @param daemon
	 *            a reference to the active GameDaemon
	 * @param facade
	 *            a reference to the active ViewFacade
	 */
	public void installActionInterpreter(GameDaemon daemon, ViewFacade facade);

	/**
	 * Deactivates this interpreter. This operation may only be invoked once
	 * after each time installActionInterpreter is invoked, otherwise behavior
	 * is undefined.
	 */
	public void uninstallActionInterpreter();

}
