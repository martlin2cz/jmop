package cz.martlin.jmop.core.misc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class InternetConnectionStatusTest {
	public InternetConnectionStatusTest() {
	}

	@BeforeEach
	public void setUp() throws Exception {
		System.err.println();
	}

	///////////////////////////////////////////////////////////////////////////
	@Test
	public void testBasic() {
		final int timeout = 1;
		BaseConfiguration config = new TestingConfig(timeout);
		InternetConnectionStatus connection = new InternetConnectionStatus(config);
		assertFalse(connection.isOffline());

		connection.markOffline();
		assertTrue(connection.isOffline());

		sleep(2 * timeout);
		assertFalse(connection.isOffline());
	}

	@Test
	public void testProperly() {
		final int timeout = 5;
		BaseConfiguration config = new TestingConfig(timeout);
		InternetConnectionStatus connection = new InternetConnectionStatus(config);
		assertFalse(connection.isOffline());

		// firstly mark offline
		connection.markOffline();
		assertTrue(connection.isOffline());
		assertTrue(connection.isOffline());

		// sleep to check it still remains offline
		sleep(timeout / 2);
		assertTrue(connection.isOffline());
		assertTrue(connection.isOffline());

		// sleep the whole timout and check online
		sleep(timeout);
		assertFalse(connection.isOffline());
		assertFalse(connection.isOffline());

		// mark offline again
		connection.markOffline();
		assertTrue(connection.isOffline());
		assertTrue(connection.isOffline());

		// sleep more than the timout and check online
		sleep(timeout + 1);
		assertFalse(connection.isOffline());
		assertFalse(connection.isOffline());
	}
	///////////////////////////////////////////////////////////////////////////

	private void sleep(int wait) {
		try {
			TimeUnit.SECONDS.sleep(wait);
		} catch (InterruptedException e) {
			System.err.println(e);
			assumeTrue(e == null);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	public class TestingConfig implements BaseConfiguration {

		private final int offlineRetryTimeout;

		public TestingConfig(int offlineRetryTimeout) {
			this.offlineRetryTimeout = offlineRetryTimeout;
		}

		@Override
		public TrackFileFormat getSaveFormat() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getAllTracksPlaylistName() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getOfflineRetryTimeout() {
			return offlineRetryTimeout;
		}

		@Override
		public int getSearchCount() {
			throw new UnsupportedOperationException();
		}
	}
}
