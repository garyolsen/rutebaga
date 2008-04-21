package rutebaga.test.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ConcreteElementalList;
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
			
			
			ConcreteElementalList list = new ConcreteElementalList();
			list.add("A");
			list.add("B");
			list.add("C");
			list.add("D");
			list.add("E");
			
			
			ContextMenu cm = new ContextMenu(list);
			cm.setLocation(200, 300);
			
			view.addViewComponent(cm);
			
			view.addViewComponent(scroll);
			
			TextFieldComponent component = new TextFieldComponent(200, 20);
			component.setHasFocus(true);
			component.setLocation(400,200);
			
			DialogDecorator dialog = new DialogDecorator(new TextLabelComponent("Please bind a key for Walk!"), 150, 100);
			dialog.setLocation(200,200);
			
			view.addViewComponent(dialog);
			
			
			view.addViewComponent(component);
			
			while(true)
				view.renderFrame();

		}

}
