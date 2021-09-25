package cz.martlin.jmop.common.storages.xspf;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager.MetaKind;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
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
			// the track index is crucial for us to work,
			// we cannot simply recover from that

			// reporter.report("Cannot get track index", e);
			throw new JMOPPersistenceException("Cannot get track index", e);
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
			reporter.report("Cannot get track annotation", e);
			return "";
		}
	}

	@Override
	public Duration getTrackDuration(XSPFTrack xtrack) {
		try {
			return super.getTrackDuration(xtrack);
		} catch (Exception e) {
			reporter.report("Cannot get track duration", e);
			return DurationUtilities.createDuration(0, 0, 0);
		}
	}

	@Override
	public void setTrackIndex(TrackIndex index, XSPFTrack xtrack) {
		try {
			super.setTrackIndex(index, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set track index", e);
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
			reporter.report("Cannot set annotation", e);
		}
	}

	@Override
	public void setTrackDuration(Track track, XSPFTrack xtrack) {
		try {
			super.setTrackDuration(track, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set duration", e);
		}
	}

	@Override
	public void setTrackLocation(Track track, TracksLocator tracks, XSPFTrack xtrack) {
		try {
			super.setTrackLocation(track, tracks, xtrack);
		} catch (Exception e) {
			reporter.report("Cannot set track location", e);
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

}
