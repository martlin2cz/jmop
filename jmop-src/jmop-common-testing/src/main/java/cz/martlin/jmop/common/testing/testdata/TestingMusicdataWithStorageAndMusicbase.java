package cz.martlin.jmop.common.testing.testdata;

import java.io.File;
import java.net.URI;
import java.util.Objects;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
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
	

	public TestingMusicdataWithStorageAndMusicbase(BaseMusicbaseModifing musicbase, BaseMusicbaseStorage storage, TrackFileFormat trackFileOrNot) {
		super(trackFileOrNot);

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
	protected Track createTheTrack(Bundle bundle, String title, String description, Duration duration, TrackFileFormat trackFileOrNot,
			URI uri) {

		File trackFile = trackFileOrNot != null ? TestingResources.prepareSampleTrack(this, trackFileOrNot) : null;
		TrackFileCreationWay trackCreationWay = trackFileOrNot != null ? TrackFileCreationWay.COPY_FILE : TrackFileCreationWay.NO_FILE;
		
		TrackData data = new TrackData(title, description, duration, uri);
		Track track = musicbase.createNewTrack(bundle, data, trackCreationWay, trackFile);

		storage.createTrack(track, trackCreationWay, trackFile);
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
