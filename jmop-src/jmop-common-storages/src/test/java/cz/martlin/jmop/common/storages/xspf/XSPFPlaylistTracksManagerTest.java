package cz.martlin.jmop.common.storages.xspf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager;
import cz.martlin.jmop.common.storages.playlists.BaseValueToAndFromStringConverters;
import cz.martlin.jmop.common.storages.playlists.SimpleValueToAndFromStringConverters;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.resources.TestingResources;
import cz.martlin.jmop.common.testing.resources.TestingTracksSource;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFMeta;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.ExceptionWrapper;
import cz.martlin.xspf.util.Printer;
import cz.martlin.xspf.util.XSPFException;

@TestMethodOrder(OrderAnnotation.class)
class XSPFPlaylistTracksManagerTest {

	private static final URI META_URI = URI.create("cz.jmop.xspf.extender.id");

	@RegisterExtension
	public final TestingMusicdataExtension tme;

	private final XSPFPlaylistTracksManager man;
	private final BasePlaylistMetaInfoManager<XSPFCommon> mim;
	private final XSPFPlaylistManipulator extender;
	private final TracksLocator tracks;


	public XSPFPlaylistTracksManagerTest() {
		this.tme = TestingMusicdataExtension.simple(true);
		
		BaseValueToAndFromStringConverters converters = new SimpleValueToAndFromStringConverters();
		mim = new XSPFExtensionElemsAttrsMetaInfoManager(converters);
		JMOPtoXSFPAdapter adapter = new JMOPtoXSFPAdapter(mim);
		man = new XSPFPlaylistTracksManager(adapter);
		XSPFPlaylistTracksManager tracker = new XSPFPlaylistTracksManager(adapter);
		extender = new XSPFPlaylistManipulator(adapter, tracker );

		tracks = new TestingTracksSource(TrackFileFormat.MP3);
	}

	///////////////////////////////////////////////////////////////////////////

	@Order(1)
	@Test
	void testJustLoad() throws JMOPPersistenceException, IOException {
		XSPFFile xfile = load();
		XSPFPlaylist xplaylist = xfile.playlist();

		verify(xplaylist, 1, tme.tmd.oneMoreTime, "T001");
		verify(xplaylist, 2, tme.tmd.aerodynamic, "T002");
		verify(xplaylist, 3, tme.tmd.verdisQuo, "T003"); //
	}

	@Order(2)
	@Test
	void testJustSave() throws JMOPPersistenceException, IOException {
		Playlist playlist = tme.tmd.discovery;

		XSPFPlaylist xplaylist = savePlaylist(playlist);

		verify(xplaylist, 1, tme.tmd.oneMoreTime, "T001");
		verify(xplaylist, 2, tme.tmd.aerodynamic, "T002");
		verify(xplaylist, 3, tme.tmd.verdisQuo, "T003"); //
	}

	@Order(3)
	@Test
	void testAddTrack() throws JMOPPersistenceException, IOException {
		Playlist playlist = tme.tmd.discovery;
		PlaylistModifier mod = new PlaylistModifier(playlist);
		mod.append(tme.tmd.getLucky);

		XSPFPlaylist xplaylist = savePlaylist(playlist);

		verify(xplaylist, 1, tme.tmd.oneMoreTime, "T001");
		verify(xplaylist, 2, tme.tmd.aerodynamic, "T002");
		verify(xplaylist, 3, tme.tmd.verdisQuo, "T003");
		verify(xplaylist, 4, tme.tmd.getLucky, null);
	}
	
	@Order(4)
	@Test
	void testRemoveTrack() throws JMOPPersistenceException, IOException {
		Playlist playlist = tme.tmd.discovery;
		PlaylistModifier mod = new PlaylistModifier(playlist);
		mod.remove(TrackIndex.ofHuman(2));

		XSPFPlaylist xplaylist = savePlaylist(playlist);

		verify(xplaylist, 1, tme.tmd.oneMoreTime, "T001");
		verify(xplaylist, 2, tme.tmd.verdisQuo, "T003");
	}
	
	@Order(5)
	@Test
	void testMoveTrack() throws JMOPPersistenceException, IOException {
		Playlist playlist = tme.tmd.discovery;
		PlaylistModifier mod = new PlaylistModifier(playlist);
		mod.move(TrackIndex.ofHuman(2), TrackIndex.ofHuman(1));

		XSPFPlaylist xplaylist = savePlaylist(playlist);

		verify(xplaylist, 1, tme.tmd.aerodynamic, "T002");
		verify(xplaylist, 2, tme.tmd.oneMoreTime, "T001");
		verify(xplaylist, 3, tme.tmd.verdisQuo, "T003");
	}
	
	@Order(6)
	@Test
	void testAddRemoveTrack() throws JMOPPersistenceException, IOException {
		Playlist playlist = tme.tmd.discovery;
		PlaylistModifier mod = new PlaylistModifier(playlist);
		mod.remove(TrackIndex.ofHuman(2));
		mod.append(tme.tmd.getLucky);

		XSPFPlaylist xplaylist = savePlaylist(playlist);

		verify(xplaylist, 1, tme.tmd.oneMoreTime, "T001");
		verify(xplaylist, 2, tme.tmd.verdisQuo, "T003");
		verify(xplaylist, 3, tme.tmd.getLucky, null);
	}
	
	@Order(7)
	@Test
	void testRenameTrack() throws JMOPPersistenceException, IOException {
		Playlist playlist = tme.tmd.discovery;

		tme.tmd.oneMoreTime.setTitle("One more time!");
		
		XSPFPlaylist xplaylist = savePlaylist(playlist);

		verify(xplaylist, 1, tme.tmd.oneMoreTime, "T001");
		verify(xplaylist, 2, tme.tmd.aerodynamic, "T002");
		verify(xplaylist, 3, tme.tmd.verdisQuo, "T003");
	}
	
	@Disabled("The implementation cannot cover some set of complex modifications")
	@Order(8)
	@Test
	void testComplexChanges() throws JMOPPersistenceException, IOException {
		Playlist playlist = tme.tmd.discovery;
		PlaylistModifier mod = new PlaylistModifier(playlist);
		
		mod.move(TrackIndex.ofHuman(3), TrackIndex.ofHuman(1));
		tme.tmd.oneMoreTime.setTitle("One more time!");
		mod.remove(TrackIndex.ofHuman(2));
		mod.insertBefore(tme.tmd.getLucky, TrackIndex.ofHuman(1));
		
		XSPFPlaylist xplaylist = savePlaylist(playlist);

		verify(xplaylist, 1, tme.tmd.verdisQuo, "T003");
		verify(xplaylist, 2,  tme.tmd.getLucky, null);
		verify(xplaylist, 3, tme.tmd.oneMoreTime, "T001");
	}

	///////////////////////////////////////////////////////////////////////////

	private XSPFPlaylist savePlaylist(Playlist playlist) throws JMOPPersistenceException, IOException {
		XSPFFile xfile = load();

		Tracklist tracklist = playlist.getTracks();
		man.setTracks(extender, tracklist, tracks, xfile);

		Printer.print(0, "Prepared xfile", xfile);
		return xfile.getPlaylist();
	}

	private XSPFFile load() throws IOException, XSPFException {
		File file = TestingResources.prepareResource(this, "discovery-with-metas.xspf");
		XSPFFile xfile = XSPFFile.load(file);
		return xfile;
	}

	private void verify(XSPFPlaylist xplaylist, int expectedTrackNum, Track expectedTrack, String expectedMeta)
			throws XSPFException {

		List<XSPFTrack> matchings = xplaylist.tracks().list() //
				.filter(ExceptionWrapper.wrapPredicate( //
						xt -> expectedTrackNum == xt.getTrackNum())) //
				.filter(ExceptionWrapper.wrapPredicate( //
						xt -> expectedTrack.getTitle().equals(xt.getTitle()))) //
				.collect(Collectors.toList());

		if (matchings.size() < 1) {
			fail("No such xtrack");
		}
		if (matchings.size() > 1) {
			fail("More than one such xtrack");
		}

		XSPFTrack xtrack = matchings.get(0);
		Printer.print(0, "Found track " + expectedTrackNum + ": " + expectedTrack.getTitle(), xtrack);
		
		XSPFMeta meta = xtrack.metas().meta(META_URI);
		if (expectedMeta != null) {
			assertNotNull(meta);
			assertEquals(expectedMeta, meta.getContent());
		} else {
			assertNull(meta);
		}
		
	}

}
