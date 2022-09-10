package cz.martlin.jmop.common.storages.xspf;

import java.io.File;
import java.net.URI;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager.MetaKind;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;
import javafx.util.Duration;

public class FailsaveJMOPToXSPFAdapter extends JMOPtoXSFPAdapter {

	private BaseErrorReporter reporter;

	public FailsaveJMOPToXSPFAdapter(BasePlaylistMetaInfoManager<XSPFCommon> mim, BaseErrorReporter reporter) {
		super(mim);

		this.reporter = reporter;
	}

	@Override
	public void setBundleName(Bundle bundle, XSPFPlaylist xplaylist) {
		try {
			super.setBundleName(bundle, xplaylist);
		} catch (Exception e) {
			reporter.report("Cannot set bundle name", e);
		}
	}

	@Override
	public String getBundleName(XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		try {
			return super.getBundleName(xplaylist);
		} catch (Exception e) {
			// the bundle name is crucial for us to work,
			// we cannot simply recover from that

			// reporter.report("Cannot get bundle name", e);
			throw new JMOPPersistenceException("Cannot get bundle name", e);
		}
	}

	@Override
	public void setPlaylistTitle(Playlist playlist, XSPFPlaylist xplaylist) {
		try {
			super.setPlaylistTitle(playlist, xplaylist);
		} catch (Exception e) {
			reporter.report("Cannot set playlist title", e);
		}
	}

	@Override
	public void setPlaylistCurrentTrack(Playlist playlist, XSPFPlaylist xplaylist) {
		try {
			super.setPlaylistCurrentTrack(playlist, xplaylist);
		} catch (Exception e) {
			reporter.report("Cannot set playlist current track", e);
		}
	}

	@Override
	public String getPlaylistTitle(XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		try {
			return super.getPlaylistTitle(xplaylist);
		} catch (Exception e) {
			// the playlist title is crucial for us to work,
			// we cannot simply recover from that

			// reporter.report("Cannot get playlist title", e);
			throw new JMOPPersistenceException("Cannot get playlist title", e);
		}
	}

	@Override
	public TrackIndex getPlaylistCurrentTrack(XSPFPlaylist xplaylist) {
		try {
			return super.getPlaylistCurrentTrack(xplaylist);
		} catch (Exception e) {
			reporter.report("Cannot get playlist current track", e);
			return TrackIndex.ofIndex(0);
		}
	}

	@Override
	public TrackIndex getTrackIndex(XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			return super.getTrackIndex(xtrack);
		} catch (Exception e) {
			reporter.report("Cannot get trackIndex of the track " + xtrackTitleToReport(xtrack), e);
			return computeAlternativeTrackIndex(xtrack);
		}
	}

	@Override
	public String getTrackTitle(XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			return super.getTrackTitle(xtrack);
		} catch (Exception e) {
			// the track title is crucial for us to work,
			// we cannot simply recover from that

			// reporter.report("Cannot get track title", e);
			throw new JMOPPersistenceException("Cannot get track title", e);
		}
	}

	@Override
	public String getTrackAnnotation(XSPFTrack xtrack) {
		try {
			return super.getTrackAnnotation(xtrack);
		} catch (Exception e) {
			reporter.report("Cannot get track annotation of the track " + xtrackTitleToReport(xtrack), e);
			return "";
		}
	}

	@Override
	public Duration getTrackDuration(XSPFTrack xtrack) {
		try {
			return super.getTrackDuration(xtrack);
		} catch (Exception e) {
			reporter.report("Cannot get track duration of the track " + xtrackTitleToReport(xtrack), e);
			return DurationUtilities.createDuration(0, 0, 0);
		}
	}
	
	@Override
	public URI getTrackSource(XSPFTrack xtrack) {
		
		try {
			return super.getTrackSource(xtrack);
		} catch (JMOPPersistenceException e) {
			reporter.report("Cannot get track source of the track " + xtrackTitleToReport(xtrack), e);
			return null;
		}
	}
	
	@Override
	public File getTrackFile(XSPFTrack xtrack) {
		try {
			return super.getTrackFile(xtrack);
		} catch (Exception e) {
			reporter.report("Cannot get track file of the track " + xtrackTitleToReport(xtrack), e);
			return null;
		}
	}

	@Override
	public void setTrackIndex(TrackIndex index, XSPFTrack xtrack) {
		try {
			super.setTrackIndex(index, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set track index of the track " + xtrackTitleToReport(xtrack), e);
		}
	}

	@Override
	public void setTrackTitle(Track track, XSPFTrack xtrack) {
		try {
			super.setTrackTitle(track, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set track title", e);
		}
	}

	@Override
	public void setTrackAnnotation(Track track, XSPFTrack xtrack) {
		try {
			super.setTrackAnnotation(track, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set annotation of the track " + xtrackTitleToReport(xtrack), e);
		}
	}

	@Override
	public void setTrackDuration(Track track, XSPFTrack xtrack) {
		try {
			super.setTrackDuration(track, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set duration of the track " + xtrackTitleToReport(xtrack), e);
		}
	}
	
	@Override
	public void setTrackSource(Track track, XSPFTrack xtrack) {
		try {
			super.setTrackSource(track, xtrack);
		} catch (JMOPPersistenceException e) {
			reporter.report("Cannot set track source of the track " + xtrackTitleToReport(xtrack), e);
		}
	}

	@Override
	public void setTrackFile(Track track, XSPFTrack xtrack) {
		try {
			super.setTrackFile(track, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set track file of the track " + xtrackTitleToReport(xtrack), e);
		}
	}

	@Override
	public void setMetadata(Metadata metadata, MetaKind kind, XSPFCommon xelem) {
		try {
			super.setMetadata(metadata, kind, xelem);
		} catch (Exception e) {
			reporter.report("Cannot set metadata", e);
		}
	}

	@Override
	public Metadata getMetadata(XSPFCommon xelem, MetaKind kind) {
		try {
			return super.getMetadata(xelem, kind);
		} catch (Exception e) {
			reporter.report("Cannot get metadata", e);
			return Metadata.createNew();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns title of the given xtrack, or "???" if cannot be obtained.
	 * Use in error reporting only.
	 *  
	 * @param xtrack
	 * @return
	 */
	private String xtrackTitleToReport(XSPFTrack xtrack) {
		try {
			return xtrack.getTitle();
		} catch (XSPFException e) {
			reporter.report("Cannot get track title", e);
			return "???";
		}
	}

	/**
	 * Tries to compute the track index of the given xtrack based
	 * on the order number (index) of the provided track node in the
	 * owning trackList element.
	 * 
	 * @param xtrack
	 * @return
	 */
	private TrackIndex computeAlternativeTrackIndex(XSPFTrack xtrack) {
		Node trackNode = xtrack.getNode();
		Node tracklistNode = trackNode.getParentNode();
		NodeList tracklistChildren = tracklistNode.getChildNodes();
		
		int elemIndex = 0;
		for (int i = 0; i < tracklistChildren.getLength(); i++) {
			Node ithTrackNode = tracklistChildren.item(i);
			if (trackNode.equals(ithTrackNode)) {
				return TrackIndex.ofIndex(elemIndex);
			}
			if (ithTrackNode.getNodeType() == Node.ELEMENT_NODE) {
				elemIndex++;
			}
		}
		
		throw new IllegalArgumentException("The track node index (in the child nodes list) cannot be obtained");
	}


}
