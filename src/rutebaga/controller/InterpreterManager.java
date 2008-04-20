package rutebaga.controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import rutebaga.view.rwt.TextFieldListener;

/**
 * @author Matthew Chuah
 */
public interface InterpreterManager
{
	/**
	 * @param uai
	 */
	void activate(UserActionInterpreter uai);

	/**
	 * @param uai
	 */
	void deactivate(UserActionInterpreter uai);

	void registerAsKeyListener(KeyListener uai);
	
	void registerAsTextFieldListener(TextFieldListener uai);
	
	void registerAsMouseListener(MouseListener uai);
}
