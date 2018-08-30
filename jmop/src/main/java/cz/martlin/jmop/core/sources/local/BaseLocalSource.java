package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.BaseSourceImpl;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public interface BaseLocalSource extends BaseSourceImpl {

	public abstract List<String> listBundlesNames() throws JMOPSourceException;

	public abstract Bundle getBundle(String name) throws JMOPSourceException;

	public abstract void createBundle(Bundle bundle) throws JMOPSourceException;

	/////////////////////////////////////////////////////////////////////////////////////

	public abstract List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException;

	public abstract Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException;

	public abstract void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException;

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public abstract Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException;

	public abstract File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException;

	public abstract boolean exists(Track next, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException;


}
