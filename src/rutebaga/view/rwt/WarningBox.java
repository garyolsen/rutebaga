package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import rutebaga.commons.math.Vector2D;
import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElement;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;

public class WarningBox extends ViewCompositeComponent {

	private ColorAttribute blockingBackground = new ColorAttribute(new Color(150,150,150,150));
	private ColorAttribute fadeColor = new ColorAttribute(new Color(200,200,255,150));
	private ColorAttribute visibleBackground = new ColorAttribute(new Color(150,150,255,200));
	
	Rectangle blockingArea;
	Rectangle visibleArea;
	
	private ElementalList list;
	
	private int wavePos = 0;
	private boolean direction = true;
	
	public WarningBox(ElementalList list, Vector2D dimensions) {
		this(list, dimensions, dimensions);
	}
	
	public WarningBox(ElementalList list, Vector2D visibleDimensions, Vector2D blockingDimensions) {
		visibleArea = new Rectangle(visibleDimensions.getX().intValue(), visibleDimensions.getY().intValue());
		blockingArea = new Rectangle(blockingDimensions.getX().intValue(), blockingDimensions.getY().intValue());
		if (!blockingArea.contains(visibleArea))
			visibleArea = blockingArea;
		super.setBounds(blockingArea);
		
		this.list = list;
		
		visibleArea.setLocation((int) (blockingArea.getWidth() - visibleArea.getWidth()) / 2, (int) (blockingArea.getHeight() - visibleArea.getHeight()) / 2);
		
		TextLabelComponent label = new TextLabelComponent(list.getLabel(), TextLabelComponent.CENTER_ALIGNMENT);
		Rectangle labelBounds = new Rectangle((int) visibleArea.getWidth(), 30);
		label.setBounds(labelBounds);
		label.setLocation((int) visibleArea.getX(), (int) visibleArea.getY());
		label.setFontColor(Color.YELLOW);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		this.addChild(label);

		if (list.contentSize() == 0)
			return;
		
		int verticalSpace = (int) visibleArea.getHeight() - label.getHeight();
		int spacing = Math.max(verticalSpace / list.contentSize(), 10);
		int buttonWidth = 200;
		int buttonHeight = Math.max(spacing - 10, 5);
		int xPos = (int) (visibleArea.getX() + visibleArea.getWidth() / 2 - buttonWidth / 2);
		int yPos = (int) (visibleArea.getY() + label.getHeight());
		
		for (ListElement e : list) {
			ButtonComponent button = new ButtonComponent(e.getLabel());
			button.setCommand(e.getCommand());
			button.setLocation(xPos, yPos);
			button.setButtonShape(new Rectangle(0, 0, buttonWidth, buttonHeight));

			this.addChild(button);
			yPos += spacing;
		}
	}

	@Override
	public void draw(Drawer draw) {
		draw.setAttribute(blockingBackground);
		draw.drawShape(getLocation(), blockingArea);
		draw.setAttribute(visibleBackground);
		draw.drawShape(getLocation(), visibleArea);
		draw.setAttribute(null);
	
		if (list.contentSize() == 0) {
			draw.setAttribute(blockingBackground);
			int waveWidth = (int) visibleArea.getWidth() - 40;
			if (direction)
				wavePos += 10;
			else
				wavePos -= 10;
			if (wavePos > waveWidth || wavePos < 0)
				direction = !direction;
			Rectangle rect = new Rectangle(5, 100);
			Point p = new Point((int) (getX() + blockingArea.getWidth() / 2 - visibleArea.getWidth() / 2 + wavePos + 20), (int) (getY() + blockingArea.getHeight() / 2 - visibleArea.getHeight() / 2 + 50));
			draw.drawShape(p, rect);
			draw.setAttribute(fadeColor);
			p.x -= 5;
			draw.drawShape(p, rect);
			p.x += 10;
			draw.drawShape(p, rect);
		}
		
		super.draw(draw);
	}

	@Override
	protected boolean processMouseEvent(MouseEvent event) {
		return true;
	}
	
	@Override
	protected boolean processMouseMotionEvent(MouseEvent event) {
		return true;
	}
}
