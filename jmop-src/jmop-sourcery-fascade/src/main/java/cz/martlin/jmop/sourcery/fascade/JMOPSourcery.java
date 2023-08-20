package cz.martlin.jmop.sourcery.fascade;

import java.util.List;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.fascade.JMOPMusicbase;

public class JMOPSourcery {
	private final JMOPMusicbase musicbase;
	private final JMOPConfig config;
	private final JMOPRemote youtube;
	
	public JMOPSourcery(JMOPMusicbase musicbase, JMOPConfig config, JMOPRemote youtube) {
		super();
		this.musicbase = musicbase;
		this.config = config;
		this.youtube = youtube;
	}
	
	public JMOPMusicbase musicbase() {
		return musicbase;
	}
	
	public JMOPConfig config() {
		return config;
	}
	
	public JMOPRemote youtube() {
		return youtube;
	}


	
}
