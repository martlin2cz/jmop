package cz.martlin.jmop.sourcery.app.config;

import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;

public class ConstantRemotesConfiguration implements BaseRemotesConfiguration {

	@Override
	public int getSearchCount() {
		return 5;
	}

}
