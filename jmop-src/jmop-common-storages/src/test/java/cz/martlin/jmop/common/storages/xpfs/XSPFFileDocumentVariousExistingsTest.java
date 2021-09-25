package cz.martlin.jmop.common.storages.xpfs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;

@Deprecated
public class XSPFFileDocumentVariousExistingsTest {

	private final Bundle bundle = new Bundle("Daft punk", Metadata.createNew());
	private final List<Track> tracks = List.of( //
			new Track(bundle, "OMT", "One More Time", "...", //
					DurationUtilities.createDuration(0, 5, 20), Metadata.createNew()), //
			new Track(bundle, "VQ", "Veridis Quo", "...", //
					DurationUtilities.createDuration(0, 5, 44), Metadata.createNew()), //
			new Track(bundle, "AD", "Aerodynamic", "...", //
					DurationUtilities.createDuration(0, 3, 27), Metadata.createNew())); //

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testLoadCorrect() throws JMOPPersistenceException {
		testLoadCheckSave("correct.xspf", true);
	}

	
	
	@Test
	public void testLoadNoJMOP() throws JMOPPersistenceException {
		testLoadCheckSave("no-jmop.xspf", true);
	}
	
	@Test
	public void testSome() throws JMOPPersistenceException {
		//FIXME overrides, the <album> and <creator> fields gets lost by that
		testLoadCheckSave("some.xspf", true);
	}
	
	
	@Test
	public void testLoadFromVLC() throws JMOPPersistenceException {
		testLoadCheckSave("from-vlc.xspf", false);
	}

	
	@Test
	public void testLoadFromParole() throws JMOPPersistenceException {
		testLoadCheckSave("from-parole.xspf", false);
	}
	
	@Test
	public void testLoadIncorrectAttrVals() throws JMOPPersistenceException {
		testLoadCheckSave("incorrect-attrvals.xspf", true);
	}
	
	@Test
	public void testLoadIncorrectSyntax() throws JMOPPersistenceException {
		assertThrows(JMOPPersistenceException.class, //
				() -> load("incorrect-syntax.xspf"));
	}

	///////////////////////////////////////////////////////////////////////////
	private void testLoadCheckSave(String fileName, boolean verify) throws JMOPPersistenceException {
		Playlist playlist = load(fileName);
		
		assertNotNull(playlist);
		if (verify) {
			assertEquals("Discovery", playlist.getName());
			assertEquals(3, playlist.getTracks().count());
		}
		
		save(fileName, playlist);
	}
	private Playlist load(String name) throws JMOPPersistenceException {
		System.out.println("Loading testing playlist " + name + ":");
		
		BaseErrorReporter reporter = new SimpleErrorReporter();
		_old_XSPFFilesManipulator xspf = new _old_XSPFFilesManipulator(reporter);
		throw new UnsupportedOperationException();
//		File file = TestingXSPFFiles.fileToReadAssumed(name);
//		System.out.println("Loading from file: " + file.getAbsolutePath());
//		
//		Map<String, Track> tracksMap = tracks.stream()//
//				.collect(Collectors.toMap(t -> t.getTitle(), t -> t));
//		
//		Playlist playlist = xspf.loadOnlyPlaylist(bundle, tracksMap, file);
//		MusicbaseDebugPrinter.print(playlist);
//		
//		return playlist;
	}
	
	private void save(String fileName, Playlist playlist) throws JMOPPersistenceException {
		System.out.println("Saving testing playlist " + playlist.getName() + ":");
		
		BaseErrorReporter reporter = new SimpleErrorReporter();
		_old_XSPFFilesManipulator xspf = new _old_XSPFFilesManipulator(reporter);
		throw new UnsupportedOperationException();
//		File file = TestingXSPFFiles.fileToWriteAssumed(fileName);
//		System.out.println("Saving to file: " + file.getAbsolutePath());
		
//		xspf.saveOnlyPlaylist(playlist, file);
	}



	

}
