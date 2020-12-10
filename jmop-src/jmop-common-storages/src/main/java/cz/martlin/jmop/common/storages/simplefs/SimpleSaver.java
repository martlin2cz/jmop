package cz.martlin.jmop.common.storages.simplefs;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.Tracklist;
import cz.martlin.jmop.common.musicbase.commons.BaseMusicdataSaver;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;
import javafx.util.Duration;

public class SimpleSaver implements BaseMusicdataSaver {

	private final File root;
	private final BaseFileSystemAccessor fs;

	public SimpleSaver(File root, BaseFileSystemAccessor fs) {
		super();
		this.root = root;
		this.fs = fs;
	}

	@Override
	public List<String> loadBundlesNames() throws JMOPSourceException {
		return fs.listDirectoriesMatching(root, d -> true) //
				.stream() //
				.map(d -> d.getName()) //
				.collect(Collectors.toList());
	}

	@Override
	public Bundle loadBundleData(File bundleDir, String bundleName) {
		Metadata metadata = Metadata.createNew();
		return new Bundle(bundleName, metadata);
	}

	@Override
	public List<String> loadPlaylistsNames(File bundleDir, String bundleName) throws JMOPSourceException {
		return fs.listFilesMatching(bundleDir, f -> isTracklistFile(f)) //
				.stream() //
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
	public List<String> loadTracksTitles(File bundleDir, String bundleName) throws JMOPSourceException {
		return fs.listFilesMatching(bundleDir, f -> isTrackFile(f)) //
				.stream() //
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

	@Override
	public void saveBundleData(File bundleDir, Bundle bundle) {
		// ignore, since we don't store metadata
	}

	@Override
	public void savePlaylistData(File playlistFile, Playlist playlist) throws JMOPSourceException {
		Tracklist tracklist = playlist.getTracks();
		saveTracklist(tracklist, playlistFile);
	}

	@Override
	public void saveTrackData(File trackFile, Track track) {
		// ignore, since we don't store metadata
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

	private void saveTracklist(Tracklist tracklist, File playlistFile) throws JMOPSourceException {
		fs.saveLines(playlistFile, //
				tracklist.getTracks().stream() //
						.map(t -> (t.getIdentifier() + "mp3")) //
						.collect(Collectors.toList()) //
		); //
	}
}
