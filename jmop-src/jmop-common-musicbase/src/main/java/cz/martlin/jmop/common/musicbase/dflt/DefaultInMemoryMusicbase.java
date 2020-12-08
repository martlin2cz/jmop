package cz.martlin.jmop.common.musicbase.dflt;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class DefaultInMemoryMusicbase implements BaseInMemoryMusicbase {

	// TODO replace with something like MappingWithNameAsAlphabeticallyOrderedKey
	// with add,remove,rename API
	// thing about the case, where the key would be Couple<Bundle, name>
	private final List<Bundle> bundles;
	private final List<Playlist> playlists;
	private final List<Track> tracks;

	public DefaultInMemoryMusicbase() {
		this.bundles = new LinkedList<>();
		this.playlists = new LinkedList<>();
		this.tracks = new LinkedList<>();
	}

	public Set<Bundle> bundles() {
		return new TreeSet<>(bundles);
	}

	public Set<Playlist> playlists(Bundle bundle) {
		return playlists.stream() //
				.filter(p -> p.getBundle() == bundle) //
				.collect(Collectors.toSet()); //
	}

	public Set<Track> tracks(Bundle bundle) {
		return tracks.stream() //
				.filter(t -> t.getBundle() == bundle) //
				.collect(Collectors.toSet()); //
	}

	@Override
	public Bundle createBundle(String name) {
		Metadata metadata = Metadata.createNew();
		Bundle bundle = new Bundle(name, metadata);

		bundles.add(bundle);
		return bundle;
	}

	@Override
	public void renameBundle(Bundle bundle, String newName) {
		bundle.setName(newName);
	}

	@Override
	public void removeBundle(Bundle bundle) {
		bundles.remove(bundle);
	}

	@Override
	public void bundleUpdated(Bundle bundle) {
		// nothing to do here
	}

	@Override
	public Playlist createPlaylist(Bundle bundle, String name) {
		Metadata metadata = Metadata.createNew();
		Playlist playlist = new Playlist(bundle, name, metadata);

		playlists.add(playlist);
		return playlist;
	}

	@Override
	public void renamePlaylist(Playlist playlist, String newName) {
		playlist.setName(newName);

	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle newBundle) {
		playlist.setBundle(newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		playlists.remove(playlist);
	}

	@Override
	public void playlistUpdated(Playlist playlist) throws JMOPSourceException {
		// nothing to do here
	}

	@Override
	public Track createTrack(Bundle bundle, TrackData data) {
		Metadata metadata = Metadata.createNew();
		Track track = new Track(bundle, //
				data.getIdentifier(), //
				data.getTitle(), //
				data.getDescription(), //
				data.getDuration(), //
				metadata); //

		tracks.add(track);
		return track;
	}

	@Override
	public void renameTrack(Track track, String newTitle) {
		track.setTitle(newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle newBundle) {
		track.setBundle(newBundle);
	}

	@Override
	public void removeTrack(Track track) {
		tracks.remove(track);
	}

	@Override
	public void trackUpdated(Track track) throws JMOPSourceException {
		// nothing to do here
	}

}
