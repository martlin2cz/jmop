package cz.martlin.jmop.common.storages.fileobjects;

import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An manipulator (storer and extracter) of the JMOP data (bundle data, bundle
 * tracks, playlist data+tracks), to and from some file object.
 * 
 * It can be plain File, but rather some instance loaded by
 * {@link BaseFileObjectIO}.
 * 
 * @author martin
 *
 * @param <FT>
 */
public interface BaseFileObjectManipulator<FT> {

	/**
	 * Populates the bundle data and list of tracks into the given file object.
	 * 
	 * @param bundle
	 * @param tracks
	 * @param tracksSource
	 * @param fileObject
	 * @throws JMOPPersistenceException
	 */
	public void setBundleDataAndTracks(Bundle bundle, Set<Track> tracks, TracksLocator tracksSource, FT fileObject)
			throws JMOPPersistenceException;

	/**
	 * Populates the playlist data into the given file object.
	 * 
	 * @param playlist
	 * @param tracks
	 * @param fileObject
	 * @throws JMOPPersistenceException
	 */
	public void setPlaylistData(Playlist playlist, TracksLocator tracks, FT fileObject) throws JMOPPersistenceException;

	/**
	 * Loads the bundle data from the given file object.
	 * 
	 * @param fileObject
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public Bundle getBundleData(FT fileObject) throws JMOPPersistenceException;

	/**
	 * Loads the bundle tracks from the given file object.
	 * 
	 * @param fileObject
	 * @param bundle TODO
	 * @return
	 * @throws JMOPPersistenceException 
	 */
	public Set<Track> getBundleTracks(FT fileObject, Bundle bundle) throws JMOPPersistenceException;

	/**
	 * Loads the playlist data from the given fiel object, by using the specified
	 * tracks.
	 * 
	 * @param fileObject
	 * @param bundle
	 * @param tracks
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public Playlist getPlaylistData(FT fileObject, Bundle bundle, Map<String, Track> tracks)
			throws JMOPPersistenceException;

}
