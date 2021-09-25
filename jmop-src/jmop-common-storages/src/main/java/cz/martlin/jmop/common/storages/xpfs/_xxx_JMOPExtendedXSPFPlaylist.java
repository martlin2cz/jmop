package cz.martlin.jmop.common.storages.xpfs;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.xspf.playlist.BaseXSPFElement;
import cz.martlin.xspf.playlist.XSPFPlaylist;
import cz.martlin.xspf.playlist.XSPFTrack;

@Deprecated
public class _xxx_JMOPExtendedXSPFPlaylist {

	private final Playlist playlist;

	private final TracksSource tracks;
	private final BaseErrorReporter reporter;
	private final JMOPXSPFPlaylistExtender extender;

	public _xxx_JMOPExtendedXSPFPlaylist(Playlist playlist, TracksSource tracks, BaseErrorReporter reporter) {
		super();
		this.playlist = playlist;
		
		this.tracks = tracks;
		this.reporter = reporter;
		this.extender = new JMOPXSPFPlaylistExtender(reporter);
	}

	public void savePlaylistWithBundle(File file) throws JMOPPersistenceException {
		XSPFPlaylist xspf = getOrCreate(file);

		setPlaylistData(playlist, xspf);

		Bundle bundle = playlist.getBundle();
		setBundleData(bundle, xspf);

		throw new UnsupportedOperationException();
	}

	public void saveOnlyPlaylist(File file) throws JMOPPersistenceException {
		XSPFPlaylist xspf = getOrCreate(file);

		setPlaylistData(playlist, xspf);

		throw new UnsupportedOperationException();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void setBundleData(Bundle bundle, BaseXSPFElement xspf) throws JMOPPersistenceException {
		Element extension = extender.getOrCreateExtension(xspf, "bundle");
		if (extension == null) {
			return;
		}

		extender.setAttr(extension, "name", bundle.getName());
		setMetadata(xspf, "bundle", bundle.getMetadata());
	}

	private void setPlaylistData(Playlist playlist, XSPFPlaylist xspf) throws JMOPPersistenceException {
		throw new UnsupportedOperationException();

//		Element extension = extender.getOrCreateExtension(xspf, "playlist");
//		if (extension == null) {
//			return;
//		}
//
//		extender.setAttr(extension, "currentTrack", playlist.getCurrentTrackIndex());
//		setMetadata(xspf, "playlist", playlist.getMetadata());
//		setTracks(xspf, playlist.getTracks());
	}

	private void setTracks(XSPFPlaylist xspf, Tracklist tracks) throws JMOPPersistenceException {
		List<XSPFTrack> xspfTracks = tracks.getTracks().stream()//
				.map((t) -> {
					try {
						XSPFTrack xspfTrack = xspf.newTrack();
						setTrack(t, xspfTrack);
						return xspfTrack;
					} catch (JMOPPersistenceException e) {
						reporter.report("Could not set track " + t.getTitle(), e);
						return null;
					}
				}) //
				.filter((x) -> x != null) //
				.collect(Collectors.toList());

		throw new UnsupportedOperationException();
	}

	private void setTrack(Track track, XSPFTrack xspf) throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		xspf.setTitle(track.getTitle());
//		// TODO identifier
//		xspf.setAnnotation(track.getDescription());
//		xspf.setDuration(track.getDuration());
//
//		File file = tracks.trackFile(track);
//		URI uri = fileToUri(file);
//		if (uri != null) {
//			xspf.setLocation(uri);
//		}

//		setMetadata(xspf, "track", track.getMetadata());
	}

	private void setMetadata(BaseXSPFElement xspf, String metaName, Metadata metadata) {
		Element extension = extender.getOrCreateExtension(xspf, metaName);

		extender.setAttr(extension, "created", metadata.getCreated());
		extender.setAttr(extension, "numberOfPlays", metadata.getNumberOfPlays());
		extender.setAttr(extension, "lastPlayed", metadata.getLastPlayed());
		extender.setAttr(extension, "totalTimePlayed", metadata.getTotalTime());
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private URI fileToUri(File file) {
		String uri = "file://" + file.getPath();
		try {
//			return URI.create(uri);
			return new URI(uri);
		} catch (Exception e) {
			reporter.report("Could not construct the track file uri", e);
			return null;
		}
	}

	private XSPFPlaylist getOrCreate(File file) throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		XSPFPlaylist playlist;
//		if (file.exists()) {
//			try {
//				playlist = XSPFPlaylist.load(file);
//			} catch (JMOPPersistenceException e) {
//				reporter.report("Could not load XSPF file " + file, e);
//				playlist = XSPFPlaylist.create();
//			}
//		} else {
//			playlist = XSPFPlaylist.create();
//		}
//
//		extender.init(playlist);
//		return playlist;
	}

}
