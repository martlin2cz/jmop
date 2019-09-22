package cz.martlin.jmop.core.sources.locals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public class TestingLocalSource implements XXX_BaseLocalSource {

	private final Map<String, Bundle> bundles;
	private final Map<Bundle, Map<String, Playlist>> playlists;
	private final Map<Bundle, Map<String, Track>> tracks;

	public TestingLocalSource() {
		this.bundles = new LinkedHashMap<>();
		this.playlists = new LinkedHashMap<>();
		this.tracks = new LinkedHashMap<>();
	}

	@Override
	public List<String> listBundlesNames() {
		return new ArrayList<>(bundles.keySet());
	}

	@Override
	public Bundle getBundle(String name) {
		return bundles.get(name);
	}

	@Override
	public void createBundle(Bundle bundle) {
		String name = bundle.getName();
		bundles.put(name, bundle);

		playlists.put(bundle, new LinkedHashMap<>());
		tracks.put(bundle, new LinkedHashMap<>());
	}

	@Override
	public List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException {
		return new ArrayList<>(playlists.get(bundle).keySet());
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		return playlists.get(bundle).get(name);
	}

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException {
		Map<String, Playlist> map = playlists.get(bundle);
		String name = playlist.getName();
		map.put(name, playlist);
	}

	@Override
	public void saveBundle(Bundle bundle) throws JMOPSourceException {
		// okay
	}

	@Override
	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException {
		return tracks.get(bundle).get(identifier);
	}

	@Override
	public File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {

		String title = track.getTitle();
		try {
			String prefix = "_" + location.name() + "_"; //$NON-NLS-1$ //$NON-NLS-2$
			String suffix = "." + format.getExtension(); //$NON-NLS-1$
			String name = prefix + title;
			File file = File.createTempFile(name, suffix);
			
			InputStream ins = TestingTrackFileAccessor.read();
			Files.copy(ins , file.toPath(), StandardCopyOption.REPLACE_EXISTING); // TODO
																					// wtf?
			return file;
		} catch (IOException e) {
			throw new JMOPSourceException("Failed", e); //$NON-NLS-1$
		}

	}

	@Override
	public boolean exists(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException {
		File file = fileOfTrack(track, location, format);
		return file.exists();
	}

	public void print(PrintStream to) {
		for (Bundle bundle : bundles.values()) {
			to.println("BUNDLE: " + bundle.getName() + ", kind: " + bundle.getKind()); //$NON-NLS-1$ //$NON-NLS-2$

			for (Playlist playlist : playlists.get(bundle).values()) {
				to.println(
						" - PLAYLIST: " + playlist.getName() + ", track: " + playlist.getTracks().getTracks().size()); //$NON-NLS-1$ //$NON-NLS-2$
			}

			for (Track track : tracks.get(bundle).values()) {
				to.println(" - TRACK: " + track.getTitle() + ", id: " + track.getIdentifier() + ", description: " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ track.getDescription());
			}

			to.println();
		}

	}

}
