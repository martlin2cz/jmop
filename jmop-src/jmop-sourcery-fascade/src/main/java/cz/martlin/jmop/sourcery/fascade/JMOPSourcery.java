package cz.martlin.jmop.sourcery.fascade;

import cz.martlin.jmop.common.fascade.IJMOPCommonFascade;

public class JMOPSourcery implements IJMOPCommonFascade {
	private final JMOPSourceryMusicbase musicbase;
	private final JMOPConfig config;
	private final JMOPRemote youtube;
	
	public JMOPSourcery(JMOPSourceryMusicbase musicbase, JMOPConfig config, JMOPRemote youtube) {
		super();
		this.musicbase = musicbase;
		this.config = config;
		this.youtube = youtube;
	}
	
	@Override
	public JMOPSourceryMusicbase musicbase() {
		return musicbase;
	}
	
	public JMOPConfig config() {
		return config;
	}
	
	public JMOPRemote youtube() {
		return youtube;
	}


	
}
