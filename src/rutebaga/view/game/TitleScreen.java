package rutebaga.view.game;


import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;


import rutebaga.controller.command.ElementalList;
import rutebaga.controller.command.ListElement;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.ButtonComponent;
import rutebaga.view.rwt.TextLabelComponent;
import rutebaga.view.rwt.ViewCompositeComponent;

public class TitleScreen extends ViewCompositeComponent {

	private static final ColorAttribute background = new ColorAttribute(new Color(255,255,255));
	
	public TitleScreen(ElementalList list, int width, int height) {
		this.setBounds(new Rectangle(width, height));
		
		
		
		int count = 0;
		
		for(ListElement e : list)
			count++;
		
		int spacing = 50;
		int xAlign = this.getWidth()/2 - 50;
		int yAlign = (getHeight() - spacing * count - 100) / 2;
		
		TextLabelComponent label = new TextLabelComponent(list.getLabel());
		label.setLocation(xAlign, yAlign - 100);
		this.addChild(label);
		
		for(ListElement e : list ) {
			ButtonComponent button = new ButtonComponent(e.getLabel());
			button.setCommand(e.getCommand());
			button.setLocation(xAlign, yAlign);
			
			this.addChild(button);
			yAlign += spacing;
		}
	}
	
	@Override
	public void draw(Drawer draw) {
		draw.setAttribute(background);
		draw.drawRectangle(new Point(0,0), getWidth(), getHeight());
		
		super.draw(draw);
	}

}
