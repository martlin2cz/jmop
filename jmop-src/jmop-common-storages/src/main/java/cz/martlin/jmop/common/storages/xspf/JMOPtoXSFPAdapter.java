package cz.martlin.jmop.common.storages.xspf;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager.MetaKind;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XSPFRuntimeException;
import javafx.util.Duration;

/**
 * An adapter between the JMOP and XSPF. The main responsibility is to handle
 * the all the possible (XSPF) exceptions (and rethrow as JMOP exceptions).
 * 
 * @author martin
 *
 */
public class JMOPtoXSFPAdapter {

	private final BasePlaylistMetaInfoManager<XSPFCommon> mim;

	public JMOPtoXSFPAdapter(BasePlaylistMetaInfoManager<XSPFCommon> mim) {
		super();
		this.mim = mim;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// set/get ... bundle

	public void setBundleName(Bundle bundle, XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		mim.setStrMetaInfo(xplaylist, MetaKind.BUNDLE, "name", bundle.getName());
	}

	public String getBundleName(XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		try {
			String bundleName = mim.getStrMetaValue(xplaylist, MetaKind.BUNDLE, "name");
			if (bundleName == null || bundleName.isEmpty()) {
				throw new IllegalArgumentException("The bundle name is not specified or is empty");
			}
			return bundleName;
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new JMOPPersistenceException("Could not get bundle name", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// set/get ... playlist

	public void setPlaylistTitle(Playlist playlist, XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		try {
			xplaylist.setTitle(playlist.getName());
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Could not set playlist title", e);
		}
	}

	public void setPlaylistCurrentTrack(Playlist playlist, XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		mim.setIndexMetaInfo(xplaylist, MetaKind.PLAYLIST, "currentTrack", playlist.getCurrentTrackIndex());
	}

	public String getPlaylistTitle(XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		try {
			String title = xplaylist.getTitle();
			if (title == null) {
				throw new JMOPPersistenceException("The xplaylist has no title: " + xplaylist);
			}
			return title;
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot obtain the title", e);
		}
	}

	public TrackIndex getPlaylistCurrentTrack(XSPFPlaylist xplaylist) throws JMOPPersistenceException {
		return mim.getIndexMeta(xplaylist, MetaKind.PLAYLIST, "currentTrack");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// set ... track


	public TrackIndex getTrackIndex(XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			Integer num = xtrack.getTrackNum();
			Objects.requireNonNull(num, "The track num is not specified");
			return TrackIndex.ofHuman(num);
		} catch (XSPFException | XSPFRuntimeException | NullPointerException e) {
			throw new JMOPPersistenceException("Cannot obtain track number", e);
		}
	}

	
	public String getTrackTitle(XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			String title = xtrack.getTitle();
			requireNonEmpty(title);
			return title;
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot obtain track title", e);
		}

	}

	public String getTrackAnnotation(XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			return xtrack.getAnnotation();
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot obtain track annotation", e);
		}
	}

	public Duration getTrackDuration(XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			java.time.Duration duration = xtrack.getDuration();
			return durationToDuration(duration);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot obtain track duration", e);
		}
	}

	public File getTrackFile(XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			URI location = xtrack.getLocation();
			return new File(location);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot obtain track file", e);
		}
	}


	public void setTrackIndex(TrackIndex index, XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			int num = index.getHuman();
			xtrack.setTrackNum(num);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot set track index", e);
		}
	}
	
	public void setTrackTitle(Track track, XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			String title = track.getTitle();
			xtrack.setTitle(title);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot set track title", e);
		}
	}

	public void setTrackAnnotation(Track track, XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			String description = track.getDescription();
			xtrack.setAnnotation(description);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot set track annotation", e);
		}
	}

	public void setTrackDuration(Track track, XSPFTrack xtrack) throws JMOPPersistenceException {
		try {
			Duration duration = track.getDuration();
			java.time.Duration reduration = durationToDuration(duration);
			xtrack.setDuration(reduration);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot set track duration", e);
		}
	}

	public void setTrackFile(Track track, XSPFTrack xtrack) throws JMOPPersistenceException {

		File file = track.getFile();
		URI uri = JMOPtoXSFPAdapter.fileToUri(file);
		try {
			xtrack.setLocation(uri);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("The track file cannot be set", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// set ... metadata

	public void setMetadata(Metadata metadata, MetaKind kind, XSPFCommon xelem) throws JMOPPersistenceException {

		mim.setDateMetaInfo(xelem, kind, "created", metadata.getCreated());
		mim.setCountMetaInfo(xelem, kind, "numberOfPlays", metadata.getNumberOfPlays());
		mim.setDateMetaInfo(xelem, kind, "lastPlayed", metadata.getLastPlayed());
		mim.setDurationMetaInfo(xelem, kind, "totalTimePlayed", metadata.getTotalTime());

	}

	public Metadata getMetadata(XSPFCommon xelem, MetaKind kind) throws JMOPPersistenceException {

		LocalDateTime created = mim.getDateMetaValue(xelem, kind, "created");
		int numberOfPlays = mim.getCountMetaValue(xelem, kind, "numberOfPlays");
		LocalDateTime lastPlayed = mim.getDateMetaValue(xelem, kind, "lastPlayed");
		Duration totalTime = mim.getDurationMetaValue(xelem, kind, "totalTimePlayed");

		return Metadata.createExisting(created, lastPlayed, numberOfPlays, totalTime);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// the rest

	public static URI fileToUri(File file) throws JMOPPersistenceException {
		try {
			if (file == null) {
				return null;
			}
			
			return file.toURI();
		} catch (Exception e) {
			throw new JMOPPersistenceException("Could not construct the track file uri", e);
		}
	}
	
	public static File uriToFile(URI uri) throws JMOPPersistenceException {
		try {
			if (uri == null) {
				return null;
			}
			
			return new File(uri);
		} catch (Exception e) {
			throw new JMOPPersistenceException("Could not construct the track file", e);
		}
	}

	public static javafx.util.Duration durationToDuration(java.time.Duration duration) {
		if (duration == null) {
			return null;
		}
		long millis = duration.toMillis();
		return javafx.util.Duration.millis(millis);
	}

	public static java.time.Duration durationToDuration(javafx.util.Duration duration) {
		if (duration == null) {
			return null;
		}
		long millis = (long) duration.toMillis();
		return java.time.Duration.ofMillis(millis);
	}

	private void requireNonEmpty(String text) throws JMOPPersistenceException {
		if (text == null || text.isEmpty() || text.isBlank()) {
			throw new JMOPPersistenceException("The value is empty");
		}
	}

}
