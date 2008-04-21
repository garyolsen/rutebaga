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
	
	public AvatarAbilityCommandFactory(Entity<?> avatar, Instance<?> target,
			UserInterfaceFacade facade) {

		this.avatar = avatar;
		this.target = target;
		this.facade = facade;
	}


	public ElementalList getCommandListFor(Ability element) {
		ConcreteElementalList list = new ConcreteElementalList();
		list.add(element.getName(), new AbilityCommand<Instance>(element, target));
		list.add("Close", new CloseContextMenuCommand(facade));
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
