package cz.martlin.jmop.common.storages.xspf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.ExceptionWrapper;
import cz.martlin.xspf.util.ExceptionWrapper.PredicateThrowing;
import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XSPFRuntimeException;

/**
 * Simple helper to the {@link XSPFPlaylistManipulator} which's responsibility is
 * to manage the tracks. I.e to populate the jmop tracks to xspf (and back),
 * with ideally no original xspf data loss (to reuse existing xspf tracks as
 * much as possible, rather then just throw away the whole tracks() and
 * re-create it from scratch). It may also try to keep the trace of the moved
 * (different track num), renamed (different title) or any other way altered
 * tracks, and keep the xspf file as less changed as possible.
 * 
 * The manupulation with the particular one track (set/load) gets delegated back
 * to the extender.
 * 
 * @author martin
 *
 */
public class XSPFPlaylistTracksManager {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final JMOPtoXSFPAdapter adapter;

	public XSPFPlaylistTracksManager(JMOPtoXSFPAdapter adapter) {
		super();
		this.adapter = adapter;
	}

/////////////////////////////////////////////////////////////////////////////////////


	public void setTracks(XSPFPlaylistManipulator extender, Set<Track> tracksSet, TracksLocator tracks, XSPFFile xfile)
			throws JMOPPersistenceException {
		
		Tracklist trackslist = new Tracklist(new ArrayList<>(tracksSet));
		//FIXME this will attach (quite random) trackNums
		// make them to not to be set instead
		setTracks(extender, trackslist, tracks, xfile);
	}

	
	public void setTracks(XSPFPlaylistManipulator extender, Tracklist tracklist, TracksLocator tracks, XSPFFile xfile)
			throws JMOPPersistenceException {
		try {
			XSPFTracks currentXtracks = //
					xfile.playlist().getTracks() != null //
							? xfile.playlist().getTracks() //
							: xfile.newTracks(); //

			XSPFTracks newXtracks = xfile.newTracks();

			setTracks(extender, tracklist, tracks, currentXtracks, newXtracks);

			xfile.playlist().setTracks(newXtracks);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Could not update the tracks", e);
		}
	}

	private void setTracks(XSPFPlaylistManipulator extender, Tracklist tracklist, TracksLocator tracks,
			XSPFTracks currentXtracks, XSPFTracks newXtracks) throws XSPFException, JMOPPersistenceException {

		Map<TrackIndex, Track> indexToTrackMap = tracklist.asIndexedMap();
		for (TrackIndex index : indexToTrackMap.keySet()) {
			Track track = indexToTrackMap.get(index);

			XSPFTrack xtrack = getXTrackOfTrack(currentXtracks, track, index);

			currentXtracks.remove(xtrack);
			newXtracks.add(xtrack);

			extender.setTrack(index, track, xtrack, tracks);
		}
	}

	private XSPFTrack getXTrackOfTrack(XSPFTracks xtracks, Track track, TrackIndex index)
			throws JMOPPersistenceException {

		LOG.info("Looking for the " + track + " with " + index + " in " + xtracks);
		XSPFTrack xtrack = null;

		xtrack = tryFind(xtrack, xtracks, "title, duration and index", //
				(xt) -> Objects.equals(adapter.getTrackTitle(xt), track.getTitle()) //
						&& Objects.equals(adapter.getTrackDuration(xt), track.getDuration()) //
						&& Objects.equals(adapter.getTrackIndex(xt), index));

		xtrack = tryFind(xtrack, xtracks, "title and index", //
				(xt) -> Objects.equals(adapter.getTrackTitle(xt), track.getTitle()) //
						&& Objects.equals(adapter.getTrackIndex(xt), index));

		xtrack = tryFind(xtrack, xtracks, "title", //
				(xt) -> Objects.equals(adapter.getTrackTitle(xt), track.getTitle()));

//		xtrack = tryFind(xtrack, xtracks, "duration and index", //
//				(xt) -> Objects.equals(adapter.getTrackDuration(xt), track.getDuration())) //
//						&& Objects.equals(adapter.getTrackIndex(xt), index)));

		xtrack = tryFind(xtrack, xtracks, "index", //
				(xt) -> Objects.equals(adapter.getTrackIndex(xt), index));

		if (xtrack != null) {
			LOG.info("Found " + xtrack + " for the " + track + " with " + index);
			return xtrack;
		} else {
			LOG.info("No xtrack found for " + track + " with " + index + ", creating new");
			return createNewTrack(xtracks);
		}
	}

	private XSPFTrack tryFind(XSPFTrack alreadyFoundXtrack, XSPFTracks xtracks, String description,
			PredicateThrowing<XSPFTrack> matching) throws JMOPPersistenceException {

		if (alreadyFoundXtrack != null) {
			return alreadyFoundXtrack;
		}

		XSPFTrack xtrack = find(xtracks, matching);
		if (xtrack != null) {
			LOG.debug("Found matching xtrack by " + description + ": " + xtrack);
			return xtrack;
		} else {
			LOG.debug("Did not find matching xtrack by " + description);
			return null;
		}
	}

	private XSPFTrack find(XSPFTracks xtracks, PredicateThrowing<XSPFTrack> matching) throws JMOPPersistenceException {

		try {
			return xtracks.list() //
					.filter(ExceptionWrapper.wrapPredicate(matching)) //
					.findAny().orElse(null);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Could not find a track", e);
		}
	}

	private XSPFTrack createNewTrack(XSPFTracks xtracks) throws JMOPPersistenceException {
		try {
			XSPFTrack newTrack = xtracks.createNew();
			xtracks.add(newTrack);
			return newTrack;
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Could not create a track", e);
		}
	}

/////////////////////////////////////////////////////////////////////////////////////

	public Tracklist getPlaylistTracks(XSPFPlaylistManipulator extender, XSPFFile xfile, Bundle bundle,
			Map<String, Track> tracks) throws JMOPPersistenceException {
		try {

			XSPFTracks xtracks = xfile.playlist().tracks(); // TODO verify not null
			Map<TrackIndex, Track> map = new HashMap<>(xtracks.size());
			
			for (XSPFTrack xtrack : xtracks.iterate()) {
				Track track = getTrack(extender, xtrack, bundle, tracks);
				if (track == null) {
					continue;
				}
				TrackIndex index = adapter.getTrackIndex(xtrack);
				if (index == null) {
					continue;
				}
				
				map.put(index, track);
			}

			List<Track> tracklist = TrackIndex.list(map);
			return new Tracklist(tracklist);
		} catch (XSPFException | RuntimeException e) {
			throw new JMOPPersistenceException("Could not get tracks", e);
		}
	}

	private Track getTrack(XSPFPlaylistManipulator extender, XSPFTrack xspf, Bundle bundle, Map<String, Track> tracks)
			throws JMOPPersistenceException {
		try {
			if (tracks == null) {
				LOG.info("Loading track from " + xspf);
				return extender.loadTrack(xspf, bundle);
			} else {
				LOG.info("Picking track from " + xspf);
				return pickTrack(xspf, bundle, tracks);
			}
		} catch (JMOPPersistenceException e) {
			throw new JMOPPersistenceException("The track cannot be loaded/picked", e);
		}
	}

	private Track pickTrack(XSPFTrack xspf, Bundle bundle, Map<String, Track> tracks) throws JMOPPersistenceException {

		String title = adapter.getTrackTitle(xspf);

		if (!tracks.containsKey(title)) {
			throw new JMOPPersistenceException("The track " + title + " does not exist");
		}

		return tracks.get(title);
	}

/////////////////////////////////////////////////////////////////////////////////////

	@FunctionalInterface
	public interface XSPFPredicate {

		public boolean apply(XSPFTrack xtrack) throws XSPFException;

	}

}
