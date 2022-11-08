package cz.martlin.jmop.sourcery.locals.abstracts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;

/**
 * An abstract encapsulator for any kind of any external playlist file. Declares
 * some abstract methods to be used for the importing.
 * 
 * @author martin
 *
 * @param <PT>
 * @param <TT>
 */
public abstract class BaseExternalPlaylistEncapsulator<PT, TT> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseExternalPlaylistEncapsulator.class);

	public abstract PT loadPlaylist(File file) throws IOException;

	public abstract String obtainName(PT xplaylist) throws IOException;

	public List<TrackData> obtainTracks(PT xplaylist) throws IOException {
		List<TT> xtracks = obtainRawTracks(xplaylist);
		return convertTracks(xtracks);
	}

	///////////////////////////////////////////////////////////////////////////

	protected abstract List<TT> obtainRawTracks(PT xplaylist) throws IOException;

	protected List<TrackData> convertTracks(List<TT> xtracks) {
		List<TrackData> result = new ArrayList<>(xtracks.size());

		for (TT xtrack : xtracks) {
			try {
				TrackData td = convertTrack(xtrack);
				result.add(td);
			} catch (Exception e) {
				LOGGER.warn("Track {} could not get converted", xtrack, e);
			}
		}

		return result;
	}

	protected abstract TrackData convertTrack(TT xtrack) throws IOException;

}
