package cz.martlin.jmop.sourcery.picocli.misc;

import cz.martlin.jmop.sourcery.fascade.JMOPRemote;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;

/**
 * The remote services enum.
 * 
 * @author martin
 *
 */
public enum Service {
	YOUTUBE;

	/**
	 * Picks the remote fascade based on that.
	 * @return
	 */
	public JMOPRemote pickRemote() {
		JMOPSourcery jmop = JMOPSourceryProvider.get().getSourcery();

		switch (this) {
		case YOUTUBE:
			return jmop.youtube();
		default:
			throw new IllegalArgumentException("unknown service");
		}
	}
}
