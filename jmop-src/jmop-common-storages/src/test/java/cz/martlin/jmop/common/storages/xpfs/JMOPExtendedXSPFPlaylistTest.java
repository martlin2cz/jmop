package cz.martlin.jmop.common.storages.xpfs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.utils.TestingMusicbase;
import cz.martlin.jmop.common.utils.TestingTracksSource;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

class JMOPExtendedXSPFPlaylistTest {
	
	private final TestingMusicbase tmb;
	private final TestingTracksSource tracks;
	private final BaseErrorReporter reporter;
	
	
	public JMOPExtendedXSPFPlaylistTest() {
		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();
		this.tmb = new TestingMusicbase(musicbase, false);
		this.tracks = new TestingTracksSource(TrackFileFormat.MP3);
		this.reporter = new SimpleErrorReporter();
	}
	
	
	@Test
	void testPlaylists() throws JMOPPersistenceException {
		JMOPExtendedXSPFPlaylist pla = JMOPExtendedXSPFPlaylist.createNew(reporter);
		
		pla.setPlaylistData(tmb.discovery, tracks);
		
		Map<String, Track> tracks = Map.of( //
				tmb.aerodynamic.getTitle(), tmb.aerodynamic, // 
				tmb.verdisQuo.getTitle(), tmb.verdisQuo,  //
				tmb.oneMoreTime.getTitle(), tmb.oneMoreTime); //
		
		Playlist extracted = pla.getPlaylistData(tmb.daftPunk, tracks);
		
		assertEquals(tmb.discovery, extracted);
	}
	
	@Test
	void testBundles() throws JMOPPersistenceException {
		JMOPExtendedXSPFPlaylist pla = JMOPExtendedXSPFPlaylist.createNew(reporter);
		
		pla.setBundleData(tmb.daftPunk);
		
		Bundle extracted = pla.getBundleData();
		
		assertEquals(tmb.daftPunk, extracted);
	}
	
	@Test
	void testCreateSaveLoad() throws JMOPPersistenceException {
		File file;
		try {
			file = File.createTempFile("some-", "-playlist.xspf");
		} catch (IOException e) {
			assumeTrue(false, e.toString());
			return;
		}
		
		// Load. Will fail (create new as a fallback).
		assertThrows(JMOPPersistenceException.class, () -> JMOPExtendedXSPFPlaylist.load(file, reporter));
		JMOPExtendedXSPFPlaylist.tryLoadOrCreate(file, reporter);
		
		// So create new and save it.
		JMOPExtendedXSPFPlaylist created = JMOPExtendedXSPFPlaylist.createNew(reporter);
		// do something ...
		created.save(file);
		
		JMOPExtendedXSPFPlaylist.load(file, reporter);
		
	}

}
