package cz.martlin.jmop.common.musicbase.dflt;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import javafx.util.Duration;

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

	/////////////////////////////////////////////////////////////////////////////////////

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

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void addBundle(Bundle bundle) throws JMOPSourceException {
		bundles.add(bundle);
	}

	@Override
	public Bundle createNewBundle(String name) {
		Metadata metadata = Metadata.createNew();
		return addBundle(name, metadata);
	}

	private Bundle addBundle(String name, Metadata metadata) {
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
	public void addPlaylist(Playlist playlist) throws JMOPSourceException {
		playlists.add(playlist);
	}

	@Override
	public Playlist createNewPlaylist(Bundle bundle, String name) {
		Metadata metadata = Metadata.createNew();
		return addPlaylist(bundle, name, metadata);
	}

	private Playlist addPlaylist(Bundle bundle, String name, Metadata metadata) {
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
	public void addTrack(Track track) throws JMOPSourceException {
		tracks.add(track);
	}

	@Override
	public Track createNewTrack(Bundle bundle, TrackData data) {
		Metadata metadata = Metadata.createNew();
		return addTrack(bundle, data.getIdentifier(), data.getTitle(), data.getDescription(), data.getDuration(), metadata);
	}

	private Track addTrack(Bundle bundle, String identifier, String title, String description, Duration duration,
			Metadata metadata) {
		Track track = new Track(bundle, identifier, title, description, duration, metadata);

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

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		return "DefaultInMemoryMusicbase [bundles=" + bundles + ", playlists=" + playlists + ", tracks=" + tracks + "]";
	}
}
