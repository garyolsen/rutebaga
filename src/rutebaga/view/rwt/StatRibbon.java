package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import rutebaga.controller.command.Command;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;

public class StatRibbon extends ViewComponent {
	
	private CloseCommand close = new CloseCommand();
	private OpenCommand open = new OpenCommand();
	
	private ColorAttribute color = new ColorAttribute(Color.GRAY);
	
	private ButtonComponent button;
	
	private int closedX = 0;
	private int openX = 200;
	
	private boolean toggled = false;
	
	private int HEIGHT = 30;
	
	private Entity entity;
	private StatisticId id1, id2;

	public StatRibbon(Entity entity, StatisticId id1, StatisticId id2) {
		button = new ButtonComponent(">");
		button.setBounds(0, 0, 10, HEIGHT);
		button.setCommand(open);
		this.setBounds(button.getBounds());
		
		this.entity = entity;
		this.id1 = id1;
		this.id2 = id2;
	}
	
	public void draw(Drawer draw) {
		draw.setAttribute(color);
		draw.drawRectangle(getLocation(), (toggled) ? openX : closedX , HEIGHT);
		if(toggled) {
			draw.drawString(new Point(getX() + openX/4, HEIGHT*3/4), id1.getName() + entity.getStats().getStatObject(id1));
			draw.drawString(new Point(getX() + openX/2, HEIGHT*3/4), id2.getName() + entity.getStats().getStatObject(id1));
		}
		button.draw(draw);
	}

	public void setLocation(int x, int y) {
		button.setLocation(x, y);
		super.setLocation(x, y);
	}
	
	public void setLocation(Point p) {
		button.setLocation(p);
		super.setLocation(p);
	}
	
	private void toggleOpen() {
		toggled = true;
		button.setCommand(close);
		button.setLabel("<");

		this.setBounds(getX(), getY(), openX + 10, HEIGHT);

		button.setLocation(openX, button.getY());
	}
	
	private void toggleClosed() {
		toggled = false;
		button.setCommand(open);
		button.setLabel(">");
		this.setBounds(button.getBounds());
		

		button.setLocation(getX() + closedX, button.getY());
	}
	

	@Override
	protected boolean processMouseEvent(MouseEvent event) {
		return button.processMouseEvent(event) || super.processMouseEvent(event);
	}

	
	private class OpenCommand implements Command {

		public void execute() {
			if(isFeasible())
				toggleOpen();
		}

		public boolean isFeasible() {
			return true;
		}
		
	}
	
	private class CloseCommand implements Command {

		public void execute() {
			if(isFeasible()) {
				toggleClosed();
			}
		}

		public boolean isFeasible() {
			return true;
		}
		
	}

}
