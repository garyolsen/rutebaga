package rutebaga.commons;

import java.util.HashSet;
import java.util.Set;

/**
 * A rule based on a series of other rules.
 * 
 * The behavior is determined by the default value of the rule: a default value
 * of true implies AND semantics (the rule is true iff all rules are true),
 * whereas a default value of false implies OR semantics (the rule is false iff
 * all rules are false).
 * 
 * @author Gary LosHuertos
 * 
 * @param <T>
 *            the context type
 */
public class ChainedRule<T> implements Rule<T>
{
	private final boolean defaultValue;
	private Set<Rule<T>> rules = new HashSet<Rule<T>>();

	public ChainedRule(boolean defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public boolean determine(T context)
	{
		for (Rule<T> rule : rules)
			if (rule.determine(context) != defaultValue)
				return !defaultValue;
		return defaultValue;
	}

}
