package cz.martlin.jmop.common.storages.simples;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.storages.load.AbstractMusicdataLoader;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.common.storages.utils.FilesLocatorExtension;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

public class SimpleLoader extends AbstractMusicdataLoader {

	private final File root;
	private final BaseFileSystemAccessor fs;
	private final FilesLocatorExtension locator;
	private final BaseErrorReporter reporter;

	public SimpleLoader(File root, BaseFileSystemAccessor fs, BaseFilesLocator locator, BaseErrorReporter reporter) {
		super();
		this.root = root;
		this.fs = fs;
		this.locator = new FilesLocatorExtension(locator);
		this.reporter = reporter;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> loadBundlesNames() {
		try {
			return fs.listDirectories(root) //
					.map(d -> d.getName()) //
					.collect(Collectors.toList());
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not load bundle names", e);
			return Collections.emptyList();
		}
	}

	@Override
	public Bundle loadBundle(String bundleName) {
		Metadata metadata = Metadata.createNew();
		return new Bundle(bundleName, metadata);
	}

	@Override
	public List<String> loadPlaylistsNames(Bundle bundle, String bundleName) {
		try {
			File bundleDir = locator.bundleDir(bundle);
			return fs.listFiles(bundleDir) //
					.filter(f -> isTracklistFile(f)) //
					.map(f -> tracklistFileToPlaylistName(f)) //
					.collect(Collectors.toList());
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not load playlist names", e);
			return Collections.emptyList();
		}
	}

	@Override
	public Playlist loadPlaylist(Bundle bundle, Map<String, Track> tracks, String playlistName) {
		Metadata metadata = Metadata.createNew();
		File playlistFile = locator.playlistFile(bundle, playlistName);
		Tracklist tracklist = loadTracklist(bundle, playlistFile, tracks);
		int currentTrackIndex = 0;
		return new Playlist(bundle, playlistName, tracklist, currentTrackIndex, metadata);
	}

	@Override
	public List<String> loadTracksTitles(Bundle bundle, String bundleName)  {
		try {
			File bundleDir = locator.bundleDir(bundle);
			return fs.listFiles(bundleDir) //
					.filter(f -> isTrackFile(f)) //
					.map(f -> trackFileToTrackId(f)) //
					.collect(Collectors.toList());
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not load tracks titles", e);
			return Collections.emptyList();
		}
	}

	@Override
	public Track loadTrack(Bundle bundle, String trackTitle) {
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

	private Tracklist loadTracklist(Bundle bundle, File playlistFile, Map<String, Track> tracks) {
		try {
			return new Tracklist( //
					fs.loadLines(playlistFile) //
							.stream() //
							.map(t -> tracks.get(t)) //
							.collect(Collectors.toList()));
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not load tracklist", e);
			return new Tracklist();
		}
	}

}
