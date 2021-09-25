package cz.martlin.jmop.player.fascade;

public class JMOPPlayer {
	private final JMOPConfig config;
	private final JMOPMusicbase musicbase;
	private final JMOPPlaying playing;
	private final JMOPStatus status;
	
	public JMOPPlayer(JMOPConfig config, JMOPMusicbase musicbase, JMOPPlaying playing, JMOPStatus status) {
		super();
		this.config = config;
		this.musicbase = musicbase;
		this.playing = playing;
		this.status = status;
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
	
	public JMOPStatus status() {
		return status;
	}
	
	
	
}
