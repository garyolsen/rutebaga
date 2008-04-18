package rutebaga.model.environment.appearance;

import rutebaga.model.environment.Instance;

public class AnimatedAppearanceManager extends AppearanceManager
{
	private Appearance[] appearances;
	private int wait;
	private int countdown;
	private int current;

	public AnimatedAppearanceManager(Appearance[] appearances, int wait)
	{
		super();
		this.appearances = appearances;
		this.wait = wait;
	}

	@Override
	public Appearance getAppearance()
	{
		return appearances[current];
	}

	@Override
	public void tick()
	{
		if (countdown == 0)
		{
			current++;
			if(current >= appearances.length)
				current = 0;
			countdown = wait;
		}
		countdown--;
	}

}
