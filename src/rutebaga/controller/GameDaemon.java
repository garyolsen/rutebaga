package rutebaga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Vector;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.CommandQueue;
import rutebaga.view.ViewFacade;

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
 * The GameDaemon also maintains a queue of {@link Command Commands}. Commands
 * that manipulate the model should always be queued so they can be executed in
 * sequential order at an appropriate time.
 * 
 * @see Command
 * @see rutebaga.view.ViewFacade
 * @author may
 * @see UserActionInterpreter
 */
public class GameDaemon extends KeyAdapter implements CommandQueue,
		InterpreterManager
{

	private ViewFacade facade;

	private InterpreterStack interpreterStack;

	private Queue<Command> commandQueue;

	private boolean eventsAreQueued;

	/**
	 * Constructs a new GameDaemon.
	 */
	private GameDaemon(ViewFacade view, boolean queued)
	{
		if (view == null)
			throw new NullPointerException();
		facade = view;
		commandQueue = new LinkedList<Command>();
		interpreterStack = new InterpreterStack();
		eventsAreQueued = queued;
	}

	public static GameDaemon initialize()
	{
		return initialize(false);
	}

	public static GameDaemon initialize(boolean queueEvents)
	{
		ViewFacade facade = new ViewFacade();
		return new GameDaemon(facade, queueEvents);
	}

	/**
	 * Allows clients to automatically queue a
	 * {@link rutebaga.controller.command.Command}.
	 * 
	 * @param command
	 *            The Command to queue.
	 * @see rutebaga.controller.CommandQueue#queueCommand(rutebaga.controller.Command)
	 */
	public void queueCommand(Command command)
	{
		commandQueue.offer(command);
	}

	protected void processCommandQueue()
	{
		while (!commandQueue.isEmpty())
		{
			Command command = commandQueue.poll();
			command.execute();
		}
	}

	public void activate(UserActionInterpreter uai)
	{
		uai.installActionInterpreter(this, facade);
		interpreterStack.push(uai);
	}

	public void deactivate(UserActionInterpreter uai)
	{
		// Remove uai from the stack along with all interpreters above it
		while (!interpreterStack.isEmpty())
		{
			UserActionInterpreter i = interpreterStack.pop();
			i.uninstallActionInterpreter();
			if (i.equals(uai))
				break;
		}
	}

	@Override
	public void keyPressed(final KeyEvent e)
	{
		processEvent(new EventCommand()
		{
			@Override
			public void act(UserActionInterpreter uai)
			{
				uai.keyPressed(e);
			}
		});
	}

	public void actionPerformed(final ActionEvent e)
	{
		processEvent(new EventCommand()
		{
			@Override
			public void act(UserActionInterpreter uai)
			{
				uai.actionPerformed(e);
			}
		});
	}

	public void tick()
	{
		processEvent(new EventCommand()
		{
			@Override
			public void act(UserActionInterpreter uai)
			{
				uai.tick();
			}
		});
	}

	private void processEvent(Command command)
	{
		if (eventsAreQueued)
			queueCommand(command);
		else
			command.execute();
	}

	private abstract class EventCommand implements Command
	{
		public boolean isFeasible()
		{
			return true;
		}

		public void execute()
		{
			for (UserActionInterpreter uai : interpreterStack)
			{
				act(uai);
				if (!uai.eventsFallThrough())
					break;
			}
		}

		public abstract void act(UserActionInterpreter uai);
	}

	private class InterpreterStack implements Iterable<UserActionInterpreter>
	{
		private Vector<UserActionInterpreter> stack = new Vector<UserActionInterpreter>();

		private int modCount = 0;

		public void push(UserActionInterpreter uai)
		{
			stack.add(uai);
		}

		public UserActionInterpreter pop()
		{
			return stack.remove(stack.size() - 1);
		}

		public UserActionInterpreter peek()
		{
			return stack.get(stack.size() - 1);
		}

		public boolean isEmpty()
		{
			return stack.isEmpty();
		}

		public Iterator<UserActionInterpreter> iterator()
		{
			return new Iterator<UserActionInterpreter>()
			{
				int cursor = stack.size();

				int expectedModCount = modCount;

				public boolean hasNext()
				{
					return cursor != 0;
				}

				public UserActionInterpreter next()
				{
					try
					{
						checkForComodification();
						return stack.get(--cursor);
					}
					catch (IndexOutOfBoundsException e)
					{
						checkForComodification();
						throw new NoSuchElementException();
					}
				}

				public void remove() throws UnsupportedOperationException
				{
					throw new UnsupportedOperationException();
				}

				private void checkForComodification()
				{
					if (modCount != expectedModCount)
						throw new ConcurrentModificationException();
				}
			};
		}
	}
}
