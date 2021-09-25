package cz.martlin.jmop.common.storages.xpfs;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.xspf.playlist.BaseXSPFElement;
import cz.martlin.xspf.playlist.XSPFPlaylist;
import cz.martlin.xspf.playlist.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;
import javafx.util.Duration;

public class JMOPExtendedXSPFPlaylist {

	private final BaseErrorReporter reporter;
	private final XSPFPlaylist xspf;
	private final JMOPXSPFPlaylistExtender extender;

	private JMOPExtendedXSPFPlaylist(XSPFPlaylist xspf, BaseErrorReporter reporter) throws JMOPPersistenceException {
		super();
		this.reporter = reporter;

		this.xspf = xspf;
		this.extender = new JMOPXSPFPlaylistExtender(reporter);

		extender.init(xspf);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public static JMOPExtendedXSPFPlaylist load(File file, BaseErrorReporter reporter) throws JMOPPersistenceException {
		try {
			XSPFPlaylist xspf = XSPFPlaylist.load(file);
			return new JMOPExtendedXSPFPlaylist(xspf, reporter);
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Cannot load XSPF file" + file, e);
		}
	}

	public static JMOPExtendedXSPFPlaylist createNew(BaseErrorReporter reporter) throws JMOPPersistenceException {
		try {
			XSPFPlaylist xspf = XSPFPlaylist.create();
			return new JMOPExtendedXSPFPlaylist(xspf, reporter);
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Cannot create XSPF document", e);
		}
	}

	public static JMOPExtendedXSPFPlaylist tryLoadOrCreate(File file, BaseErrorReporter reporter)
			throws JMOPPersistenceException {
		try {
			XSPFPlaylist xspf = getOrCreate(file, reporter);
			return new JMOPExtendedXSPFPlaylist(xspf, reporter);
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Could not create/load XSPF document/file " + file, e);
		}
	}

	private static XSPFPlaylist getOrCreate(File file, BaseErrorReporter reporter) throws XSPFException {
		if (file != null && file.exists()) {
			try {
				return XSPFPlaylist.load(file);
			} catch (XSPFException e) {
				reporter.report("Could not load XSPF file " + file, e);
			}
		}

		return XSPFPlaylist.create();

	}

	public void save(File file) throws JMOPPersistenceException {
		try {
			xspf.save(file);
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("The save failed", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void setBundleData(Bundle bundle) {
		Element extension = extender.getOrCreateExtension(xspf, "bundle");
		if (extension == null) {
			return;
		}

		extender.setAttr(extension, "name", bundle.getName());
		setMetadata("bundle", bundle.getMetadata());
	}

	public void setPlaylistData(Playlist playlist, TracksSource tracks) throws JMOPPersistenceException {
		setPlaylistName(playlist);

		Element extension = extender.getOrCreateExtension(xspf, "playlist");
		if (extension == null) {
			return;
		}

		extender.setAttr(extension, "currentTrack", playlist.getCurrentTrackIndex());
		setMetadata("playlist", playlist.getMetadata());
		setTracks(playlist.getTracks(), tracks);
	}

	private void setPlaylistName(Playlist playlist) throws JMOPPersistenceException {
		try {
			xspf.setTitle(playlist.getName());
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Could not set playlist name", e);
		}
	}

	private void setTracks(Tracklist tracklist, TracksSource tracks) throws JMOPPersistenceException {
		List<XSPFTrack> xspfTracks = tracklist.getTracks().stream()//
				.map((t) -> {
					try {
						XSPFTrack xspfTrack = xspf.newTrack();
						setTrack(t, xspfTrack, tracks);
						return xspfTrack;
					} catch (XSPFException e) {
						reporter.report("Could not set track " + t.getTitle(), e);
						return null;
					}
				}) //
				.filter((x) -> x != null) //
				.collect(Collectors.toList());

		try {
			xspf.setTracks(xspfTracks);
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Could not set tracks", e);
		}
	}

	private void setTrack(Track track, XSPFTrack xspf, TracksSource tracks) throws XSPFException {
		xspf.setTitle(track.getTitle());
		// TODO identifier
		xspf.setAnnotation(track.getDescription());

		Duration duration = track.getDuration();
		xspf.setDuration(java.time.Duration.ofMillis((long) duration.toMillis()));

		File file = tracks.trackFile(track);
		URI uri = fileToUri(file);
		if (uri != null) {
			xspf.setLocation(uri);
		}

		setMetadata("track", track.getMetadata());
	}

	private void setMetadata(String metaName, Metadata metadata) {
		Element extension = extender.getOrCreateExtension(xspf, metaName);

		extender.setAttr(extension, "created", metadata.getCreated());
		extender.setAttr(extension, "numberOfPlays", metadata.getNumberOfPlays());
		extender.setAttr(extension, "lastPlayed", metadata.getLastPlayed());
		extender.setAttr(extension, "totalTimePlayed", metadata.getTotalTime());
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Bundle getBundleData() {
		String name = getBundleName();
		Metadata metadata = getMetadata(xspf, "bundle");
		return new Bundle(name, metadata);
	}

	private String getBundleName() {
		try {
			Element extension = extender.getExtension(xspf, "bundle");
			String name = extender.getStrAttr(extension, "name");
			requireNonEmpty(name);
			return name;
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("There is no bundle name", e);
		}
	}

	public Playlist getPlaylistData(Bundle bundle, Map<String, Track> tracks) throws JMOPPersistenceException {

		String name = getPlaylistName();
		Tracklist tracklist = getPlaylistTracks(bundle, tracks);
		TrackIndex curentTrackIndex = getPlaylistCurrentTrackIndex(tracklist);
		Metadata metadata = getMetadata(xspf, "playlist");

		return new Playlist(bundle, name, tracklist, curentTrackIndex, metadata);
	}

	private String getPlaylistName() throws JMOPPersistenceException {
		try {
			String name = xspf.getTitle();
			requireNonEmpty(name);
			return name;
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Could not obtain the playlist name", e);
		}

	}

	private TrackIndex getPlaylistCurrentTrackIndex(Tracklist tracklist) {
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

	private Tracklist getPlaylistTracks(Bundle bundle, Map<String, Track> tracks) throws JMOPPersistenceException {
		try {
			return new Tracklist(xspf.getTracks().stream() //
					.map((x) -> getTrack(x, bundle, tracks)) //
					.filter((t) -> t != null) //
					.collect(Collectors.toList()));
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Could not get tracks", e);
		}
	}

	private Track getTrack(XSPFTrack xspf, Bundle bundle, Map<String, Track> tracks) {
		try {
			if (tracks == null) {
				return loadTrack(xspf, bundle);
			} else {
				return pickTrack(xspf, bundle, tracks);
			}
		} catch (JMOPPersistenceException | XSPFException e) {
			reporter.report("The track cannot be loaded/picked", e);
			return null;
		}
	}

	private Track loadTrack(XSPFTrack xspf, Bundle bundle) throws XSPFException, JMOPPersistenceException {
		String title = xspf.getTitle();
		requireNonEmpty(title);

		String identifier = "TODO"; // TODO identifier
		String description = xspf.getAnnotation();
		Duration duration = Duration.millis(xspf.getDuration().toMillis());
		Metadata metadata = getMetadata(xspf, "track");

		return new Track(bundle, identifier, title, description, duration, metadata);
	}

	private Track pickTrack(XSPFTrack xspf, Bundle bundle, Map<String, Track> tracks)
			throws JMOPPersistenceException, XSPFException {
		String title = xspf.getTitle();
		if (!tracks.containsKey(title)) {
			throw new JMOPPersistenceException("The track " + title + " does not exist");
		}

		return tracks.get(title);
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

	private URI fileToUri(File file) {
		try {
			return file.toURI();
		} catch (Exception e) {
			reporter.report("Could not construct the track file uri", e);
			return null;
		}
	}

}
