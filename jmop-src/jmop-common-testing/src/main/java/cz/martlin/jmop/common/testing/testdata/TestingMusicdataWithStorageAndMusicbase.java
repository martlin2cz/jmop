package cz.martlin.jmop.common.testing.testdata;

import java.io.InputStream;
import java.util.Objects;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.testing.resources.TestingResources;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

/**
 * The testing musicdata which creates the testing musicdata in the provided
 * storage and the musicbase at the same time.
 * 
 * @author martin
 */
public class TestingMusicdataWithStorageAndMusicbase extends AbstractTestingMusicdata {

	private final BaseMusicbaseModifing musicbase;
	private final BaseMusicbaseStorage storage;
	

	public TestingMusicdataWithStorageAndMusicbase(BaseMusicbaseModifing musicbase, BaseMusicbaseStorage storage, boolean fileExisting) {
		super(fileExisting);

		Objects.requireNonNull(musicbase, "The musicbase is not provided");
		Objects.requireNonNull(storage, "The storage is not provided");
		
		this.musicbase = musicbase;
		this.storage = storage;
				
		prepare();
	}

	///////////////////////////////////////////////////////////////////////////

	protected void createDaftPunk() {
		super.createDaftPunk();

		storage.saveUpdatedBundle(daftPunk);
		
		storage.saveUpdatedPlaylist(discovery);
		storage.saveUpdatedPlaylist(randomAccessMemories);
		
		storage.saveUpdatedTrack(oneMoreTime);
		storage.saveUpdatedTrack(aerodynamic);
		storage.saveUpdatedTrack(verdisQuo);
		storage.saveUpdatedTrack(getLucky);
	}

	protected void createLondonElektricity() {
		super.createLondonElektricity();

		storage.saveUpdatedPlaylist(syncopatedCity);
		storage.saveUpdatedPlaylist(yikes);
		storage.saveUpdatedPlaylist(bestTracks);
	}

	protected void createCocolinoDeep() {
		super.createCocolinoDeep();

		storage.saveUpdatedPlaylist(seventeen);
	}

	protected void createRobick() {
		super.createRobick();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected Bundle createTheBundle(String name) {
		Bundle bundle = musicbase.createNewBundle(name);
		storage.createBundle(bundle);
		return bundle;
	}

	@Override
	protected Playlist createThePlaylist(Bundle bundle, String name) {
		Playlist playlist = musicbase.createNewPlaylist(bundle, name);
		storage.createPlaylist(playlist);
		return playlist;
	}

	@Override
	protected Track createTheTrack(Bundle bundle, String title, String description, String id, Duration duration,
			boolean fileExisting) {

		InputStream trackFileContents = fileExisting ? TestingResources.loadSampleTrack(this, TrackFileFormat.MP3) : null;
		
		TrackData data = new TrackData(id, title, description, duration);
		Track track = musicbase.createNewTrack(bundle, data, trackFileContents);
		

		storage.createTrack(track, trackFileContents);
		return track;
	}

	@Override
	protected Track deleteTheTrack(Track track) {
		musicbase.removeTrack(track);
		storage.removeTrack(track);
		return null;
	}

	@Override
	protected Playlist deleteThePlaylist(Playlist playlist) {
		musicbase.removePlaylist(playlist);
		storage.removePlaylist(playlist);
		return null;
	}

	@Override
	protected Bundle deleteTheBundle(Bundle bundle) {
		musicbase.removeBundle(bundle);
		storage.removeBundle(bundle);
		return null;
	}

}
