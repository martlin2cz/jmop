package cz.martlin.jmop.common.storages.simples;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
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
	public void savePlaylistWithBundle(Playlist playlist, File file) throws JMOPSourceException {
		Tracklist tracks = playlist.getTracks();
		saveTracklist(tracks, file);
		// we are ignoring the bundle info, #sorryjako
	}

	@Override
	public void saveOnlyPlaylist(Playlist playlist, File file) throws JMOPSourceException {
		Tracklist tracks = playlist.getTracks();
		saveTracklist(tracks, file);
	}

	@Override
	public Bundle loadOnlyBundle(File file) throws JMOPSourceException {
		String name = file.getParentFile().getName();

		Metadata metadata = Metadata.createNew(); // as I said, we simply ignore the metatada and so
		return new Bundle(name, metadata);
	}

	@Override
	public Playlist loadOnlyPlaylist(Bundle bundle, Map<String, Track> tracks, File file) throws JMOPSourceException {
		String name = file.getName().replace("." + fileExtension(), "");

		Metadata metadata = Metadata.createNew();
		int currentTrackIndex = 0;
		Tracklist tracklist = loadTracklist(bundle, file);
		return new Playlist(bundle, name, tracklist, currentTrackIndex, metadata);
	}

	private void saveTracklist(Tracklist tracklist, File file) throws JMOPSourceException {
		List<String> lines = tracklist //
				.getTracks() //
				.stream() //
				.map(Track::getTitle) //
				.collect(Collectors.toList()); //

		fs.saveLines(file, lines);
	}

	private Tracklist loadTracklist(Bundle bundle, File file) throws JMOPSourceException {
		Metadata metadata = Metadata.createNew();
		Duration duration = DurationUtilities.createDuration(0, 3, 15);

		return new Tracklist(//
				fs.loadLines(file) //
						.stream() //
						.map(l -> new Track(bundle, l, l, l, duration, metadata)) //
						.collect(Collectors.toList())); //
	}

}
