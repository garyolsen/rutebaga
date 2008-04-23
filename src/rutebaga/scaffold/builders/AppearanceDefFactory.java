package rutebaga.scaffold.builders;

import java.awt.Image;
import java.util.ArrayList;

import rutebaga.appearance.AnimatedAppearanceDef;
import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.appearance.StaticAppearanceDef;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.scaffold.MasterScaffold;

public class AppearanceDefFactory
{
	private static enum Type
	{
		ANIMATED,
		STATIC;
	}

	private static AppearanceDefFactory instance = new AppearanceDefFactory();

	public static AppearanceDefFactory getInstance()
	{
		return instance;
	}

	public AppearanceManagerDefinition get(String description,
			MasterScaffold scaffold)
	{
		try
		{
		// FORMAT
		// animated [imageStart] waitTime orientation (offset)
		// static [image] orientation (offset)
		if (description == null)
			return null;
		String[] tokens = description.split("[\\s\\t]");
		switch (Type.valueOf(tokens[0].toUpperCase()))
		{
		case ANIMATED:
			return getAnimated(tokens, scaffold);
		case STATIC:
			return getStatic(tokens, scaffold);
		}
		return null;
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed while parsing \"" + description + "\"", e);
		}
	}

	private AppearanceManagerDefinition getAnimated(String[] tokens,
			MasterScaffold scaffold)
	{
		AnimatedAppearanceDef def = new AnimatedAppearanceDef();

		String imageName = tokens[1];
		String waitString = tokens[2];
		String orienataionName = tokens[3];
		if(tokens.length >= 6)
		{
			def.setOffsetX(Integer.parseInt(tokens[4]));
			def.setOffsetY(Integer.parseInt(tokens[5]));
		}

		boolean done = false;
		ArrayList<Image> images = new ArrayList<Image>();
		for (int i = 1; !done; i++)
		{
			String nStr = String.valueOf(i);
			if (nStr.length() == 1)
				nStr = "0" + nStr;
			Image image = (Image) scaffold.get(imageName + nStr);
			if (image != null)
				images.add(image);
			else
				done = true;
		}

		def.setImages(images.toArray(new Image[0]));

		Orientation orientation = Orientation.valueOf(orienataionName);
		if (orientation != null)
			def.setOrientation(orientation);

		try
		{
			Integer wait = Integer.parseInt(waitString);
			if (wait != null)
				def.setWait(wait);
		}
		catch (Exception e)
		{

		}

		return def;
	}

	private AppearanceManagerDefinition getStatic(String[] tokens,
			MasterScaffold scaffold)
	{
		StaticAppearanceDef def = new StaticAppearanceDef();

		String imageName = tokens[1];
		String orientationName = tokens[2];
		
		if(tokens.length >= 5)
		{
			def.setOffsetX(Integer.parseInt(tokens[3]));
			def.setOffsetY(Integer.parseInt(tokens[4]));
		}

		Image image = (Image) scaffold.get(imageName);
		def.setImage(image);

		Orientation orientation = Orientation.valueOf(orientationName
				.toUpperCase());
		if (orientation != null)
			def.setOrientation(orientation);

		return def;

	}
}
