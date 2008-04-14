package rutebaga.test.view;

import java.awt.Color;
import java.awt.Font;

import rutebaga.view.game.FPSTextComponent;
import rutebaga.view.rwt.TextLabelComponent;
import rutebaga.view.rwt.View;

public class ViewTest
{

	public static void main(String args[])
	{
		View view = new View(800, 600);

		TextLabelComponent text = new FPSTextComponent();
		text.setLocation(100, 100);
		text.setFontColor(Color.BLUE);
		text.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		view.addViewComponent(text);

		while (true)
			view.renderFrame();
	}
}
