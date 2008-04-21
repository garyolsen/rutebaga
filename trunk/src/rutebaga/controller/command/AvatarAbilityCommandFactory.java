package rutebaga.controller.command;

import rutebaga.commons.logic.Rule;
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
	private Rule<Object> changedDeterminer;
	
	public AvatarAbilityCommandFactory(Entity<?> avatar, Instance<?> target,
			UserInterfaceFacade facade, CommandQueue queue) {

		this.avatar = avatar;
		this.target = target;
		this.facade = facade;
		this.queue = queue;
	}


	public ElementalList getCommandListFor(final Ability element) {
		ConcreteElementalList list = new ConcreteElementalList();
		list.add(element.getName(), QueueCommand.makeForQueue(new AbilityCommand<Instance>(element, target), queue));
		if (avatar.getAvailableSkillPoints() > 0 && avatar == target) {
			list.add(new LabelDeterminer() {
				public String getLabel() {
					return "Assign skill point (" + avatar.getSkillPoints(element.getCategory()) + " assigned, " + avatar.getAvailableSkillPoints() + " left)";
				}
			}, QueueCommand.makeForQueue(new AssignSkillPointCommand<Instance>(element), queue));
			list.setChangeDeterminer(new Rule<Object>() {
				private int skillPoints = -1;
				public boolean determine(Object context) {
					boolean changed = skillPoints != avatar.getAvailableSkillPoints();
					skillPoints = avatar.getAvailableSkillPoints();
					return changed;
				}
			});
		}
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

	private class AssignSkillPointCommand<T> implements Command {

		private Ability<T> ability;
		
		public AssignSkillPointCommand(Ability<T> ability) {
			this.ability = ability;
		}
		
		public void execute() {
			avatar.allocateSkillPoints(ability.getCategory(), 1);
		}

		public boolean isFeasible() {
			return avatar.getAvailableSkillPoints() > 0;
		}
	}


}
