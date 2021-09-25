package cz.martlin.jmop.common.testing.testdata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.testing.resources.TestingResources;
import cz.martlin.jmop.common.testing.resources.TestingTrackFilesCreator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

/**
 * The testing musicdata, which creates the provided resources in the provided
 * musicbase.
 * 
 * @author martin
 *
 */
public class TestingMusicdataWithMusicbase extends AbstractTestingMusicdata {
	
	private final BaseMusicbaseModifing musicbase;

	public TestingMusicdataWithMusicbase(BaseMusicbaseModifing musicbase, boolean fileExisting) {
		super(fileExisting);
		Objects.requireNonNull(musicbase, "The musicbase is not provided");
		
		this.musicbase = musicbase;
		
		prepare();
	}

	///////////////////////////////////////////////////////////////////////////

	protected void createDaftPunk() {
		super.createDaftPunk();

		musicbase.playlistUpdated(discovery);
		musicbase.playlistUpdated(randomAccessMemories);
	}

	protected void createLondonElektricity() {
		super.createLondonElektricity();

		musicbase.playlistUpdated(syncopatedCity);
		musicbase.playlistUpdated(yikes);
		musicbase.playlistUpdated(bestTracks);
	}

	protected void createCocolinoDeep() {
		super.createCocolinoDeep();

		musicbase.playlistUpdated(seventeen);
	}

	protected void createRobick() {
		super.createRobick();
	}

	///////////////////////////////////////////////////////////////////////////

	protected void deleteDaftPunk() {
		super.deleteDaftPunk();
	}

	protected void deleteLondonElektricity() {
		super.deleteLondonElektricity();
	}

	protected void deleteCocolinoDeep() {
		super.deleteCocolinoDeep();
	}

	protected void deleteRobick() {
		super.deleteRobick();
	}

///////////////////////////////////////////////////////////////////////////

	@Override
	protected Bundle createTheBundle(String name) {
		return musicbase.createNewBundle(name);
	}

	@Override
	protected Playlist createThePlaylist(Bundle bundle, String name) {
		return musicbase.createNewPlaylist(bundle, name);
	}

	@Override
	protected Track createTheTrack(Bundle bundle, String title, String description, String id, Duration duration,
			boolean fileExisting) {

		InputStream trackFileContents = fileExisting ? TestingResources.loadSampleTrack(this, TrackFileFormat.MP3) : null;
		TrackData data = new TrackData(id, title, description, duration);
		return musicbase.createNewTrack(bundle, data, trackFileContents);
	}

	@Override
	protected Track deleteTheTrack(Track track) {
		musicbase.removeTrack(track);
		return null;
	}

	@Override
	protected Playlist deleteThePlaylist(Playlist playlist) {
		musicbase.removePlaylist(playlist);
		return null;
	}

	@Override
	protected Bundle deleteTheBundle(Bundle bundle) {
		musicbase.removeBundle(bundle);
		return null;
	}

}
