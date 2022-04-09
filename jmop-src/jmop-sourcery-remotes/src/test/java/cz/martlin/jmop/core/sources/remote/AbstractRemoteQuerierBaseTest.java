package cz.martlin.jmop.core.sources.remote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
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
	public void testRunSearch() throws JMOPSourceryException  {
		Bundle bundle = createBundle();
		String query = createQuery();

		System.out.println("Searching " + query);
		List<TrackData> tracks = querier.search(query);
		System.out.println("The result is \n" + TestingPrinter.printTDs(tracks));

		assertFalse(tracks.isEmpty());
	}

	protected abstract Bundle createBundle();

	protected abstract String createQuery();

	/////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testRunLoadNext() throws JMOPSourceryException  {
		Track track = createTrack();

		System.out.println("Loading next of \n" + TestingPrinter.print(track));
		TrackData next = querier.loadNext(track);
		System.out.println("The next is \n" + TestingPrinter.print(next));

		assertNotNull(next);
	}

	protected abstract Track createTrack();

}
