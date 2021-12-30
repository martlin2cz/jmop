package cz.martlin.jmop.sourcery.fascade;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSource;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
import cz.martlin.jmop.sourcery.engine.NewTrackAdder;

public class JMOPRemote {
	
	private final NewTrackAdder adder;
	//TODO all the remaining remote-based actions performers
	
	public JMOPRemote(BaseRemoteSource remote, BaseMusicbaseModifing musicbase) {
		this.adder = new NewTrackAdder(remote, musicbase);
	}

	public Track add(Bundle bundle, String query, boolean download) throws JMOPSourceryException {
		return adder.add(bundle, query, download);
	}
	
	//TODO all the remaining remote-based actions

}
