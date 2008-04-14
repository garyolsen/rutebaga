package rutebaga.controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public interface InterpreterManager extends KeyListener, ActionListener
{
	public void activate(UserActionInterpreter uai);

	public void deactivate(UserActionInterpreter uai);
}
