package music;

import java.applet.AudioClip;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;

import control.GameRunning;

public class Music {

	private List<AudioClip> au = new ArrayList<AudioClip>();

	private AudioClip gameMusic;

	public Music() {
//		au.add(JApplet.newAudioClip(getClass().getResource("1.wav")));
//		au.add(JApplet.newAudioClip(getClass().getResource("2.wav")));
//		au.add(JApplet.newAudioClip(getClass().getResource("3.wav")));
//		au.add(JApplet.newAudioClip(getClass().getResource("win.wav")));
//		au.add(JApplet.newAudioClip(getClass().getResource("lose.wav")));
	}

	public void start() {
		//gameMusic = au.get(GameRunning.MAP - 1);
		if (gameMusic != null) {
			//gameMusic.loop();
		}
	}
	public void gameOver() { 
		if (gameMusic != null) {
			//gameMusic.stop();
		}
		//au.get(4).play();
	}
}
