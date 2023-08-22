package cz.martlin.jmop.common.musicbase.misc;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseLoading;

/**
 * The main entry point for the musicbase. This class encapsulates the
 * listing/loading/queriing (i.e. read-only) operations.
 * 
 * @author martin
 *
 */
public class MusicbaseListingEncapsulator {
	private final BaseMusicbaseLoading musicbase;

	public MusicbaseListingEncapsulator(BaseMusicbaseLoading musicbase) {
		super();
		this.musicbase = musicbase;
	}
	

	public MusicbaseListingEncapsulator(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}

/////////////////////////////////////////////////////////////////////////////////////////


	public Set<Bundle> bundles()  {
		return new TreeSet<>(musicbase.bundles());
	}

	public Set<Playlist> playlists() {
		return musicbase.bundles().stream() //
				.flatMap(b -> musicbase.playlists(b).stream()) //
				.collect(Collectors.toCollection(() -> new TreeSet<>()));
	}
	
	public Set<Playlist> playlists(Bundle bundleOrAll)  {
		if (bundleOrAll != null) {
			return new TreeSet<>(musicbase.playlists(bundleOrAll));
		} else {
			return playlists();
		}
		
	}

	
	public Set<Track> tracks() {
		return musicbase.bundles().stream() //
				.flatMap(b -> musicbase.tracks(b).stream()) //
				.collect(Collectors.toCollection(() -> new TreeSet<>()));
	}

	public Set<Track> tracks(Bundle bundleOrAll)  {
		if (bundleOrAll != null) {
			return new TreeSet<>(musicbase.tracks(bundleOrAll));	
		} else {
			return tracks();
		}
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public Set<Track> tracks(Playlist playlist) {
		return new TreeSet<>(playlist.getTracks().getTracks());
	}
	
	public int indexOf(Playlist playlist, Track track) {
		return playlist.getTracks().getTracks().indexOf(track);
	}

	public Set<Playlist> playlistsContaining(Track track)  {
		Bundle bundle = track.getBundle();
		return playlists(bundle).stream() //
				.filter((p) -> p.getTracks().getTracks().contains(track))
				.collect(Collectors.toSet());
	}

/////////////////////////////////////////////////////////////////////////////////////////

	public Bundle getBundle(String bundleName)  {
		return bundles().stream() //
				.filter(b -> b.getName().equals(bundleName)) //
				.findAny().orElseGet(() -> null); //
	}
	
	public Playlist getPlaylist(Bundle bundleOrNull, String playlistName)  {
		return playlists(bundleOrNull).stream() //
				.filter(p -> p.getName().equals(playlistName)) //
				.findAny().orElseGet(() -> null); //
	}
	
	public Track getTrack(Bundle bundleOrNull, String trackTitle)  {
		return tracks(bundleOrNull).stream() //
				.filter(t -> t.getTitle().equals(trackTitle)) //
				.findAny().orElseGet(() -> null); //
	}

/////////////////////////////////////////////////////////////////////////////////////////

	public Set<Bundle> findBundles(String infix)  {
		return musicbase.bundles().stream() //
				.filter(b -> matches(b, infix)) //
				.collect(Collectors.toCollection(() -> new TreeSet<>()));
	}

	public Set<Track> findTracks(String infix)  {
		return musicbase.bundles().stream() //
				.map(b -> tracksOfBundle(b)) //
				.flatMap(ts -> ts.stream()) //
				.filter(t -> matches(t, infix)) //
				.collect(Collectors.toCollection(() -> new TreeSet<>()));
	}

	private Set<Track> tracksOfBundle(Bundle bundle) {
		return musicbase.tracks(bundle);
	}

	// TODO all the rest

	/////////////////////////////////////////////////////////////////////////////////////////

	private boolean matches(Bundle bundle, String infix) {
		return bundle.getName().contains(infix);
	}

	private boolean matches(Track track, String infix) {
		return track.getTitle().contains(infix);
	}


}
