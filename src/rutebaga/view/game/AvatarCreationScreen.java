package rutebaga.view.game;


import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElement;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.*;

public class AvatarCreationScreen extends ViewCompositeComponent {
	
	private static final ColorAttribute background = new ColorAttribute(new Color(255,255,255));

	
	public AvatarCreationScreen(Rectangle bounds, TextFieldListener listener, ElementalList list, Command accept, Command cancel) {
		this.setBounds(bounds);
		
		TextLabelComponent label = new TextLabelComponent("Name: ");
		label.setLocation(getWidth()/2 - 50, getHeight()/3 - 40);
		this.addChild(label);
		
		TextFieldComponent text = new TextFieldComponent(100, 20);
		text.addTextFieldListener(listener);
		text.setLocation(getWidth()/2, getHeight()/3 - 50);
		text.setHasFocus(true);
		text.setText(list.getLabel());
		
		this.addChild(text);
		
		Point p = new Point(getWidth() / 2, getHeight() / 3);
		
		for (ListElement element : list) {
			ButtonComponent button = new ButtonComponent(element.getLabel());
			button.setLocation((Point) p.clone());
			button.setParent(this);
			button.setCommand(element.getCommand());
			
			this.addChild(button);
			p.y += 50;
		}
		
		ButtonComponent button1 = new ButtonComponent("Create");
		button1.setLocation(getWidth()/2 - 100, getHeight()*2/3);
		button1.setCommand(accept);
		button1.setParent(this);
		
		this.addChild(button1);
		
		ButtonComponent button2 = new ButtonComponent("Cancel");
		button2.setLocation(getWidth()/2 + 100, getHeight()*2/3);
		button2.setCommand(cancel);
		button2.setParent(this);
		
		this.addChild(button2);
	}
	
	public void draw(Drawer draw) {

		draw.setAttribute(background);
		draw.drawRectangle(new Point(0,0), getWidth(), getHeight());
		
		super.draw(draw);
	}

}
