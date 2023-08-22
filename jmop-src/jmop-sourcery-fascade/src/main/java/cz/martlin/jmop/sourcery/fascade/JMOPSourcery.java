package cz.martlin.jmop.sourcery.fascade;

import cz.martlin.jmop.common.fascade.IJMOPCommonFascade;

/**
 * The JMOP Sourcery fascade. Just contains particular subcomponents.
 * 
 * @author martin
 *
 */
public class JMOPSourcery implements IJMOPCommonFascade {
	/**
	 * The musicbase things.
	 */
	private final JMOPSourceryMusicbase musicbase;
	/**
	 * The configuration and lifecycle things.
	 */
	private final JMOPConfig config;
	/**
	 * The importing from youtube thing.
	 */
	private final JMOPRemote youtube;
	/**
	 * The local importing things.
	 */
	private final JMOPLocal local;

	/**
	 * Creates.
	 * 
	 * @param musicbase
	 * @param config
	 * @param youtube
	 * @param local
	 */
	public JMOPSourcery(JMOPSourceryMusicbase musicbase, JMOPConfig config, JMOPRemote youtube, JMOPLocal local) {
		super();
		this.musicbase = musicbase;
		this.config = config;
		this.youtube = youtube;
		this.local = local;
	}

	/**
	 * Returns the musicbase fascade.
	 */
	@Override
	public JMOPSourceryMusicbase musicbase() {
		return musicbase;
	}

	/**
	 * Returns the configs and lifecycle fascade.
	 * 
	 * @return
	 */
	public JMOPConfig config() {
		return config;
	}

	/**
	 * Returns the youtube fascade.
	 * 
	 * @return
	 */
	public JMOPRemote youtube() {
		return youtube;
	}

	/**
	 * Returns the locals fascade.
	 * 
	 * @return
	 */
	public JMOPLocal local() {
		return local;
	}

}
