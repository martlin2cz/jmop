package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.core.config.BaseConfiguration;

/**
 * The abstract configuration of default engine.
 * @author martin
 *
 */
public interface BaseDefaultEngineConfig extends BaseConfiguration {

	/**
	 * What to do when track file doesn't exist?
	 * 
	 * @author martin
	 *
	 */
	public enum NonExistingFileStrategy {
		STOP, SKIP;
	}

	/**
	 * After how much seconds of playing mark the track as played?
	 * 
	 * @return
	 */
	int getMarkAsPlayedAfter();

	/**
	 * What to do when track file doesn't exist?
	 * @return
	 */
	NonExistingFileStrategy getNonexistingFileStrategy();
	
}
