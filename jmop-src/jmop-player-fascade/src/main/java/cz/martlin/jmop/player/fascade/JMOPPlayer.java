package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.fascade.IJMOPCommonFascade;

public class JMOPPlayer implements IJMOPCommonFascade {
	private final JMOPConfig config;
	private final JMOPPlayerMusicbase musicbase;
	private final JMOPPlaying playing;
	private final JMOPStatus status;
	
	public JMOPPlayer(JMOPConfig config, JMOPPlayerMusicbase musicbase, JMOPPlaying playing, JMOPStatus status) {
		super();
		this.config = config;
		this.musicbase = musicbase;
		this.playing = playing;
		this.status = status;
	}
	
	public JMOPConfig config() {
		return config;
	}
	
	@Override
	public JMOPPlayerMusicbase musicbase() {
		return musicbase;
	}
	
	public JMOPPlaying playing() {
		return playing;
	}
	
	public JMOPStatus status() {
		return status;
	}
	
	
	
}
