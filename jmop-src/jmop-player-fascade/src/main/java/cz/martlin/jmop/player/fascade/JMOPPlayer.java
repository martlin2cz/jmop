package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.fascade.IJMOPCommonFascade;

/**
 * The actual JMOP PLAYER fascade. Delegates to particular components.
 * 
 * @author martin
 *
 */
public class JMOPPlayer implements IJMOPCommonFascade {

	/**
	 * The config and lifecycle related things.
	 */
	private final JMOPConfig config;
	/**
	 * The musicbase related things.
	 */
	private final JMOPPlayerMusicbase musicbase;
	/**
	 * The player, playlist and playing related things.
	 */
	private final JMOPPlaying playing;
	/**
	 * The curerent status of things.
	 */
	private final JMOPStatus status;

	/**
	 * Constructs.
	 * 
	 * @param config
	 * @param musicbase
	 * @param playing
	 * @param status
	 */
	public JMOPPlayer(JMOPConfig config, JMOPPlayerMusicbase musicbase, JMOPPlaying playing, JMOPStatus status) {
		super();
		this.config = config;
		this.musicbase = musicbase;
		this.playing = playing;
		this.status = status;
	}

	/**
	 * Returns the configuration and lifecycle realated things object.
	 * 
	 * @return
	 */
	public JMOPConfig config() {
		return config;
	}

	/**
	 * Returns the musicbase related things object.
	 */
	@Override
	public JMOPPlayerMusicbase musicbase() {
		return musicbase;
	}

	/**
	 * Returns the playing related things object.
	 * 
	 * @return
	 */
	public JMOPPlaying playing() {
		return playing;
	}

	/**
	 * Returns the current status of things object.
	 * 
	 * @return
	 */
	public JMOPStatus status() {
		return status;
	}

}
