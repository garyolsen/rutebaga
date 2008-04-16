package rutebaga.controller;

public interface TickDaemon {
	void pause();
	
	void unpause();
	
	boolean isPaused();
	
	void setListener(TickListener listener);
}
