package cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.JMOPtoXSFPAdapter;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.XSPFPlaylistTracksManager;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.BasePlaylistMetaInfoManager;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.XSPFExtensionElemsAttrsMetaInfoManager;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.conv.BaseValueToAndFromStringConverters;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.conv.SimpleValueToAndFromStringConverters;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.SimpleTestingMusicdata;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.util.Printer;
import cz.martlin.xspf.util.XSPFException;

class XSPFPlaylistExtendersTest {

	private final AbstractTestingMusicdata tmb;
//	private final TestingTracksSource tracks;
	private final XSPFPlaylistIO io;
	private final XSPFPlaylistManipulator extender;

	public XSPFPlaylistExtendersTest() {
		this.tmb = new SimpleTestingMusicdata();
//		this.tracks = new TestingTracksSource(TrackFileFormat.MP3);

		BaseErrorReporter reporter = new SimpleErrorReporter();
		io = new XSPFPlaylistIO(reporter);

		BaseValueToAndFromStringConverters converters = new SimpleValueToAndFromStringConverters();
		BasePlaylistMetaInfoManager<XSPFCommon> mim = new XSPFExtensionElemsAttrsMetaInfoManager(converters);
		JMOPtoXSFPAdapter adapter = new JMOPtoXSFPAdapter(mim);
		XSPFPlaylistTracksManager tracker = new XSPFPlaylistTracksManager(adapter);
		extender = new XSPFPlaylistManipulator(adapter, tracker);
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testPlaylist() throws JMOPPersistenceException, XSPFException {
		XSPFFile xfile = XSPFPlaylistIO.createNew();

		extender.setPlaylistData(tmb.discovery, xfile);
		Printer.print(0, "Playlist set", xfile);

		Map<String, Track> tracks = Map.of( //
				tmb.aerodynamic.getTitle(), tmb.aerodynamic, //
				tmb.verdisQuo.getTitle(), tmb.verdisQuo, //
				tmb.oneMoreTime.getTitle(), tmb.oneMoreTime); //

		Playlist extracted = extender.getPlaylistData(xfile, tmb.daftPunk, tracks);

		assertEquals(tmb.discovery, extracted);
	}

	@Test
	void testBundle() throws JMOPPersistenceException, XSPFException {
		XSPFFile xfile = XSPFPlaylistIO.createNew();

		Set<Track> bundleTracks = Set.of(tmb.aerodynamic, tmb.verdisQuo, tmb.oneMoreTime, tmb.getLucky);
		extender.setBundleDataAndTracks(tmb.daftPunk, bundleTracks, xfile);
		// Printer.print(0, "Bundle set", xfile);// print would fail as no tracks have
		// been set

		Bundle extracted = extender.getBundleData(xfile);

		assertEquals(tmb.daftPunk, extracted);
	}

/////////////////////////////////////////////////////////////////////////////////////

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
		assertThrows(JMOPPersistenceException.class, () -> io.load(file));
		io.tryLoadOrCreate(file);

		// So create new and save it.
		XSPFFile xspf = XSPFPlaylistIO.createNew();
		// do something ...
		io.save(xspf, file);

		io.load(file);

	}

}
