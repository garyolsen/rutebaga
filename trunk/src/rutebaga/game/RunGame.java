package rutebaga.game;

import rutebaga.controller.Game;
import rutebaga.controller.GameDaemon;
import rutebaga.controller.TitleActionInterpreter;
import  sun.audio.*;    //import the sun.audio package
import  java.io.*;

public class RunGame
{
	public static void main(String args[]) {

		Game game = new AgabaturGame();
		
		GameDaemon daemon = GameDaemon.initialize(game);

		//** add this into your application code as appropriate
		// Open an input stream  to the audio file.
		InputStream in = null;
		try {
			in = new FileInputStream("TestImages/supermarios.mid");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create an AudioStream object from the input stream.
		AudioStream as = null;
		try {
			as = new AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
		// Use the static class member "player" from class AudioPlayer to play
		// clip.
		AudioPlayer.player.start(as);            
		// Similarly, to stop the audio.
		//AudioPlayer.player.stop(as); 

		
		TitleActionInterpreter title = new TitleActionInterpreter();
		daemon.activate(title);
	}
}
