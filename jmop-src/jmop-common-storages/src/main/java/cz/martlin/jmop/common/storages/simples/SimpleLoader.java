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
import cz.martlin.jmop.common.storages.bundlesdir.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import javafx.util.Duration;

public class SimpleLoader implements BaseMusicdataLoader {

	private final File root;
	private final BaseFileSystemAccessor fs;

	public SimpleLoader(File root, BaseFileSystemAccessor fs) {
		super();
		this.root = root;
		this.fs = fs;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> loadBundlesNames() throws JMOPSourceException {
		return fs.listDirectories(root) //
				.map(d -> d.getName()) //
				.collect(Collectors.toList());
	}

	@Override
	public Bundle loadBundleData(File bundleDir, String bundleName) throws JMOPSourceException {
		Metadata metadata = Metadata.createNew();
		return new Bundle(bundleName, metadata);
	}

	@Override
	public List<String> loadPlaylistsNames(File bundleDir, Bundle bundle, String bundleName) throws JMOPSourceException {
		return fs.listFiles(bundleDir) //
				.filter(f -> isTracklistFile(f)) //
				.map(f -> tracklistFileToPlaylistName(f)) //
				.collect(Collectors.toList());
	}

	@Override
	public Playlist loadPlaylistData(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName)
			throws JMOPSourceException {
		Metadata metadata = Metadata.createNew();
		Tracklist tracklist = loadTracklist(bundle, playlistFile, tracks);
		int currentTrackIndex = 0;
		return new Playlist(bundle, playlistName, tracklist, currentTrackIndex, metadata);
	}

	@Override
	public List<String> loadTracksTitles(File bundleDir, Bundle bundle, String bundleName) throws JMOPSourceException {
		return fs.listFiles(bundleDir) //
				.filter(f -> isTrackFile(f)) //
				.map(f -> trackFileToTrackId(f)) //
				.collect(Collectors.toList());
	}

	@Override
	public Track loadTrackData(File trackFile, Bundle bundle, String trackTitle) {
		Metadata metadata = Metadata.createNew();
		Duration duration = DurationUtilities.createDuration(0, 3, 15);
		String id = trackTitle;
		String description = trackTitle;
		String title = trackTitle;
		return new Track(bundle, id, title, description, duration, metadata);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isTracklistFile(File file) {
		return file.getName().endsWith(".txt");
	}

	private boolean isTrackFile(File file) {
		return file.getName().endsWith(".mp3"); // TODO save format
	}

	private String tracklistFileToPlaylistName(File file) {
		return file.getName().replace(".txt", "");
	}

	private String trackFileToTrackId(File file) {
		return file.getName().replace(".mp3", "");
	}

	private Tracklist loadTracklist(Bundle bundle, File playlistFile, Map<String, Track> tracks)
			throws JMOPSourceException {
		return new Tracklist( //
				fs.loadLines(playlistFile) //
						.stream() //
						.map(t -> tracks.get(t)) //
						.collect(Collectors.toList()));
	}

}
