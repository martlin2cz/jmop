package cz.martlin.jmop.common.testing.testdata;

import java.io.File;
import java.util.Objects;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.testing.resources.TestingResources;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

/**
 * The testing musicdata which creates the testing musicdata in the provided
 * storage.
 * 
 * @author martin
 * @deprecated use {@link TestingMusicdataWithStorageAndMusicbase} instead (as many of the storages reqires pre-filled musicbase too)
 */
@Deprecated
public class TestingMusicdataWithStorage extends AbstractTestingMusicdata {

	private final BaseMusicbaseStorage storage;

	public TestingMusicdataWithStorage(BaseMusicbaseStorage storage, TrackFileFormat trackFileOrNot) {
		super(trackFileOrNot);
		Objects.requireNonNull(storage, "The storage is not provided");
		
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
		Bundle bundle = TestingDataCreator.bundle(null, name);
		storage.createBundle(bundle);
		return bundle;
	}

	@Override
	protected Playlist createThePlaylist(Bundle bundle, String name) {
		Playlist playlist = TestingDataCreator.playlist(null, bundle, name);
		storage.createPlaylist(playlist);
		return playlist;
	}

	@Override
	protected Track createTheTrack(Bundle bundle, String title, String description, String id, Duration duration,
			TrackFileFormat trackFileOrNot) {

		Track track = TestingDataCreator.track(null, bundle, title, description, id, duration, trackFileOrNot);
		File trackFile = trackFileOrNot != null ? TestingResources.prepareSampleTrack(this, trackFileOrNot) : null;

		storage.createTrack(track, TrackFileCreationWay.COPY_FILE, trackFile);
		return track;
	}

	@Override
	protected Track deleteTheTrack(Track track) {
		storage.removeTrack(track);
		return null;
	}

	@Override
	protected Playlist deleteThePlaylist(Playlist playlist) {
		storage.removePlaylist(playlist);
		return null;
	}

	@Override
	protected Bundle deleteTheBundle(Bundle bundle) {
		storage.removeBundle(bundle);
		return null;
	}

}
