package rutebaga.controller;

public class DefaultTickDaemon implements TickDaemon
{
	private boolean paused;
	private TickListener listener;
	private int msecRate;

	public DefaultTickDaemon(int msecRate) {
		this.msecRate = msecRate;
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					try
					{
						long time = System.currentTimeMillis();
						if (!paused && listener != null)
							listener.tick();
						long diff = DefaultTickDaemon.this.msecRate - (System.currentTimeMillis() - time);
						if (diff > 0)
							Thread.sleep(diff);
						// System.out.println(1000.0 / (System.currentTimeMillis() - time));
					}
					catch(Exception e)
					{
						throw new RuntimeException(e);
					}
				}
			}
		}.start();
	}

	public boolean isPaused()
	{
		return paused;
	}

	public void pause()
	{
		paused = true;
	}

	public void setListener(TickListener listener)
	{
		this.listener = listener;
	}

	public void unpause()
	{
		paused = false;
	}
}
