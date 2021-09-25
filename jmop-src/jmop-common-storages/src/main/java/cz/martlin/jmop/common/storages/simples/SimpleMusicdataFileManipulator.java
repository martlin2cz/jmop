package cz.martlin.jmop.common.storages.simples;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * Simple musicdata file manipulator. The produced playlist file contains NO
 * bundle info, NO playlist info, only the list of track titles, each on each
 * own line.
 * 
 * @author martin
 *
 */
public class SimpleMusicdataFileManipulator implements BaseMusicdataFileManipulator {

	private final BaseFileSystemAccessor fs;

	public SimpleMusicdataFileManipulator(BaseFileSystemAccessor fs) {
		super();
		this.fs = fs;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String fileExtension() {
		return "txt";
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void saveBundleData(Bundle bundle, Set<Track> tracks, File file, TracksLocator tracksSource)
			throws JMOPPersistenceException {
		saveTracks(tracks, file);
	}

	@Override
	public void savePlaylistData(Playlist playlist, File file, TracksLocator tracksSource)
			throws JMOPPersistenceException {
		Tracklist tracklist = playlist.getTracks();
		saveTracks(tracklist.getTracks(), file);

	}

	@Override
	public Bundle loadBundleData(File file) throws JMOPPersistenceException {
		String name = file.getParentFile().getName();

		Metadata metadata = Metadata.createNew(); // as I said, we simply ignore the metatada and so
		return new Bundle(name, metadata);
	}

	@Override
	public Set<Track> loadBundleTracks(File file, Bundle bundle) throws JMOPPersistenceException {
		return new HashSet<>(loadTracks(bundle, file));
	}

	@Override
	public Playlist loadPlaylistData(Bundle bundle, Map<String, Track> tracks, File file)
			throws JMOPPersistenceException {
		String name = file.getName().replace("." + fileExtension(), "");

		Metadata metadata = Metadata.createNew();
		TrackIndex currentTrackIndex = TrackIndex.ofIndex(0);
		Tracklist tracklist = new Tracklist(loadTracks(bundle, file));
		return new Playlist(bundle, name, tracklist, currentTrackIndex, metadata);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void saveTracks(Collection<Track> tracks, File file) {
		List<String> lines = tracks.stream() //
				.map(Track::getTitle) //
				.collect(Collectors.toList()); //

		try {
			fs.saveLines(file, lines);
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not save tracklist", e);
		}
	}

	private List<Track> loadTracks(Bundle bundle, File file) {
		Metadata metadata = Metadata.createNew();
		Duration duration = DurationUtilities.createDuration(0, 3, 15);

		try {
			return fs.loadLines(file) //
					.stream() //
					.map(l -> new Track(bundle, l, l, l, duration, metadata)) //
					.collect(Collectors.toList());
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not load tracklist", e);
		} //
	}

}
