package cz.martlin.jmop.core.misc.ops;

import java.util.List;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseOperations {

	///////////////////////////////////////////////////////////////////////////////////////////////
	void runSearch(Bundle bundle, String query, ConsumerWithException<List<Track>> resultHandler)
			throws JMOPMusicbaseException;

	void runLoadNext(Track track, ConsumerWithException<Track> resultHandler) throws JMOPMusicbaseException;

	//TODO create kinds of resultHandler(s), issue #5
	void prepareFiles(Track track, ConsumerWithException<Track> resultHandler) throws JMOPMusicbaseException;

}