package cz.martlin.jmop.common.musicbase.misc;

import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class MusicbaseListingEncapsulator {
	private final BaseMusicbase musicbase;

	public MusicbaseListingEncapsulator(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}

/////////////////////////////////////////////////////////////////////////////////////////

	public Set<Bundle> bundles() throws JMOPSourceException {
		return musicbase.bundles();
	}

	public Set<Playlist> playlists(Bundle bundle) throws JMOPSourceException {
		return musicbase.playlists(bundle);
	}

	public Set<Track> tracks(Bundle bundle) throws JMOPSourceException {
		return musicbase.tracks(bundle);
	}
	
/////////////////////////////////////////////////////////////////////////////////////////

	public Set<Bundle> findBundles(String infix) throws JMOPSourceException {
		return musicbase.bundles().stream() //
				.filter(b -> matches(b, infix)) //
				.collect(Collectors.toSet()); //
	}
	
	public Set<Track> findTracks(String infix) throws JMOPSourceException {
		try {
		return musicbase.bundles().stream() //
				.map(b -> tracksOfBundle(b)) //
				.flatMap(ts -> ts.stream()) //
				.filter(t -> matches(t, infix)) //
				.collect(Collectors.toSet()); //
		} catch (RuntimeException e) {
			throw new JMOPSourceException(e); //TODO FIXME catch all th exceptions at all?
		}
	}

	private Set<Track> tracksOfBundle(Bundle bundle) {
		try {
			return musicbase.tracks(bundle);
		} catch (JMOPSourceException e) {
			throw new RuntimeException(e);
		}
	}

	//TODO all the rest
	
	/////////////////////////////////////////////////////////////////////////////////////////

	private boolean matches(Bundle bundle, String infix) {
		return bundle.getName().contains(infix);
	}
	
	private boolean matches(Track track, String infix) {
		return track.getTitle().contains(infix);
	}

}
