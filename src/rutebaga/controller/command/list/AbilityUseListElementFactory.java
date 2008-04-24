package rutebaga.controller.command.list;

import rutebaga.controller.command.AbilityUseCommand;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.CommandQueue;
import rutebaga.controller.command.QueueCommand;
import rutebaga.model.entity.Ability;
import rutebaga.model.environment.Instance;

public class AbilityUseListElementFactory implements
		ListElementFactory<Ability>
{

	private Instance<?> target;
	private CommandQueue queue;

	public AbilityUseListElementFactory(Instance<?> target, CommandQueue queue)
	{
		this.target = target;
		this.queue = queue;
	}

	public ListElement makeElement(final Ability element)
	{
		return new ListElement()
		{
			public Command getCommand()
			{
				if (!element.isFeasible())
					return null;
				else
					return QueueCommand.makeForQueue(
							new AbilityUseCommand<Instance>(element, target),
							queue);
			}

			public String getLabel()
			{
				return "Use '" + element.getName() + '\'';
			}
		};
	}
}
