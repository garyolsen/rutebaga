package rutebaga.controller;

import rutebaga.commons.Rule;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class CompositeCommand implements Command {

	private static final Rule<Iterator<Command>> defaultFeasibilityRule = new DefaultFeasibilityRule();
	
	private Rule<Iterator<Command>> rule;
	private Set<Command> commands; 
	private Stack<Command> 
	
	public CompositeCommand() {
		rule = defaultFeasibilityRule;
		commands = new HashSet<Command>();
	}
	
	public void add(Command command) {
		commands.add(command);
	}
	
	public void remove(Command command) {
		commands.remove(command);
	}
	
	public void setFeasibilityRule(Rule<Iterator<Command>> rule) {
		this.rule = rule == null ? defaultFeasibilityRule : rule;
	}
	
	public boolean isFeasible() {
		return rule.determine(commands.iterator());
	}
	
	public void execute() {
		for (Command command)
	}
	
	private static class DefaultFeasibilityRule implements Rule<Iterator<Command>> {
		
	}
}
