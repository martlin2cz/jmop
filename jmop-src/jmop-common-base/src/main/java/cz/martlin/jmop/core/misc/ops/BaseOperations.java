package cz.martlin.jmop.core.misc.ops;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseOperations {

	///////////////////////////////////////////////////////////////////////////////////////////////
	void runSearch(Bundle bundle, String query, ConsumerWithException<List<Track>> resultHandler)
			throws JMOPSourceException;

	void runLoadNext(Track track, ConsumerWithException<Track> resultHandler) throws JMOPSourceException;

	//TODO create kinds of resultHandler(s), issue #5
	void prepareFiles(Track track, ConsumerWithException<Track> resultHandler) throws JMOPSourceException;

}