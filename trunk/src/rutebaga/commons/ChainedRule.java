package rutebaga.commons;

import java.util.HashSet;
import java.util.Set;

/**
 * A rule based on a series of other rules.
 * 
 * For a ChainedRule, behavior is determined by the default value of the
 * {@link Rule}: a default value of true implies AND semantics (the rule is
 * true iff all rules are true), whereas a default value of false implies OR
 * semantics (the rule is false iff all rules are false).
 * 
 * @author Gary LosHuertos
 * 
 * @param <T>
 *            the context type
 */
public class ChainedRule<T> implements Rule<T> {
	private final boolean defaultValue;

	private Set<Rule<T>> rules = new HashSet<Rule<T>>();

	/**
	 * Constructs a new ChainedRule with the specified default value.
	 * 
	 * @param defaultValue
	 *            A boolean value that determines the default behavior of this
	 *            ChainRule. True implies AND semantics (the rule is true iff
	 *            all rules are true). False implies OR semantics (the rule is
	 *            false iff all rules are false).
	 */
	public ChainedRule(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Returns the the status of this rule in the specified context.  The semantics of the ChainedRule is specified by the
	 * default value.
	 * 
	 * @param context
	 *            the context in which the rule runs.
	 * @return A boolean corresponding to this Rule's agreement with the default
	 *         value in the given context.
	 * @see rutebaga.commons.Rule#determine(Object)
	 */
	public boolean determine(T context) {
		for (Rule<T> rule : rules)
			if (rule.determine(context) != defaultValue)
				return !defaultValue;
		return defaultValue;
	}

}
