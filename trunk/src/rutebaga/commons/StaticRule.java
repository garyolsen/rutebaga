package rutebaga.commons;

/**
 * Used to represent a rule that should always evaluate the same way.
 * 
 * @author Gary
 * 
 * @param <T>
 *            The context for this Rule.
 */
public class StaticRule<T> implements Rule<T> {
	private final boolean value;

	/**
	 * Constructs a StaticRule that will always evaluate to the provided value.
	 * 
	 * @param value
	 *            will define the evaluation of this StaticRule.
	 */
	public StaticRule(boolean value) {
		this.value = value;
	}

	/**
	 * Returns the static value of this StaticRule. The same as calling
	 * {@link #determine(T)}.
	 * 
	 * @return the value of this StaticRule. The same as evaluating
	 *         {@link #determine(T)}
	 * @see #determine(T)
	 */
	public boolean isValue() {
		return value;
	}

	/**
	 * Returns the evaluation of this StaticRule in the provided context. The
	 * same as calling {@link #isValue()}.
	 * 
	 * @param context
	 *            The context in which this StaticRule will be evaluated.
	 * @return The evaluation of this StaticRule in the provided context.
	 * @see rutebaga.commons.Rule#determine(null)
	 * @see #isValue()
	 */
	public boolean determine(T context) {
		return value;
	}

}
