package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collection;

import rutebaga.controller.command.Command;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.drawer.FontAttribute;

public class StatRibbon extends ViewComponent {
	
	private CloseCommand close = new CloseCommand();
	private OpenCommand open = new OpenCommand();
	
	private ColorAttribute backgroundColor = new ColorAttribute(Color.GRAY);
	private ColorAttribute textColor = new ColorAttribute(Color.BLACK);
	private ColorAttribute barColor = new ColorAttribute(Color.YELLOW);
	private FontAttribute font = new FontAttribute(new Font("Arial", Font.BOLD, 12));
	
	private ButtonComponent button;
	
	private int closedX = 0;
	private int openX = 200;
	
	private boolean toggled = false;
	
	private int buttonWidth = 14;
	
	private int barHeight = 20;
	private int barSpacing = 4;
	private int margin = 5;
	private int borderWidth = 2;
	
	private Collection<StatValue> stats;

	public StatRibbon(Collection<StatValue> stats) {
		button = new ButtonComponent(">");
		button.setBounds(0, 0, buttonWidth, (barHeight + barSpacing) * stats.size() + margin * 2 - barSpacing);
		button.setCommand(open);
		this.setBounds(button.getBounds());
		
		this.stats = stats;
	}
	
	public void draw(Drawer draw) {
		draw.setAttribute(backgroundColor);
		draw.drawRectangle(getLocation(), (toggled) ? openX : closedX , getHeight());
		if(toggled) {
			draw.setAttribute(font);
			int fontHeight = draw.getFontMetrics().getMaxAscent() + draw.getFontMetrics().getMaxDescent();
			int y = getY() + margin;
			
			for (StatValue stat: stats) {
				draw.setAttribute(textColor);
				draw.drawRectangle(new Point(getX() + margin, y), openX - margin * 2, barHeight);
				draw.setAttribute(barColor);
				draw.drawRectangle(new Point(getX() + margin + borderWidth, y + borderWidth), openX - margin * 2 - borderWidth * 2, barHeight - borderWidth * 2);
				draw.setAttribute(textColor);
				draw.drawString(new Point(getX() + margin * 2, y + fontHeight), stat.getId().getName());
				String value = (int) stat.getValue() + "";
				int textWidth = draw.getFontMetrics().stringWidth(value);
				draw.drawString(new Point(getX() + openX - textWidth - margin * 2, y + fontHeight), value);
				y += barHeight + barSpacing;
			}
			//draw.drawString(new Point(getX() + openX/2, HEIGHT*3/4), id2.getName() + entity.getStats().getStatObject(id1));
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

		this.setBounds(getX(), getY(), openX + margin * 2, (barHeight + barSpacing)  * stats.size() + margin * 2 - barSpacing);

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
