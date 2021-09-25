package cz.martlin.jmop.common.storages.loader.tracks;

import java.util.HashSet;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An track loader, which firstly lists tracks indentifiers and just then
 * loads the particular tracks. The loads delegates to
 * {@link BaseTracksByIdentifierLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class ByIdentifierTracksLoader<IT> implements BaseTracksLoader {

	private final BaseTracksIdentifiersLister<IT> lister;
	private final BaseTracksByIdentifierLoader<IT> loader;

	public ByIdentifierTracksLoader(BaseTracksIdentifiersLister<IT> lister, BaseTracksByIdentifierLoader<IT> loader) {
		super();
		this.lister = lister;
		this.loader = loader;
	}

	@Override
	public Set<Track> loadTracks(Bundle bundle) throws JMOPPersistenceException {
		Set<IT> indentifiers = lister.loadTracksIdentifiers(bundle);

		return loadTracks(bundle, indentifiers);
	}

	private Set<Track> loadTracks(Bundle bundle, Set<IT> indentifiers) throws JMOPPersistenceException {
		Set<Track> tracks = new HashSet<>(indentifiers.size());

		for (IT indentifier : indentifiers) {
			Track track = loader.loadTrack(bundle, indentifier);

			if (track != null) {
				tracks.add(track);
			}
		}

		return tracks;
	}

}
