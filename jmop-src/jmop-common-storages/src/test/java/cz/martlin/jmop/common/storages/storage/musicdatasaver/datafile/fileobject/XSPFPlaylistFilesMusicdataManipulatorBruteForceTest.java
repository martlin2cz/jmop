package cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.XSPFPlaylistFilesMusicdataManipulator;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.resources.TestingResources;
import cz.martlin.jmop.common.testing.resources.TestingTracksSource;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

class XSPFPlaylistFilesMusicdataManipulatorBruteForceTest {

	private static final boolean OKAY = false;
	private static final boolean FAIL = true;

	@RegisterExtension
	public final TestingMusicdataExtension tme;

	private final TestingTracksSource trackSource;
	private final XSPFPlaylistFilesMusicdataManipulator failsaveManipulator;
	private final XSPFPlaylistFilesMusicdataManipulator weakManipulator;

	public XSPFPlaylistFilesMusicdataManipulatorBruteForceTest() {
		this.tme = TestingMusicdataExtension.simple(TrackFileFormat.MP3);
		this.trackSource = new TestingTracksSource(TrackFileFormat.MP3);

		BaseErrorReporter reporter = new SimpleErrorReporter();
		this.weakManipulator =  XSPFPlaylistFilesMusicdataManipulator.createWeak(reporter);
		this.failsaveManipulator =  XSPFPlaylistFilesMusicdataManipulator.createFailsave(reporter);
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testNotExistingFile() throws JMOPPersistenceException {
		testLoadAndSave(null, false, //
				FAIL, OKAY, FAIL, OKAY, // weak: LB, SB, LP, SP
				FAIL, OKAY, FAIL, OKAY); // fsv: LB, SB, LP, SP
	}

	@Test
	void testEmptyPlaylist() throws JMOPPersistenceException {
		testLoadAndSave("empty-playlist.xspf", false, //
				FAIL, OKAY, FAIL, OKAY, // weak: LB, SB, LP, SP
				FAIL, OKAY, OKAY, OKAY); // fsv: LB, SB, LP, SP
	}

	@Test
	void testMinimalFile() throws JMOPPersistenceException {
		testLoadAndSave("minimal-discovery.xspf", true, //
				FAIL, OKAY, FAIL, OKAY, // weak: LB, SB, LP, SP
				FAIL, OKAY, OKAY, OKAY); // fsv: LB, SB, LP, SP
	}

	@Test
	void testFileWithNoMetas() throws JMOPPersistenceException {
		testLoadAndSave("discovery-with-no-metas.xspf", true, //
				FAIL, OKAY, FAIL, OKAY, // weak: LB, SB, LP, SP
				FAIL, OKAY, OKAY, OKAY); // fsv: LB, SB, LP, SP
	}
	
	@Test
	void testFileWithOtheExtension() throws JMOPPersistenceException {
		testLoadAndSave("discovery-with-other-extension.xspf", true, //
				FAIL, OKAY, FAIL, OKAY, // weak: LB, SB, LP, SP
				FAIL, OKAY, OKAY, OKAY); // fsv: LB, SB, LP, SP
	}

	@Test
	void testFileWithMetasAsChildren() throws JMOPPersistenceException {
		testLoadAndSave("discovery-with-jmop-metas-as-children.xspf", true, //
				FAIL, OKAY, FAIL, OKAY, // weak: LB, SB, LP, SP
				FAIL, OKAY, OKAY, OKAY); // fsv: LB, SB, LP, SP
	}

	@Test
	void testFileWithMetasAsAttrs() throws JMOPPersistenceException {
		testLoadAndSave("discovery-with-jmop-metas-as-attrs.xspf", true, //
				OKAY, OKAY, OKAY, OKAY, // weak: LB, SB, LP, SP
				OKAY, OKAY, OKAY, OKAY); // fsv: LB, SB, LP, SP
	}

	@Test
	void testFileWithIcorrectValues() throws JMOPPersistenceException {
		testLoadAndSave("discovery-with-incorrect-values.xspf", true, //
				FAIL, FAIL, FAIL, FAIL, // weak: LB, SB, LP, SP
				OKAY, OKAY, OKAY, OKAY); // fsv: LB, SB, LP, SP
	}
/////////////////////////////////////////////////////////////////////////////////////

	private void testLoadAndSave(String playlistFileName, boolean checkEquality, //
			boolean mayWeakBundleLoadFail, boolean mayWeakBundleSaveFail, //
			boolean mayWeakPlaylistLoadFail, boolean mayWeakPlaylistSaveFail, //
			boolean mayFailsaveBundleLoadFail, boolean mayFailsaveBundleSaveFail, //
			boolean mayFailsavePlaylistLoadFail, boolean mayFailsavePlaylistSaveFail //
	) throws JMOPPersistenceException {

		testLoadAndSave(playlistFileName, weakManipulator, checkEquality, //
				mayWeakBundleLoadFail, mayWeakBundleSaveFail, //
				mayWeakPlaylistLoadFail, mayWeakPlaylistSaveFail);

		testLoadAndSave(playlistFileName, failsaveManipulator, checkEquality, //
				mayFailsaveBundleLoadFail, mayFailsaveBundleSaveFail, //
				mayFailsavePlaylistLoadFail, mayFailsavePlaylistSaveFail);

	}

	private void testLoadAndSave(String playlistFileName, XSPFPlaylistFilesMusicdataManipulator manipulator,
			boolean checkEquality, //
			boolean mayBundleLoadFail, boolean mayBundleSaveFail, //
			boolean mayPlaylistLoadFail, boolean mayPlaylistSaveFail) throws JMOPPersistenceException {

		checkAndLoadBundle(manipulator, playlistFileName, checkEquality, mayBundleLoadFail);
		checkAndSaveBundle(manipulator, playlistFileName, mayBundleSaveFail);

		checkAndLoadPlaylist(manipulator, playlistFileName, checkEquality, mayPlaylistLoadFail);
		checkAndSavePlaylist(manipulator, playlistFileName, mayPlaylistSaveFail);
	}

	private void checkAndLoadBundle(XSPFPlaylistFilesMusicdataManipulator manipulator, String playlistFileName,
			boolean checkEquality, boolean mayBundleLoadFail) throws JMOPPersistenceException {

		File file = pickFile(playlistFileName);

		if (mayBundleLoadFail) {
			assertFails(() -> manipulator.loadBundleData(file), manipulator, playlistFileName);
		} else {
			try {
				Bundle loaded = manipulator.loadBundleData(file);
				if (checkEquality) {
					assertEquals(tme.tmd.daftPunk, loaded);
				}
			} catch (JMOPPersistenceException e) {
				String manName = manipulatorName(manipulator);
				fail("Load bundle from " + file + " by " + manName + " failed", e);
			}
		}
	}

	private void checkAndLoadPlaylist(XSPFPlaylistFilesMusicdataManipulator manipulator, String playlistFileName,
			boolean checkEquality, boolean mayPlaylistLoadFail) throws JMOPPersistenceException {

		File file = pickFile(playlistFileName);
		Map<String, Track> tracksMap = tracksMap();

		if (mayPlaylistLoadFail) {
			assertFails(() -> manipulator.loadPlaylistData(tme.tmd.daftPunk, tracksMap, file), manipulator,
					playlistFileName);
		} else {
			try {
				Playlist loaded = manipulator.loadPlaylistData(tme.tmd.daftPunk, tracksMap, file);
				if (checkEquality) {
					assertEquals(tme.tmd.discovery, loaded);
				}
			} catch (JMOPPersistenceException e) {
				String manName = manipulatorName(manipulator);
				fail("Load playlist from " + file + " by " + manName + " failed", e);
			}
		}
	}

	private void checkAndSaveBundle(XSPFPlaylistFilesMusicdataManipulator manipulator, String playlistFileName,
			boolean mayBundleSaveFail) throws JMOPPersistenceException {

		File file = pickFile(playlistFileName);
		Set<Track> tracksSet = Set.of(tme.tmd.oneMoreTime, tme.tmd.verdisQuo, tme.tmd.aerodynamic, tme.tmd.getLucky);
		
		if (mayBundleSaveFail) {
			assertFails(() -> manipulator.saveBundleData(tme.tmd.daftPunk, tracksSet, file), manipulator,
					playlistFileName);
		} else {
			try {
				manipulator.saveBundleData(tme.tmd.daftPunk, tracksSet, file);
			} catch (JMOPPersistenceException e) {
				String manName = manipulatorName(manipulator);
				fail("Save bundle to " + file + " by " + manName + " failed", e);
			}
		}
	}

	private void checkAndSavePlaylist(XSPFPlaylistFilesMusicdataManipulator manipulator, String playlistFileName,
			boolean mayPlaylistSaveFail) throws JMOPPersistenceException {

		File file = pickFile(playlistFileName);

		if (mayPlaylistSaveFail) {
			assertFails(() -> manipulator.savePlaylistData(tme.tmd.discovery, file), manipulator,
					playlistFileName);
		} else {
			try {
				manipulator.savePlaylistData(tme.tmd.discovery, file);
			} catch (JMOPPersistenceException e) {
				String manName = manipulatorName(manipulator);
				fail("Save playlist to " + file + " by " + manName + " failed", e);
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private Map<String, Track> tracksMap() {
		return Map.of( //
				tme.tmd.aerodynamic.getTitle(), tme.tmd.aerodynamic, //
				tme.tmd.verdisQuo.getTitle(), tme.tmd.verdisQuo, //
				tme.tmd.oneMoreTime.getTitle(), tme.tmd.oneMoreTime); //
	}

	private void assertFails(Executable exec, XSPFPlaylistFilesMusicdataManipulator manipulator, String playlistFileName) {
		String manName = manipulatorName(manipulator);

		Exception ex = assertThrows(JMOPPersistenceException.class, exec, //
				"The " + manName + " may fail on " + playlistFileName + " , but didn't");

		System.err.println(ex);
	}

	private File pickFile(String resourceName) {
		try {
			if (resourceName != null) {
				return TestingResources.prepareResource(this, resourceName);
			} else {
				return prepareEmptyFile();
			}
		} catch (IOException e) {
			assumeTrue(e != null, e.toString());
			return null;
		}
	}

	private File prepareEmptyFile() throws IOException {
		File file;
		file = File.createTempFile("playlist-", ".xspf");
		Files.delete(file.toPath());

		System.out.println("Ready (non-existing) file: " + file);
		return file;
	}

	private String manipulatorName(XSPFPlaylistFilesMusicdataManipulator manipulator) {
		if (manipulator == failsaveManipulator) {
			return "failsave";
		}
		if (manipulator == weakManipulator) {
			return "weak";
		}

		return "???";
	}
}
