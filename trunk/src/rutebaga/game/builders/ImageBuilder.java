package rutebaga.game.builders;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rutebaga.scaffold.MasterScaffold;

public class ImageBuilder extends ConfigFileBuilder
{

	public ImageBuilder(MasterScaffold scaffold)
	{
		super(scaffold);
	}

	public Object create(String id)
	{
		try
		{
			return ImageIO.read(new File(getProperty(id, "path")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getDefaultFileName()
	{
		return "config/images";
	}

}
