package rutebaga.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.Command;
import rutebaga.controller.keyboard.ConcreteKeyBinding;
import rutebaga.controller.keyboard.KeyBinding;
import rutebaga.controller.keyboard.KeyBindingList;
import rutebaga.controller.keyboard.KeyCode;
import rutebaga.model.entity.Entity;

public class MovementBindingSource {

	private KeyBindingList<Command> bindings;
	private Entity entity;
	private Map<Double, KeyBinding<Command>> directions;

	public static MovementBindingSource defaultBindings(Entity entity,
			KeyBindingList<Command> bindings) {
		// TODO: Eliminate need for MOVE_SPEED
		final double MOVE_SPEED = 0.2;
		final Vector2D SOUTH = new Vector2D(MOVE_SPEED / Math.sqrt(2),
				MOVE_SPEED / Math.sqrt(2));
		final Vector2D NORTH = new Vector2D(-MOVE_SPEED / Math.sqrt(2),
				-MOVE_SPEED / Math.sqrt(2));
		final Vector2D SOUTHEAST = new Vector2D(MOVE_SPEED, 0);
		final Vector2D NORTHEAST = new Vector2D(0, -MOVE_SPEED);
		final Vector2D SOUTHWEST = new Vector2D(0, MOVE_SPEED);
		final Vector2D NORTHWEST = new Vector2D(-MOVE_SPEED, 0);

		MovementBindingSource manager = new MovementBindingSource(entity,
				bindings);
		manager.setDirectionBinding(KeyCode.get(KeyEvent.VK_Q), NORTHWEST);
		manager.setDirectionBinding(KeyCode.get(KeyEvent.VK_W), NORTH);
		manager.setDirectionBinding(KeyCode.get(KeyEvent.VK_E), NORTHEAST);
		manager.setDirectionBinding(KeyCode.get(KeyEvent.VK_A), SOUTHWEST);
		manager.setDirectionBinding(KeyCode.get(KeyEvent.VK_S), SOUTH);
		manager.setDirectionBinding(KeyCode.get(KeyEvent.VK_D), SOUTHEAST);

		return manager;
	}

	public MovementBindingSource(Entity entity,
			KeyBindingList<Command> bindings) {
		if (entity == null)
			throw new NullPointerException();
		this.entity = entity;
		this.bindings = bindings;
		this.directions = new HashMap<Double, KeyBinding<Command>>();
	}

	private void removeOldKeyBinding(double angle) {
		KeyBinding<Command> binding = directions.remove(angle);
		if (binding != null)
			bindings.remove(binding);
	}

	public void setDirectionBinding(KeyCode code, Vector2D direction) {
		double angle = direction.getAngle();
		removeOldKeyBinding(angle);
		Command command = new MovementCommand(direction);
		bindings.set(code, command);
		directions.put(angle, new ConcreteKeyBinding<Command>(code, command));
	}

	public Command[] getMovementCommands() {
		Command[] commands = new Command[bindings.size()];
		int pos = 0;
		for (KeyBinding<Command> binding : bindings)
			commands[pos++] = binding.getBinding();
		return commands;
	}

	private class MovementCommand implements Command {
		private Vector2D direction;

		public MovementCommand(Vector2D direction) {
			this.direction = direction;
		}

		public void execute() {
			entity.walk(direction);
		}

		public boolean isFeasible() {
			return true;
		}
	}
}
