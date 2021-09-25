package cz.martlin.jmop.common.storages.xpfs;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.xspf.playlist.BaseXSPFElement;
import cz.martlin.xspf.playlist.XSPFPlaylist;
import cz.martlin.xspf.playlist.XSPFTrack;
import javafx.util.Duration;

@Deprecated
public class _xxx_JMOPExtendedXSPFFile {

	private final File file;
	
	private final BaseErrorReporter reporter;
	private final JMOPXSPFPlaylistExtender extender;

	public _xxx_JMOPExtendedXSPFFile(File file, BaseErrorReporter reporter) {
		super();
		this.file = file;
		
		this.reporter = reporter;
		this.extender = new JMOPXSPFPlaylistExtender(reporter);
	}

	public Bundle loadOnlyBundle() throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		BaseXSPFElement xspf = XSPFPlaylist.load(file);
//		return getBundleData(xspf);
	}

	public Playlist loadOnlyPlaylist(Bundle bundle, Map<String, Track> tracks)
			throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		XSPFPlaylist xspf = XSPFPlaylist.load(file);
//		return getPlaylistData(xspf, bundle, tracks);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////

	private Bundle getBundleData(BaseXSPFElement xspf) {
		String name = getBundleName(xspf);
		Metadata metadata = getMetadata(xspf, "bundle");
		return new Bundle(name, metadata);
	}

	private String getBundleName(BaseXSPFElement xspf) {
		try {
			Element extension = extender.getExtension(xspf, "bundle");
			String name = extender.getStrAttr(extension, "name");
			requireNonEmpty(name);
			return name;
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("There is no bundle name", e);
		}
	}

	private Playlist getPlaylistData(XSPFPlaylist xspf, Bundle bundle, Map<String, Track> tracks)
			throws JMOPPersistenceException {

		String name = getPlaylistName(xspf);
		Tracklist tracklist = getPlaylistTracks(xspf, bundle, tracks);
		TrackIndex curentTrackIndex = getPlaylistCurrentTrackIndex(xspf, tracklist);
		Metadata metadata = getMetadata(xspf, "playlist");

		return new Playlist(bundle, name, tracklist, curentTrackIndex, metadata);
	}

	private String getPlaylistName(XSPFPlaylist xspf) throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		String name = xspf.getTitle();
//		requireNonEmpty(name);
//		return name;
	}

	private TrackIndex getPlaylistCurrentTrackIndex(BaseXSPFElement xspf, Tracklist tracklist) {
		try {
			Element extension = extender.getExtension(xspf, "playlist");
			TrackIndex index = extender.getIndexAttr(extension, "currentTrack");
			if (!index.smallerThan(tracklist.getTracks().size())) {
				throw new JMOPPersistenceException("The current track cannot be higher than track count");
			}
			return index;
		} catch (JMOPPersistenceException e) {
			reporter.report("There is no current track index or is invalid", e);
			return TrackIndex.ofIndex(0);
		}
	}

	private Tracklist getPlaylistTracks(XSPFPlaylist xspf, Bundle bundle, Map<String, Track> tracks)
			throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		return new Tracklist(xspf.getTracks().stream() //
//				.map((x) -> getTrack(x, bundle, tracks)) //
//				.filter((t) -> t != null) //
//				.collect(Collectors.toList()));
	}

	private Track getTrack(XSPFTrack xspf, Bundle bundle, Map<String, Track> tracks) {
		try {
			if (tracks == null) {
				return loadTrack(xspf, bundle);
			} else {
				return pickTrack(xspf, bundle, tracks);
			}
		} catch (JMOPPersistenceException e) {
			reporter.report("The track cannot be loaded/picked", e);
			return null;
		}
	}

	private Track loadTrack(XSPFTrack xspf, Bundle bundle) throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		String title = xspf.getTitle();
//		requireNonEmpty(title);
//
//		String identifier = "TODO"; //TODO identifier
//		String description = xspf.getAnnotation();
//		Duration duration = xspf.getDuration();
//		Metadata metadata = getMetadata(xspf, "track");

//		return new Track(bundle, identifier, title, description, duration, metadata);
	}

	private Track pickTrack(XSPFTrack xspf, Bundle bundle, Map<String, Track> tracks) throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		String title = xspf.getTitle();
//		if (!tracks.containsKey(title)) {
//			throw new JMOPPersistenceException("The track " + title + " does not exist");
//		}
//
//		return tracks.get(title);
	}

	private Metadata getMetadata(BaseXSPFElement xspf, String metaName) {
		Element extension = extender.getExtension(xspf, metaName);

		LocalDateTime created = extender.getDateAttr(extension, "created");
		int numberOfPlays = extender.getNumAttr(extension, "numberOfPlays");
		LocalDateTime lastPlayed = extender.getDateAttr(extension, "lastPlayed");
		Duration totalTime = extender.getDurationAttr(extension, "totalTimePlayed");

		return Metadata.createExisting(created, lastPlayed, numberOfPlays, totalTime);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void requireNonEmpty(String text) throws JMOPPersistenceException {
		if (text.isEmpty() || text.isBlank()) {
			throw new JMOPPersistenceException("The value is empty");
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
