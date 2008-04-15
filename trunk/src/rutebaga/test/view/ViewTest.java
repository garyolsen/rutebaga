package rutebaga.test.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rutebaga.view.game.FPSTextComponent;
import rutebaga.view.rwt.*;

public class ViewTest {

	public static void main(String args[]) {
		View view = new View( 800, 600);
		view.setFullscreen();
		
		TextLabelComponent text = new FPSTextComponent();
		text.setLocation(100,100);
		text.setFontColor( Color.BLUE );
		text.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		view.addViewComponent(text);
		
		ButtonComponent button = new ButtonComponent("test");
		button.setLocation( 200, 200);
		
		
		 
		try {
			Image image = ImageIO.read( new File("TestImages/cheese.png"));
			ImageComponent imageComponent = new ImageComponent(image);
			
			imageComponent.setLocation(300, 300);
			
			view.addViewComponent(imageComponent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		view.addViewComponent( button );
		
		while(true)
			view.renderFrame();
	}
}
