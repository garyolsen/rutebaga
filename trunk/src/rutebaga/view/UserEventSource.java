package rutebaga.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * An interface for listening to events from the keyboard and mouse.
 *
 */
public interface UserEventSource {
	void addKeyListener(KeyListener kl );
	
	void addMouseListener(MouseListener ml);
}
