package cz.martlin.jmop.sourcery.engine;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseLoading;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.remote.youtube.YoutubeRemoteSource;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSource;
import cz.martlin.jmop.sourcery.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;

@Tag("IDE_ONLY")
class NewTrackAdderTest {

	public class TestingConfig implements BaseRemotesConfiguration {

		@Override
		public int getSearchCount() {
			return 5;
		}

	}

	@RegisterExtension
	public TestingMusicdataExtension tme = createExtension();
	
	private TestingMusicdataExtension createExtension() {
		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();
		return TestingMusicdataExtension.withMusicbase(() -> musicbase, null);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Test
	void testWithoutDownload() throws JMOPSourceryException {
		NewTrackAdder adder = createAdder();
		
		Track znamkaPunku = adder.add(tme.tmd.robick, "robick znamka punku", false);
		System.out.println(znamkaPunku);
		assertNotNull(znamkaPunku);
		
		// check the track was added
		BaseMusicbaseLoading musicbase = (BaseMusicbaseLoading) tme.getMusicbase();
		assertTrue(musicbase.tracks(tme.tmd.robick).contains(znamkaPunku));
	}
	
	@Test
	void testWithDownload() throws JMOPSourceryException {
		NewTrackAdder adder = createAdder();
		
		Track totaHelpa = adder.add(tme.tmd.robick, "robick tota helpa", true);
		System.out.println(totaHelpa);
		assertNotNull(totaHelpa);
		
		// check the track was added
		BaseMusicbaseLoading musicbase = (BaseMusicbaseLoading) tme.getMusicbase();
		assertTrue(musicbase.tracks(tme.tmd.robick).contains(totaHelpa));
		
		// check the file to be was successfully downloaded
		assertNotNull(totaHelpa.getFile());
		assertTrue(totaHelpa.getFile().exists());
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private NewTrackAdder createAdder() {
		BaseProgressListener listener = new PrintingListener(System.out);
		BaseRemotesConfiguration config = new TestingConfig();
		BaseRemoteSource remote = YoutubeRemoteSource.create(config, listener);
		BaseMusicbaseModifing musicbaseModifiing = tme.getMusicbase();
		BaseMusicbaseLoading musicbaseLoading = (BaseMusicbaseLoading) tme.getMusicbase();
		NewTrackAdder adder = new NewTrackAdder(remote.querier(), remote.downloader(), musicbaseModifiing, musicbaseLoading);
		return adder;
	}


	

}
