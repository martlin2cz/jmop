package cz.martlin.jmop.player.fascade;

public class JMOPPlayer {
	private final JMOPConfig config;
	private final JMOPMusicbase musicbase;
	private final JMOPPlaying playing;
	
	public JMOPPlayer(JMOPConfig config, JMOPMusicbase musicbase, JMOPPlaying playing) {
		super();
		this.config = config;
		this.musicbase = musicbase;
		this.playing = playing;
	}
	
	public JMOPConfig config() {
		return config;
	}
	
	public JMOPMusicbase musicbase() {
		return musicbase;
	}
	
	public JMOPPlaying playing() {
		return playing;
	}
	
	
	
}
