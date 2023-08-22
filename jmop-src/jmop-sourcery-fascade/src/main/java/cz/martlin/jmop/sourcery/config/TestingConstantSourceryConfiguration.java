package cz.martlin.jmop.sourcery.config;

import cz.martlin.jmop.common.fascade.config.TestingConstantCommonConfig;

/**
 * The testing constant sourcery config.
 * 
 * @author martin
 *
 */
public class TestingConstantSourceryConfiguration extends TestingConstantCommonConfig implements BaseJMOPSourceryConfig {

	@Override
	public int getSearchCount() {
		return 5;
	}

}
