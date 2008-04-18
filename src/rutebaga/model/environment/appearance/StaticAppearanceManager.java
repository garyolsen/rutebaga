package rutebaga.model.environment.appearance;

import rutebaga.model.environment.Instance;

public class StaticAppearanceManager extends AppearanceManager
{
	private Appearance appearance;

	public StaticAppearanceManager(Appearance appearance)
	{
		super();
		this.appearance = appearance;
	}

	@Override
	public Appearance getAppearance()
	{
		return appearance;
	}

	@Override
	public void tick()
	{
		
	}
}
