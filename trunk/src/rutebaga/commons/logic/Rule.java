package rutebaga.commons.logic;

import java.io.Serializable;

/**
 * Used to determine a truth value in a given context.
 * 
 * @author Gary
 * 
 * @param <T>
 *            The context for this Rule.
 */
public interface Rule<T> extends Serializable
{
	/**
	 * Evaluates the rule in the given context.
	 * 
	 * @param context
	 *            The context for the rule to be tested in.
	 * @return The status of the rule in the provided context.
	 */
	boolean determine(T context);
}
