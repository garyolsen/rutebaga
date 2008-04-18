package rutebaga.test.model.effect;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.InstanceType;

public class RandomEffectType implements InstanceType<RandomEffect>
{
	private static Image appearance;
	
	static {
		try
		{
			appearance = ImageIO.read(new File("TestImages/treasure.png"));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public RandomEffect makeInstance()
	{
		RandomEffect rval = new RandomEffect(new ConstantValueProvider(0.005));
		rval.setAppearance(new Appearance());
		rval.getAppearance().setImage(appearance);
		rval.setLifetime(10000);
		return rval;
	}
}
