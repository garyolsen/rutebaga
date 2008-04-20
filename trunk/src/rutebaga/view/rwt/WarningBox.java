package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.Rectangle;

import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElement;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;

public class WarningBox extends ViewCompositeComponent {

	private ColorAttribute boxBackground = new ColorAttribute(new Color(150,150,255,200));
	
	Rectangle visibleArea;
	
	public WarningBox(ElementalList list, Vector2D dimensions) {
		this(list, dimensions, dimensions);
	}
	
	public WarningBox(ElementalList list, Vector2D visibleDimensions, Vector2D blockingDimensions) {
		visibleArea = new Rectangle(visibleDimensions.getX().intValue(), visibleDimensions.getY().intValue());
		Rectangle blockingArea = new Rectangle(blockingDimensions.getX().intValue(), blockingDimensions.getY().intValue());
		if (!blockingArea.contains(visibleArea))
			visibleArea = blockingArea;
		setBounds(blockingArea);
		
		int spacing = 50;
		int xAlign = getX() + this.getWidth()/2 - 50;
		int yAlign = getY() + (getHeight() - spacing * list.contentSize() - 100) / 2;
		
		TextLabelComponent label = new TextLabelComponent(list.getLabel());
		label.setLocation(xAlign, yAlign - 40);
		label.setFontColor(Color.RED);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		this.addChild(label);
		
		for(ListElement e : list ) {
			ButtonComponent button = new ButtonComponent(e.getLabel());
			button.setCommand(e.getCommand());
			button.setLocation(xAlign, yAlign);
			int[] xPoints = {20,40,0};
			int[] yPoints = {0,20,20};
			button.setButtonShape( new Polygon(xPoints, yPoints, 3));
			
			this.addChild(button);
			yAlign += spacing;
		}
	}
	
	@Override
	public void draw(Drawer draw) {
		draw.setAttribute(boxBackground);
		draw.drawShape(getLocation(), visibleArea);
		draw.setAttribute(null);
		
		super.draw(draw);
	}
}
