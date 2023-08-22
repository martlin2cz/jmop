package cz.martlin.jmop.sourcery.fascade;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.sourcery.remote.BaseRemotesConfiguration;

/**
 * The JMOP Sourcery config fascade.
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
	 * The configuration.
	 * 
	 */
	private final BaseRemotesConfiguration config;
	
	/**
	 * Creates.
	 * 
	 * @param config
	 * @param musicbase
	 */
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
	
	/**
	 * Loads the sourcery (the musicbase). 
	 */
	public void load()  {
		//TODO config.load()
		modyfiing.load();
	}

	/**
	 * Reloads the sourcery (the musicbase).
	 */
	public void reload()  {
		//TODO config.reload() ?
		modyfiing.reload();
	}
	
	/**
	 * Terminates the sourcery (the musicbase).
	 */
	public void terminate()  {
		modyfiing.terminate();
	}
	
	
	/////////////////////////////////////////////////////////////////
}
