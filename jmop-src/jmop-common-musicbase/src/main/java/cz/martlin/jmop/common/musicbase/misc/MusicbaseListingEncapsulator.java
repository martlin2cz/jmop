package cz.martlin.jmop.common.musicbase.misc;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

/**
 * The main entry point for the musicbase. This class encapsulates the
 * listing/loading/queriing (i.e. read-only) operations.
 * 
 * @author martin
 *
 */
public class MusicbaseListingEncapsulator {
	private final BaseMusicbase musicbase;

	public MusicbaseListingEncapsulator(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}

/////////////////////////////////////////////////////////////////////////////////////////

	public Set<Bundle> bundles() throws JMOPMusicbaseException {
		return new TreeSet<>(musicbase.bundles());
	}

	public Set<Playlist> playlists(Bundle bundle) throws JMOPMusicbaseException {
		if (bundle != null) {
			return new TreeSet<>(musicbase.playlists(bundle));
		} else {
			return musicbase.bundles().stream() //
					.flatMap(b -> {
						//TODO FIXME :-(
						try {
							return musicbase.playlists(b).stream();
						} catch (JMOPMusicbaseException e) {
							throw new RuntimeException(e);
						}
					}) //
					.collect(Collectors.toCollection(() -> new TreeSet<>()));
		}
		
	}

	public Set<Track> tracks(Bundle bundle) throws JMOPMusicbaseException {
		if (bundle != null) {
			return new TreeSet<>(musicbase.tracks(bundle));	
		} else {
			return musicbase.bundles().stream() //
					.flatMap(b -> {
						//TODO FIXME :-(
						try {
							return musicbase.tracks(b).stream();
						} catch (JMOPMusicbaseException e) {
							throw new RuntimeException(e);
						}
					}) //
					.collect(Collectors.toCollection(() -> new TreeSet<>()));
		}
		
	}

	public File trackFile(Track track) throws JMOPMusicbaseException {
		return musicbase.trackFile(track);
	}

/////////////////////////////////////////////////////////////////////////////////////////

	public Bundle getBundle(String bundleName) throws JMOPMusicbaseException {
		return bundles().stream() //
				.filter(b -> b.getName().equals(bundleName)) //
				.findAny().orElseGet(() -> null); //
	}
	
	public Playlist getPlaylist(Bundle bundleOrNull, String playlistName) throws JMOPMusicbaseException {
		return playlists(bundleOrNull).stream() //
				.filter(p -> p.getName().equals(playlistName)) //
				.findAny().orElseGet(() -> null); //
	}
	
	public Track getTrack(Bundle bundleOrNull, String trackTitle) throws JMOPMusicbaseException {
		return tracks(bundleOrNull).stream() //
				.filter(t -> t.getTitle().equals(trackTitle)) //
				.findAny().orElseGet(() -> null); //
	}

/////////////////////////////////////////////////////////////////////////////////////////

	public Set<Bundle> findBundles(String infix) throws JMOPMusicbaseException {
		return musicbase.bundles().stream() //
				.filter(b -> matches(b, infix)) //
				.collect(Collectors.toCollection(() -> new TreeSet<>()));
	}

	public Set<Track> findTracks(String infix) throws JMOPMusicbaseException {
		try {
			return musicbase.bundles().stream() //
					.map(b -> tracksOfBundle(b)) //
					.flatMap(ts -> ts.stream()) //
					.filter(t -> matches(t, infix)) //
					.collect(Collectors.toCollection(() -> new TreeSet<>()));
		} catch (RuntimeException e) {
			throw new JMOPMusicbaseException(e); // TODO FIXME catch all th exceptions at all?
		}
	}

	private Set<Track> tracksOfBundle(Bundle bundle) {
		try {
			return musicbase.tracks(bundle);
		} catch (JMOPMusicbaseException e) {
			throw new RuntimeException(e);
		}
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
