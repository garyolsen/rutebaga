package rutebaga.test;

import java.awt.Button;
import java.awt.Event;
import java.io.*;
import sun.audio.*;

public class AudioStreamPlay extends java.applet.Applet
{
	Button play, loop, stop, reset;
	// AudioPlayer instantiated to force run of static initializers.
	AudioPlayer audioPlayer = AudioPlayer.player;
	AudioDataStream audioDataStream;
	ContinuousAudioDataStream continuousAudioDataStream;

	// Applet role
	public void init()
	{
		try
		{

			// Get sound from file stream.
			FileInputStream fis = new FileInputStream(new File("C:/supermarios.mid"));
			AudioStream as = new AudioStream(fis); // header plus audio data
			AudioData ad = as.getData(); // audio data only, no header
			audioDataStream = new AudioDataStream(ad);
			continuousAudioDataStream = new ContinuousAudioDataStream(ad);
			// Create GUI.
			add(play = new Button("play"));
			add(loop = new Button("loop"));
			add(stop = new Button("stop"));
			add(reset = new Button("reset"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Component role (1.02 event model)
	public boolean handleEvent(Event event)
	{
		if (event.target == play)
		{
			audioPlayer.start(audioDataStream);
			return true;
		}
		if (event.target == loop)
		{
			audioPlayer.start(continuousAudioDataStream);
			return true;
		}
		if (event.target == stop)
		{
			audioPlayer.stop(audioDataStream);
			audioPlayer.stop(continuousAudioDataStream);
			return true;
		}
		if (event.target == reset)
		{
			audioDataStream.reset();
			continuousAudioDataStream.reset();
			return true;
		}
		return super.handleEvent(event);
	}
}
