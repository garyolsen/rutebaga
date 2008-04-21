package rutebaga.controller.command;

import rutebaga.model.entity.Ability;

public class AbilityUseCommand<T> implements Command {

	private Ability<T> ability;
	private T target;
	
	public AbilityUseCommand(Ability<T> ability, T target) {
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