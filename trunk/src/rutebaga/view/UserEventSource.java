package rutebaga.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public interface UserEventSource {
	void addKeyListener(KeyListener kl );
	
	void addMouseListener(MouseListener ml);
}
