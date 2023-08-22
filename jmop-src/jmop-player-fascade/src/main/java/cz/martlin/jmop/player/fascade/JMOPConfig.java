package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;

/**
 * The component of the fascade doing the configurations and lifecycle.
 * 
 * @author martin
 *
 */
public class JMOPConfig {
	/**
	 * The musicbase.
	 */
	private final MusicbaseModyfiingEncapsulator modyfiing;
	/**
	 * The player engine wrapper.
	 */
	private final PlayerEngineWrapper engine;
	
	/**
	 * The configuration instance.
	 */
	private final BaseConfiguration config;
	
	/**
	 * Creates.
	 */
	public JMOPConfig(BaseConfiguration config, BaseMusicbase musicbase, BasePlayerEngine engine) {
		super();
		this.config = config;
		
		this.modyfiing = new MusicbaseModyfiingEncapsulator(musicbase);
		this.engine = new PlayerEngineWrapper(engine);
	}

	/**
	 * Returns the configuration.
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseConfiguration> T getConfiguration() {
		return (T) config;
	}
	
	/////////////////////////////////////////////////////////////////
	/**
	 * Loads the musicbase from storage.
	 * 
	 */
	public void load()  {
		//TODO config.load()
		modyfiing.load();
	}

	/**
	 * Reloads the musicbase from storage.
	 * 
	 */
	public void reload()  {
		//TODO config.reload() ?
		modyfiing.reload();
	}
	
	/**
	 * Terminates. Stops the engine and terminates the storage.
	 */
	public void terminate()  {
		engine.terminate();
		modyfiing.terminate();
	}
	
	
	/////////////////////////////////////////////////////////////////
}
