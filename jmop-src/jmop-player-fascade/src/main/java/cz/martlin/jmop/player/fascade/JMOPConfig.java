package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;

public class JMOPConfig {
	
	private final MusicbaseModyfiingEncapsulator modyfiing;
	private final PlayerEngineWrapper engine;
	
	private final BaseConfiguration config;
	
	public JMOPConfig(BaseConfiguration config, BaseMusicbase musicbase, BasePlayerEngine engine) {
		super();
		this.config = config;
		
		this.modyfiing = new MusicbaseModyfiingEncapsulator(musicbase);
		this.engine = new PlayerEngineWrapper(engine);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseConfiguration> T getConfiguration() {
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
		engine.terminate();
		modyfiing.terminate();
	}
	
	
	/////////////////////////////////////////////////////////////////
}
