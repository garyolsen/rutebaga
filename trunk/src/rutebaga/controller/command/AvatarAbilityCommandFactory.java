package rutebaga.controller.command;

import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.model.entity.Ability;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.Instance;
import rutebaga.view.UserInterfaceFacade;

public class AvatarAbilityCommandFactory implements CommandFactory<Ability> {

	private UserInterfaceFacade facade;
	private Entity<?> avatar;
	private Instance<?> target;
	private CommandQueue queue;
	
	public AvatarAbilityCommandFactory(Entity<?> avatar, Instance<?> target,
			UserInterfaceFacade facade, CommandQueue queue) {

		this.avatar = avatar;
		this.target = target;
		this.facade = facade;
		this.queue = queue;
	}


	public ElementalList getCommandListFor(Ability element) {
		ConcreteElementalList list = new ConcreteElementalList();
		list.add(element.getName(), QueueCommand.makeForQueue(new AbilityCommand<Instance>(element, target), queue));
		return list;
	}

	
	private class AbilityCommand<T> implements Command {

		private Ability<T> ability;
		private T target;
		
		public AbilityCommand(Ability<T> ability, T target ) {
			this.ability = ability;
			this.target = target;
		}
		
		public void execute() {
			ability.act(target);
		}

		public boolean isFeasible() {
			return ability.isFeasible();
		}
	}
	

}
