package rutebaga.model.environment;

import java.util.LinkedList;
import java.util.List;

public class MovementEventQueue
{
	private List<MovementEvent> events = new LinkedList<MovementEvent>();

	public void onMovement(MovementEvent event)
	{
		this.events.add(event);
	}

	public List<MovementEvent> poll()
	{
		List<MovementEvent> rval = events;
		events = new LinkedList<MovementEvent>();
		return rval;
	}
}
