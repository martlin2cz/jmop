package cz.martlin.jmop.sourcery.fascade;

import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;

public class TestingRemotesConfiguration implements BaseRemotesConfiguration {

	@Override
	public int getSearchCount() {
		return 10;
	}

}
