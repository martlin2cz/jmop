package cz.martlin.jmop.core.sources.remote;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.TestingPrinter;

public abstract class AbstractRemoteQuerierBaseTest {

	private final AbstractRemoteQuerier querier;

	public AbstractRemoteQuerierBaseTest() {
		super();
		this.querier = createQuerier();
	}

	protected abstract AbstractRemoteQuerier createQuerier();

	
	/////////////////////////////////////////////////////////////////////////////////////


	@Test
	public void testRunSearch() throws JMOPSourceException {
		Bundle bundle = createBundle();
		String query = createQuery();

		System.out.println("Searching " + query);
		List<Track> tracks = querier.runSearch(bundle, query);
		System.out.println("The result is \n" + TestingPrinter.print(tracks));

		assertFalse(tracks.isEmpty());
	}

	protected abstract Bundle createBundle();

	protected abstract String createQuery();

	/////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testRunLoadNext() throws JMOPSourceException {
		Track track = createTrack();

		System.out.println("Loading next of \n" + TestingPrinter.print(track));
		Track next = querier.runLoadNext(track);
		System.out.println("The next is \n" + TestingPrinter.print(next));

		assertNotNull(next);
	}

	protected abstract Track createTrack();

}
