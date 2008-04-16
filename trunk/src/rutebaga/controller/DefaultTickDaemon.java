package rutebaga.controller;

import java.util.Timer;
import java.util.TimerTask;

public class DefaultTickDaemon implements TickDaemon {

	private Timer timer;
	private boolean paused;
	private TickListener listener;
	
	public DefaultTickDaemon(int msecFreq) {
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (!paused && listener != null)
					listener.tick();
			}
		}, 0, 1 / msecFreq);
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		paused = true;
	}

	public void setListener(TickListener listener) {
		this.listener = listener;
	}

	public void unpause() {
		paused = false;
	}
	
}
