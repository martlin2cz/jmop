package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.player.engine.dflt.BaseDefaultEngineConfig.NonExistingFileStrategy;

public interface BaseDefaultEngineConfig extends BaseConfiguration {

	public enum NonExistingFileStrategy {
		STOP, SKIP;
	}

	int getMarkAsPlayedAfter();

	NonExistingFileStrategy getNonexistingFileStrategy();
	
}
