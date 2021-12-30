package cz.martlin.jmop.sourcery.fascade;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;

public class JMOPConfig {
	
	private final MusicbaseModyfiingEncapsulator modyfiing;
	
	private final BaseRemotesConfiguration config;
	
	public JMOPConfig(BaseRemotesConfiguration config, BaseMusicbase musicbase) {
		super();
		this.config = config;
		
		this.modyfiing = new MusicbaseModyfiingEncapsulator(musicbase);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseRemotesConfiguration> T getConfiguration() {
		return (T) config;
	}
	
	/////////////////////////////////////////////////////////////////
	public void load()  {
		//TODO config.load()
		modyfiing.load();
	}

	public void reload()  {
		//TODO config.reload() ?
		modyfiing.reload();
	}
	
	public void terminate()  {
		modyfiing.terminate();
	}
	
	
	/////////////////////////////////////////////////////////////////
}
