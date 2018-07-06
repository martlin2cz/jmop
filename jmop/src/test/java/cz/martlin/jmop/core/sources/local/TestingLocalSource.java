package cz.martlin.jmop.core.sources.local;

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
import cz.martlin.jmop.core.sources.download.TestingDownloader;

public class TestingLocalSource implements BaseLocalSource {

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
	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException {
		return tracks.get(bundle).get(identifier);
	}

	@Override
	public File fileOfTrack(Track track, TrackFileFormat downloadFileFormat) throws JMOPSourceException {
		InputStream ins = getClass().getClassLoader().getResourceAsStream(TestingDownloader.TESTING_SAMPLE_FILE);

		String name = track.getTitle();
		try {
			File file = File.createTempFile(name + "_", "." + TrackFileFormat.OPUS.getExtension());
			Files.copy(ins, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return file;
		} catch (IOException e) {
			throw new JMOPSourceException("Failed", e);
		}

	}

	@Override
	public boolean exists(Track track) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		String id = track.getIdentifier();

		return tracks.get(bundle).containsKey(id);
	}

	public void print(PrintStream to) {
		for (Bundle bundle : bundles.values()) {
			to.println("BUNDLE: " + bundle.getName() + ", kind: " + bundle.getKind());

			for (Playlist playlist : playlists.get(bundle).values()) {
				to.println(
						" - PLAYLIST: " + playlist.getName() + ", track: " + playlist.getTracks().getTracks().size());
			}

			for (Track track : tracks.get(bundle).values()) {
				to.println(" - TRACK: " + track.getTitle() + ", id: " + track.getIdentifier() + ", description: "
						+ track.getDescription());
			}

			to.println();
		}

	}

}
