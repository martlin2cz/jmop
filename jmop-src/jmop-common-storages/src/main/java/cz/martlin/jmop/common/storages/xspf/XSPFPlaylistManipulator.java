package cz.martlin.jmop.common.storages.xspf;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.storages.fileobjects.BaseFileObjectManipulator;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager.MetaKind;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XSPFRuntimeException;
import javafx.util.Duration;

/**
 * An playlist extender, which modifies the XSPFFile instance.
 * 
 * @author martin
 *
 */
public class XSPFPlaylistManipulator implements BaseFileObjectManipulator<XSPFFile> {

	private final JMOPtoXSFPAdapter adapter;
	private final XSPFPlaylistTracksManager tracker;

	public XSPFPlaylistManipulator(JMOPtoXSFPAdapter adapter, XSPFPlaylistTracksManager tracker) {
		super();
		this.adapter = adapter;
		this.tracker = tracker;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void setBundleDataAndTracks(Bundle bundle, Set<Track> tracks,XSPFFile xfile)
			throws JMOPPersistenceException {
		XSPFPlaylist xplaylist = playlist(xfile);

		adapter.setBundleName(bundle, xplaylist);

		adapter.setMetadata(bundle.getMetadata(), MetaKind.BUNDLE, xplaylist);
		
		tracker.setTracks(this, tracks, xfile);
	}

	@Override
	public void setPlaylistData(Playlist playlist, XSPFFile xfile)
			throws JMOPPersistenceException {
		XSPFPlaylist xplaylist = playlist(xfile);

		adapter.setPlaylistTitle(playlist, xplaylist);
		adapter.setPlaylistCurrentTrack(playlist, xplaylist);

		adapter.setMetadata(playlist.getMetadata(), MetaKind.PLAYLIST, xplaylist);

		tracker.setTracks(this, playlist.getTracks(), xfile);
	}
	

	protected void setTrack(TrackIndex index, Track track, XSPFTrack xtrack)
			throws JMOPPersistenceException {
		adapter.setTrackIndex(index, xtrack);
		adapter.setTrackTitle(track, xtrack);
		// TODO identifier
		adapter.setTrackAnnotation(track, xtrack);

		adapter.setTrackDuration(track, xtrack);
		adapter.setTrackSource(track, xtrack);

		adapter.setTrackFile(track, xtrack);
		adapter.setMetadata(track.getMetadata(), MetaKind.TRACK, xtrack);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Bundle getBundleData(XSPFFile xfile) throws JMOPPersistenceException {
		XSPFPlaylist xplaylist = playlist(xfile);

		String name = adapter.getBundleName(xplaylist);
		Metadata metadata = adapter.getMetadata(xplaylist, MetaKind.BUNDLE);

		return new Bundle(name, metadata);
	}
	
	@Override
	public Set<Track> getBundleTracks(XSPFFile xfile, Bundle bundle) throws JMOPPersistenceException {
//		XSPFPlaylist xplaylist = playlist(xfile);
		
		//TODO litle tricky
		Tracklist tracks = tracker.getPlaylistTracks(this, xfile, bundle, null);
		return new HashSet<>(tracks.getTracks());
	}

	@Override
	public Playlist getPlaylistData(XSPFFile xfile, Bundle bundle, Map<String, Track> tracks)
			throws JMOPPersistenceException {
		XSPFPlaylist xplaylist = playlist(xfile);

		String name = adapter.getPlaylistTitle(xplaylist);
		Tracklist tracklist = tracker.getPlaylistTracks(this, xfile, bundle, tracks);
		TrackIndex curentTrackIndex = adapter.getPlaylistCurrentTrack(xplaylist);
		// TODO verify currrentTrackIndex < tracklist.count()
		Metadata metadata = adapter.getMetadata(xplaylist, MetaKind.PLAYLIST);

		return new Playlist(bundle, name, tracklist, curentTrackIndex, metadata);
	}



	protected Track loadTrack(XSPFTrack xtrack, Bundle bundle) throws JMOPPersistenceException {
		String title = adapter.getTrackTitle(xtrack);

		String description = adapter.getTrackAnnotation(xtrack);
		Duration duration = adapter.getTrackDuration(xtrack);
		Metadata metadata = adapter.getMetadata(xtrack, MetaKind.TRACK);
		URI source = adapter.getTrackSource(xtrack);
		File file = adapter.getTrackFile(xtrack);
		
		
		return new Track(bundle, title, description, duration, source, file, metadata);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private XSPFPlaylist playlist(XSPFFile xfile) throws JMOPPersistenceException {
		try {
			return xfile.playlist();
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("The file does not contain playlist specifier", e);
		}
	}

}