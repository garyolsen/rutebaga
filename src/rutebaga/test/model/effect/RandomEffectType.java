package rutebaga.test.model.effect;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.StaticAppearanceManager;

public class RandomEffectType implements InstanceType<RandomEffect>
{
	private static Image appearance;
	
	static {
		try
		{
			appearance = ImageIO.read(new File("TestImages/yoshi_egg01.png"));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public RandomEffect makeInstance()
	{
		RandomEffect rval = new RandomEffect(new ConstantValueProvider(0.005));
		rval.setAppearanceManager(new StaticAppearanceManager(new Appearance(appearance)));
		rval.setLifetime(10000);
		return rval;
	}
}
