package rutebaga.test.view;


import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import rutebaga.commons.Vector;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.EntityType;
import rutebaga.model.environment.Appearance;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.map.Tile;
import rutebaga.view.game.FPSTextComponent;
import rutebaga.view.game.MapComponent;
import rutebaga.view.rwt.*;
import temporary.Bumper;
import temporary.WindTunnel;

public class ViewTest {
	
	private static Random random = new Random();
	
	private static int SCREENWIDTH = 800, SCREENHEIGHT=600;

	public static void main(String args[]) {
		View view = new View( SCREENWIDTH, SCREENHEIGHT );
		
		
		CharEntity avatar;
		
		Environment environment = new Environment(new Rect2DTileConvertor());
		
		
		try {
			Image cheese = ImageIO.read(new File("TestImages/cheese.png"));
			Image grass = ImageIO.read(new File("TestImages/grass.jpg"));
			Image treasure = ImageIO.read(new File("TestImages/treasure.png"));

		
		for (int x = -5; x < 35; x++)
		{
			for (int y = -5; y < 75; y++)
			{
				Vector location = new Vector(x, y);
				Tile tile = new Tile()
				{

					@Override
					public double getFriction()
					{
						return 0.1;
					}

				};
				Appearance water = new Appearance(tile);
				water.setImage(grass);
				tile.setAppearance(water);
				environment.add(tile, location);
			}
		}
		

		for (int x = 0; x < 30; x++)
		{
			for (int y = 0; y < 30; y++)
			{
				Vector location = new Vector(x, y);
				if (x % 8 == 0 && y % 8 == 0)
				{
					WindTunnel tunnel = new WindTunnel();
					Appearance chest = new Appearance(tunnel);
					chest.setImage(treasure);
					tunnel.setAppearance(chest);
					environment.add(tunnel, location);
				}
			}
		}

		for (int x = 0; x < 30; x++)
		{
			for (int y = 40; y < 70; y++)
			{
				Vector location = new Vector(x, y);
				if (x % 8 == 0 && y % 8 == 0)
				{
					Instance instance = new Bumper();
					Appearance chest = new Appearance(instance);
					chest.setImage(treasure);
					instance.setAppearance(chest);
					environment.add(instance, location);
				}
			}
		}

		avatar = new CharEntity()
		{

			@Override
			public boolean blocks(Instance other)
			{
				return false;
			}

			@Override
			public double getFriction()
			{
				return 0;
			}

			@Override
			public double getMass()
			{
				return 1;
			}

			@Override
			public Object accept(EntityEffect e)
			{
				return null;
			}

		};

		Appearance appearance = new Appearance(avatar);
		appearance.setImage(cheese);
		avatar.setAppearance(appearance);

		environment.add(avatar, new Vector(10, 10));
		
		MapComponent map = new MapComponent(avatar, SCREENWIDTH, SCREENHEIGHT);
		view.addViewComponent(map);
		
		FPSTextComponent fps = new FPSTextComponent();
		fps.setLocation(100, 100);
		fps.setFontColor(Color.RED);
		
		view.addViewComponent(fps);
		
		
		while(true) {
			view.renderFrame();
			
			avatar.applyMomentum(createImpulse());
			environment.tick();
			
		}
		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Vector createImpulse() {
		double xImpulse = 0.1;
		if(random.nextBoolean())
			xImpulse *= -1.0;
		
		xImpulse *= random.nextInt(4);
		
		double yImpulse = 0.1;
		if(random.nextBoolean())
			yImpulse *= -1.0;
		
		yImpulse *= random.nextInt(7);
		
		
		return new Vector(xImpulse, yImpulse);
	}
}
