package rutebaga.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Vector;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.CommandQueue;
import rutebaga.controller.keyboard.KeyBuffer;
import rutebaga.view.ViewFacade;
import rutebaga.view.rwt.TextFieldListener;

/**
 * 
 * The GameDaemon manages {@link UserActionInterpreter UserActionInterpreters},
 * controlling when they are active. This class also controls when actions
 * occur, including processing {@link Command Commands} and rendering the View.
 * The timing mechanism is delegated to a TimeDaemon component.
 * 
 * The GameDaemon maintains a stack of
 * {@link UserActionInterpreter UserActionInterpreters}, where the top
 * interpreter receives tick notifications, and has the option of allowing ticks
 * to "bubble down" the stack by asserting eventsFallThrough. When activated,
 * interpreters are given references to the GameDaemon and
 * {@link rutebaga.view.ViewFacade ViewFacade}.
 * 
 * The GameDaemon also maintains a queue of {@link Command Commands}. Commands
 * that manipulate the model should always be queued so they can be executed in
 * sequential order at an appropriate time.
 * 
 * @see Command
 * @see rutebaga.view.ViewFacade
 * @author Matthew Chuah
 * @see UserActionInterpreter
 */
public class GameDaemon implements InterpreterManager {

	/**
	 * The GameDaemon retains a reference to the ViewFacade to pass it on to
	 * UserActionInterpreters.
	 */
	private ViewFacade view;

	/**
	 * An implementation of a stack specifically for interpreters.
	 */
	private InterpreterStack interpreterStack;

	/**
	 * The command queue.
	 */
	private CommandQueueImpl commandQueue;

	/**
	 * Determine whether all broadcasted events should automatically go to the
	 * queue or be executed immediately.
	 */
	private boolean eventsAreQueued;

	/**
	 * The daemon that sends tick events to this Daemon.
	 */
	private TickDaemon ticker;

	/**
	 * 
	 */
	private KeyBuffer keyBuffer;

	/**
	 * 
	 */
	private Game game;

	/**
	 * Constructs a new GameDaemon.
	 */
	private GameDaemon(ViewFacade view, Game game, boolean queued) {
		if (view == null)
			throw new NullPointerException();
		this.view = view;
		this.game = game;
		this.commandQueue = new CommandQueueImpl();
		this.interpreterStack = new InterpreterStack();
		this.eventsAreQueued = queued;
		this.ticker = new DefaultTickDaemon(30);
		final KeyListener keyProcessor = new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				processEvent(new EventCommand<KeyListener>() {
					@Override
					KeyListener extract(InterpreterStackRecord isr) {
						return isr.keyListener;
					}

					@Override
					void act(KeyListener kl) {
						kl.keyPressed(e);
					}
				}, false);
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				processEvent(new EventCommand<KeyListener>() {
					@Override
					KeyListener extract(InterpreterStackRecord isr) {
						return isr.keyListener;
					}

					@Override
					void act(KeyListener kl) {
						kl.keyReleased(e);
					}
				}, false);
			}
		};

		final MouseListener mouseProcessor = new MouseAdapter() {
			public void mouseClicked(final MouseEvent e) {
				processEvent(new EventCommand<MouseListener>() {
					@Override
					MouseListener extract(InterpreterStackRecord isr) {
						return isr.mouseListener;
					}

					@Override
					void act(MouseListener uai) {
						uai.mouseClicked(e);
					}
				});
			}
		};

		this.keyBuffer = new KeyBuffer();
		view.addKeyListener(this.keyBuffer);
		view.addMouseListener(mouseProcessor);
		this.ticker.setListener(new TickListener() {
			public void tick() {
				GameDaemon.this.keyBuffer.poll(keyProcessor);
				GameDaemon.this.commandQueue.processQueue();
				GameDaemon.this.tick();
				GameDaemon.this.view.renderFrame();
			}
		});
		/*
		 * Connascence of position/timing. The ticker must not be unpaused until
		 * all the GameDaemon instance fields are initialized.
		 */
		this.ticker.unpause();
	}
	
	/**
	 * Prepares and returns a reference to a new GameDaemon that does not queue
	 * events.
	 * 
	 * @return a GameDaemon
	 */
	public static GameDaemon initialize(Game game) {
		return initialize(game, false);
	}

	/**
	 * Prepares and returns a reference to a new GameDaemon with the specified
	 * handling of events.
	 * 
	 * @param queueEvents
	 *            whether or not to queue events
	 * @return a GameDaemon
	 */
	public static GameDaemon initialize(Game game, boolean queueEvents) {
		ViewFacade facade = new ViewFacade();
		facade.constructFullscreenView();
		return new GameDaemon(facade, game, queueEvents);
	}

	/**
	 * Gets the CommandQueue this GameDaemon is using.
	 * 
	 * @return this GameDaemon's command queue
	 */
	public CommandQueue getCommandQueue() {
		return commandQueue;
	}

	/**
	 * Activates the specified UserActionInterpreter, pushing it on to the top
	 * of this GameDaemon's interpreter stack.
	 * 
	 * @see rutebaga.controller.InterpreterManager#activate(rutebaga.controller.UserActionInterpreter)
	 */
	public void activate(UserActionInterpreter uai) {
		interpreterStack.push(new InterpreterStackRecord(uai));
		uai.installActionInterpreter(this, game, view);
	}

	/**
	 * Deactivates the specified UserActionInterpreter, removing it, as well as
	 * all Interpreters activated after this one, from the GameDaemon's
	 * interpreter stack. If the specified UserActionInterpreter is not in this
	 * GameDaemon's interpreter stack, no interpreters will be removed.
	 * 
	 * @see rutebaga.controller.InterpreterManager#deactivate(rutebaga.controller.UserActionInterpreter)
	 */
	public void deactivate(UserActionInterpreter uai) {
		// Remove uai from the stack along with all interpreters above it
		if (interpreterStack.contains(uai)) {
			boolean foundRoot = false;
			while (!interpreterStack.isEmpty()) {
				UserActionInterpreter i = interpreterStack.pop().interpreter;
				i.uninstallActionInterpreter();
				if (!i.eventsFallThrough())
					foundRoot = true;
				if (i.equals(uai))
					break;
			}
			/*
			 * If uai is a 'root' interpreter, or we popped a root interpreter
			 * above, we need to reactivate the interpreters that are beneath it
			 * in the stack.
			 */
			if (foundRoot)
				for (InterpreterStackRecord isr : interpreterStack) {
					isr.interpreter.reactivateActionInterpreter();
					if (!isr.interpreter.eventsFallThrough())
						break; // Stop at the next root interpreter
				}
		}
	}

	/**
	 * Broadcast a tick to the UserActionInterpreters that this GameDaemon is
	 * aware of. The UserActionInterpreter on the top of the stack receives the
	 * tick first. For each UserActionInterpreter ticked, if it asserts
	 * eventsFallThrough, the tick will continue to the next
	 * UserActionInterpreter. Otherwise, if a UserActionInterpreter does not
	 * assert eventsFallThrough, it will be the last interpreter in the stack to
	 * receive the tick.
	 */
	public void tick() {
		processEvent(new EventCommand<UserActionInterpreter>() {
			@Override
			UserActionInterpreter extract(InterpreterStackRecord isr) {
				return isr.interpreter;
			}

			@Override
			void act(UserActionInterpreter uai) {
				uai.tick();
			}
		});
	}

	/**
	 * A private operation which either queues a command or immediately
	 * processes it.
	 * 
	 * @param command
	 *            the command to process
	 */
	private void processEvent(Command command, boolean eventsAreQueued) {
		if (eventsAreQueued)
			commandQueue.queueCommand(command);
		else
			command.execute();
	}

	/**
	 * A private operation which either queues a command or immediately
	 * processes it, depending on this GameDaemon's eventsAreQueued member.
	 */
	private void processEvent(Command command) {
		processEvent(command, eventsAreQueued);
	}

	/**
	 * EventCommand is a private convenience class. When executed, they iterate
	 * over this GameDaemon's interpreter stack, performing the act operation on
	 * each UserActionInterpreter. The EventCommand class is meant to allow
	 * GameDaemon operations to distribute an event to the
	 * UserActionInterpreters in the stack, until an interpreter with
	 * eventsFallThrough false is reached. EventCommands are always feasible.
	 * 
	 * @author Matty
	 */
	private abstract class EventCommand<E> implements Command {
		/**
		 * Specifies whether is is feasible to execute this Command.
		 * EventCommands are always feasible.
		 * 
		 * @see rutebaga.controller.command.Command#isFeasible()
		 */
		public boolean isFeasible() {
			return true;
		}

		/**
		 * Distribute an event to the UserActionInterpreters in the enclosing
		 * GameDaemon's interpreter stack.
		 * 
		 * @see rutebaga.controller.command.Command#execute()
		 */
		public void execute() {
			for (InterpreterStackRecord isr : interpreterStack) {
				E e = extract(isr);
				if (e != null)
					act(e);
				if (!isr.interpreter.eventsFallThrough())
					break;
			}
		}

		/**
		 * Performs some operation on the specified UserActionInterpreter.
		 * Called by the execute operation on each UserActionInterpreter on the
		 * enclosing GameDaemon's interpreter stack.
		 * 
		 * @param uai
		 *            a UserActionInterpreter in the GameDaemon's stack
		 */
		abstract void act(E uai);

		abstract E extract(InterpreterStackRecord isr);
	}

	private static class InterpreterStackRecord {
		UserActionInterpreter interpreter;
		KeyListener keyListener;
		TextFieldListener textFieldListener;
		MouseListener mouseListener;

		InterpreterStackRecord(UserActionInterpreter uai) {
			interpreter = uai;
		}
	}

	/**
	 * A private implementation of a stack designed for use with
	 * UserActionInterpreters. The stack-ness is weakly enforced, as the stack
	 * provides both a contains operation and an iterator.
	 * 
	 * @author Matty
	 */
	private static class InterpreterStack implements
			Iterable<InterpreterStackRecord> {
		private Vector<InterpreterStackRecord> stack = new Vector<InterpreterStackRecord>();

		private int modCount = 0;

		public void push(InterpreterStackRecord uai) {
			stack.add(uai);
		}

		public InterpreterStackRecord pop() {
			return stack.remove(stack.size() - 1);
		}

		public InterpreterStackRecord peek() {
			return stack.get(stack.size() - 1);
		}

		public boolean isEmpty() {
			return stack.isEmpty();
		}

		public boolean contains(UserActionInterpreter uai) {
			for (InterpreterStackRecord isr: this)
				if (uai.equals(isr.interpreter))
					return true;
			return false;
		}
		
		public Iterator<InterpreterStackRecord> iterator() {
			return new Iterator<InterpreterStackRecord>() {
				int cursor = stack.size();

				int expectedModCount = modCount;

				public boolean hasNext() {
					return cursor != 0;
				}

				public InterpreterStackRecord next() {
					try {
						checkForComodification();
						return stack.get(--cursor);
					} catch (IndexOutOfBoundsException e) {
						checkForComodification();
						throw new NoSuchElementException();
					}
				}

				public void remove() throws UnsupportedOperationException {
					throw new UnsupportedOperationException();
				}

				private void checkForComodification() {
					if (modCount != expectedModCount)
						throw new ConcurrentModificationException();
				}
			};
		}
	}

	private static class CommandQueueImpl implements CommandQueue {
		/**
		 * The command queue.
		 */
		private Queue<Command> queue;

		public CommandQueueImpl() {
			queue = new LinkedList<Command>();
		}

		/**
		 * Allows clients to automatically queue a
		 * {@link rutebaga.controller.command.Command}.
		 * 
		 * @param command
		 *            The Command to queue.
		 * @see rutebaga.controller.CommandQueue#queueCommand(rutebaga.controller.Command)
		 */
		public void queueCommand(Command command) {
			queue.offer(command);
		}

		/**
		 * Iterates through the command queue, removing all commands and
		 * executing them.
		 */
		public void processQueue() {
			while (!queue.isEmpty()) {
				Command command = queue.poll();
				command.execute();
			}
		}
	}

	private InterpreterStackRecord findInterpreterStackRecord(Object obj) {
		for (InterpreterStackRecord isr : interpreterStack)
			if (isr.interpreter == obj)
				return isr;
		return null;
	}

	public void registerAsKeyListener(KeyListener uai) {
		InterpreterStackRecord isr = findInterpreterStackRecord(uai);
		if (isr != null)
			isr.keyListener = uai;
	}

	public void registerAsTextFieldListener(TextFieldListener tfl) {
		InterpreterStackRecord isr = findInterpreterStackRecord(tfl);
		if (isr != null)
			isr.textFieldListener = tfl;
	}

	public void registerAsMouseListener(MouseListener ml) {
		InterpreterStackRecord isr = findInterpreterStackRecord(ml);
		if (isr != null)
			isr.mouseListener = ml;
	}
}
