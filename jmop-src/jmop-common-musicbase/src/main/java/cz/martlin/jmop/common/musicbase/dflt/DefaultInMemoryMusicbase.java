package cz.martlin.jmop.common.musicbase.dflt;

import java.io.File;
import java.io.InputStream;
import java.util.TreeSet;
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
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
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
	public void addBundle(Bundle bundle)  {
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
	public void addPlaylist(Playlist playlist)  {
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
	public void playlistUpdated(Playlist playlist)  {
		// nothing to do here
	}

	@Override
	public void addTrack(Track track)  {
		tracks.add(track);
	}

	@Override
	public Track createNewTrack(Bundle bundle, TrackData data, TrackFileCreationWay trackFileCreationSpecifier, File trackFileSource) {
		Metadata metadata = Metadata.createNew();
		return addTrack(bundle, data.getIdentifier(), data.getTitle(), data.getDescription(), data.getDuration(), metadata, trackFileSource);
	}

	private Track addTrack(Bundle bundle, String identifier, String title, String description, Duration duration,
			Metadata metadata, File file) {
		Track track = new Track(bundle, identifier, title, description, duration, file, metadata);

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
	public void trackUpdated(Track track)  {
		// nothing to do here
	}

	/////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundles == null) ? 0 : bundles.hashCode());
		result = prime * result + ((playlists == null) ? 0 : playlists.hashCode());
		result = prime * result + ((tracks == null) ? 0 : tracks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultInMemoryMusicbase other = (DefaultInMemoryMusicbase) obj;
		if (bundles == null) {
			if (other.bundles != null)
				return false;
		} else if (!new TreeSet<>(bundles).equals(new TreeSet<>(other.bundles)))
			return false;
		if (playlists == null) {
			if (other.playlists != null)
				return false;
		} else if (!new TreeSet<>(playlists).equals(new TreeSet<>(other.playlists)))
			return false;
		if (tracks == null) {
			if (other.tracks != null)
				return false;
		} else if (!new TreeSet<>(tracks).equals(new TreeSet<>(other.tracks)))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DefaultInMemoryMusicbase [bundles=" + bundles + ", playlists=" + playlists + ", tracks=" + tracks + "]";
	}
	
}
