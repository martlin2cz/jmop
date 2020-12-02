package cz.martlin.jmop.common.storages.simplefs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.data.Tracklist;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;
import javafx.util.Duration;

public class SimpleFileSystemedMusicbase implements BaseMusicbase {

	private final File root;
	private final BaseFileSystemAccessor fs;

	public SimpleFileSystemedMusicbase(File root, BaseFileSystemAccessor fs) {
		super();
		this.root = root;
		this.fs = fs;
	}

	@Override
	public List<String> bundlesNames() throws JMOPSourceException {
		return fs.listDirectoriesMatching(root, d -> true) //
				.stream() //
				.map(d -> d.getName()) //
				.collect(Collectors.toList());
	}

	@Override
	public Bundle getBundle(String name) {
		Metadata metadata = Metadata.createNew();
		return new Bundle(name, metadata);
	}

	@Override
	public List<String> playlistsNames(Bundle bundle) throws JMOPSourceException {
		String bundleName = bundle.getName();
		File dir = new File(root, bundleName);
		return fs.listFilesMatching(dir, f -> isTracklistFile(f)) //
				.stream() //
				.map(f -> tracklistFileToPlaylistName(f)) //
				.collect(Collectors.toList());
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		Metadata metadata = Metadata.createNew();
		File bundleDir = new File(root, bundle.getName());
		File playlistFile = new File(bundleDir, name);
		Tracklist tracklist = loadTracklist(bundle, playlistFile);
		int currentTrackIndex = 0;
		return new Playlist(bundle, name, tracklist, currentTrackIndex, metadata);
	}

	@Override
	public List<String> listTracksIDs(Bundle bundle) throws JMOPSourceException {
		String bundleName = bundle.getName();
		File dir = new File(root, bundleName);
		return fs.listFilesMatching(dir, f -> isTrackFile(f)) //
				.stream() //
				.map(f -> trackFileToTrackId(f)) //
				.collect(Collectors.toList());
	}

	@Override
	public Track getTrack(Bundle bundle, String id) {
		Metadata metadata = Metadata.createNew();
		Duration duration = DurationUtilities.createDuration(0, 3, 15);
		String description = id;
		String title = id;
		return new Track(bundle, id, title, description, duration, metadata);
	}

	@Override
	public Bundle createBundle(String name) throws JMOPSourceException {
		File bundleDir = new File(root, name);
		fs.createDirectory(bundleDir);
		Metadata metadata = Metadata.createNew();
		return new Bundle(name, metadata);
	}

	@Override
	public void renameBundle(Bundle bundle, String newName) throws JMOPSourceException {
		String oldName = bundle.getName();
		File newBundleDir = new File(root, oldName);
		File oldBundleDir = new File(root, newName);

		fs.renameDirectory(oldBundleDir, newBundleDir);

		bundle.setName(newName);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPSourceException {
		String name = bundle.getName();
		File bundleDir = new File(root, name);
		fs.deleteDirectory(bundleDir);
	}

	@Override
	public void updateMetadata(Bundle bundle, Metadata newMetadata) {
		// ignore, since we don't store metadata
	}

	@Override
	public Playlist createPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		File bundleDir = new File(root, bundle.getName());
		File playlistFile = new File(bundleDir, name);
		fs.createEmptyFile(playlistFile);
		Metadata metadata = Metadata.createNew();
		return new Playlist(bundle, name, metadata);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String newName) throws JMOPSourceException {
		File bundleDir = new File(root, playlist.getBundle().getName());
		String oldName = playlist.getName();
		File oldBundleDir = new File(bundleDir, oldName);
		File newBundleDir = new File(bundleDir, newName);

		fs.moveFile(oldBundleDir, newBundleDir);

		playlist.setName(newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPSourceException {
		File oldBundleDir = new File(root, playlist.getBundle().getName());
		File newBundleDir = new File(root, newBundle.getName());

		String name = playlist.getName();
		File oldPlaylistFile = new File(oldBundleDir, name);
		File newPlaylistFile = new File(newBundleDir, name);

		fs.moveFile(oldPlaylistFile, newPlaylistFile);

		playlist.setBundle(newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPSourceException {
		File bundleDir = new File(root, playlist.getBundle().getName());
		File playlistFile = new File(bundleDir, playlist.getName());
		fs.deleteFile(playlistFile);
	}

	@Override
	public void updateMetadata(Playlist playlist, Metadata newMetadata) {
		// ignore, since we don't store metadata
	}
	
	@Override
	public void saveModifiedTracklist(Playlist playlist) throws JMOPSourceException {
		Bundle bundle = playlist.getBundle();
		Tracklist tracklist = playlist.getTracks();
		
		File bundleDir = new File(root, playlist.getBundle().getName());
		File playlistFile = new File(bundleDir, playlist.getName());
		saveTracklist(tracklist, playlistFile);
	}

	@Override
	public Track createTrack(Bundle bundle, TrackData data) throws JMOPSourceException {
		File bundleDir = new File(root, bundle.getName());
		File trackFile = new File(bundleDir, data.getIdentifier());
		fs.createEmptyFile(trackFile);
		Metadata metadata = Metadata.createNew();
		return new Track(bundle, data.getIdentifier(), data.getTitle(), data.getDescription(), data.getDuration(),
				metadata);
	}

	@Override
	public void renameTrack(Track track, String newTitle) throws JMOPSourceException {
		String oldIdentifier = track.getIdentifier();
		File newBundleDir = new File(root, oldIdentifier);
		File oldBundleDir = new File(root, newTitle); // TODO FIXME id === title !?

		fs.renameDirectory(oldBundleDir, newBundleDir);

		track.setIdentifier(newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle newBundle) throws JMOPSourceException {
		File oldBundleDir = new File(root, track.getBundle().getName());
		File newBundleDir = new File(root, newBundle.getName());

		String identifier = track.getIdentifier();
		File oldTrackFile = new File(oldBundleDir, identifier);
		File newTrackFile = new File(newBundleDir, identifier);

		fs.moveFile(oldTrackFile, newTrackFile);

		track.setBundle(newBundle);
	}

	@Override
	public void removeTrack(Track track) throws JMOPSourceException {
		File bundleDir = new File(root, track.getBundle().getName());
		File trackFile = new File(bundleDir, track.getIdentifier());
		fs.deleteFile(trackFile);
	}

	@Override
	public void updateMetadata(Track track, Metadata newMetadata) {
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

	private Tracklist loadTracklist(Bundle bundle, File playlistFile) throws JMOPSourceException {
		return new Tracklist( //
				fs.loadLines(playlistFile) //
						.stream() //
						.map(t -> getTrack(bundle, t)) //
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
