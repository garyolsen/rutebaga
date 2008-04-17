package rutebaga.controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 * @author Matthew Chuah
 */
public interface InterpreterManager extends ActionListener
{
	/**
	 * @param uai
	 */
	public void activate(UserActionInterpreter uai);

	/**
	 * @param uai
	 */
	public void deactivate(UserActionInterpreter uai);
}
