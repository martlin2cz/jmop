package cz.martlin.jmop.common.storages.simples;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

public class SimpleExtendedPlaylistManipulator implements BaseExtendedPlaylistManipulator {

	private final BaseFileSystemAccessor fs;

	public SimpleExtendedPlaylistManipulator(BaseFileSystemAccessor fs) {
		super();
		this.fs = fs;
	}

	@Override
	public String fileExtension() {
		return "txt";
	}

	@Override
	public void savePlaylistWithBundle(Playlist playlist, File file)  {
		Tracklist tracks = playlist.getTracks();
		saveTracklist(tracks, file);
		// we are ignoring the bundle info, #sorryjako
	}

	@Override
	public void saveOnlyPlaylist(Playlist playlist, File file)  {
		Tracklist tracks = playlist.getTracks();
		saveTracklist(tracks, file);
	}

	@Override
	public Bundle loadOnlyBundle(File file)  {
		String name = file.getParentFile().getName();

		Metadata metadata = Metadata.createNew(); // as I said, we simply ignore the metatada and so
		return new Bundle(name, metadata);
	}

	@Override
	public Playlist loadOnlyPlaylist(Bundle bundle, Map<String, Track> tracks, File file)  {
		String name = file.getName().replace("." + fileExtension(), "");

		Metadata metadata = Metadata.createNew();
		TrackIndex currentTrackIndex = TrackIndex.ofIndex(0);
		Tracklist tracklist = loadTracklist(bundle, file);
		return new Playlist(bundle, name, tracklist, currentTrackIndex, metadata);
	}

	private void saveTracklist(Tracklist tracklist, File file)  {
		List<String> lines = tracklist //
				.getTracks() //
				.stream() //
				.map(Track::getTitle) //
				.collect(Collectors.toList()); //

		try {
			fs.saveLines(file, lines);
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not save tracklist", e);
		}
	}

	private Tracklist loadTracklist(Bundle bundle, File file)  {
		Metadata metadata = Metadata.createNew();
		Duration duration = DurationUtilities.createDuration(0, 3, 15);

		try {
			return new Tracklist(//
					fs.loadLines(file) //
							.stream() //
							.map(l -> new Track(bundle, l, l, l, duration, metadata)) //
							.collect(Collectors.toList()));
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not load tracklist", e);
		} //
	}

}
