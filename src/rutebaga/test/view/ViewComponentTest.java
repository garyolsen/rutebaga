package rutebaga.test.view;

import java.awt.Color;

import rutebaga.view.game.FPSTextComponent;
import rutebaga.view.rwt.*;

public class ViewComponentTest {


		private static int SCREENWIDTH = 800, SCREENHEIGHT = 600;


		public static void main(String args[])
		{
			View view = new View(SCREENWIDTH, SCREENHEIGHT);
			//view.setFullscreen();

			FPSTextComponent fps = new FPSTextComponent();
			fps.setLocation(100, 100);
			fps.setFontColor(Color.RED);	
			view.addViewComponent(fps);
			
			ViewCompositeComponent vcc = new ViewCompositeComponent();
			vcc.addChild(new ButtonComponent("test1"));
			vcc.addChild(new ButtonComponent("test2"));
			vcc.addChild(new ButtonComponent("test3"));
			vcc.addChild(new ButtonComponent("test4"));
			vcc.addChild(new ButtonComponent("test5"));
		
			
			ScrollDecorator scroll = new ScrollDecorator(vcc, 200, 100);
			scroll.setLocation(200, 200);
			
			view.addViewComponent(scroll);
			
			while(true)
				view.renderFrame();

		}

}
