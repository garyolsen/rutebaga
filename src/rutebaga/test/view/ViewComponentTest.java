package rutebaga.test.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import rutebaga.controller.command.Command;
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
			fps = new FPSTextComponent();
			fps.setLocation(100, 100);
			fps.setFontColor(Color.RED);	
			vcc.addChild(fps);
			vcc.addChild(new ButtonComponent("test3"));
			vcc.addChild(new ButtonComponent("test4"));
			fps = new FPSTextComponent();
			fps.setLocation(100, 100);
			fps.setFontColor(Color.RED);	
			vcc.addChild(fps);
			vcc.addChild(new ButtonComponent("test5"));
			fps = new FPSTextComponent();
			fps.setLocation(100, 100);
			fps.setFontColor(Color.RED);	
			vcc.addChild(fps);
		
			
			ScrollDecorator scroll = new ScrollDecorator(vcc, 200, 100);
			scroll.setLocation(500, 200);
			
			
			List<Command> commands = new ArrayList<Command>();
			commands.add(null);
			commands.add(null);
			commands.add(null);
			commands.add(null);
			commands.add(null);
			
			List<String> names = new ArrayList<String>();
			names.add("A");
			names.add("B");
			names.add("C");
			names.add("D");
			names.add("E");
			
			ContextMenu cm = new ContextMenu(commands, names);
			cm.setLocation(200, 300);
			
			view.addViewComponent(cm);
			
			view.addViewComponent(scroll);
			
			TextFieldComponent component = new TextFieldComponent();
			component.setHasFocus(true);
			component.setLocation(400,200);
			
			view.addViewComponent(component);
			
			while(true)
				view.renderFrame();

		}

}
