package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.BaseSourceImpl;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

/**
 * General local source. Specifies methods for "loading and saving of bundles,
 * playlists and tracks". This means creation of bundles, manipulation with
 * playlists (creating and updating) and locating of track files based on
 * location.
 * 
 * @author martin
 *
 */
public interface BaseLocalSource extends BaseSourceImpl {

	/**
	 * Loads and lists alls bundles names.
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public abstract List<String> listBundlesNames() throws JMOPSourceException;

	/**
	 * Loads and returns bundle of given name.
	 * 
	 * @param name
	 * @return
	 * @throws JMOPSourceException
	 */
	public abstract Bundle getBundle(String name) throws JMOPSourceException;

	/**
	 * Creates bundle in the local source. If yet exists, could fail.
	 * 
	 * @param bundle
	 * @throws JMOPSourceException
	 */
	public abstract void createBundle(Bundle bundle) throws JMOPSourceException;

	/**
	 * Saves (the updated) bundle in the local source.
	 * 
	 * @param bundle
	 * @throws JMOPSourceException
	 */
	public abstract void saveBundle(Bundle bundle) throws JMOPSourceException;

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Loads and lists all playlists names within the given bundle.
	 * 
	 * @param bundle
	 * @return
	 * @throws JMOPSourceException
	 */
	public abstract List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException;

	/**
	 * Loads and returns playlist of given name and within the given bundle.
	 * 
	 * @param bundle
	 * @param name
	 * @return
	 * @throws JMOPSourceException
	 */
	public abstract Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException;

	/**
	 * Saves given playlist into the local source. If not yet existed in the
	 * source, may create it.
	 * 
	 * @param bundle
	 * @param playlist
	 * @throws JMOPSourceException
	 */
	public abstract void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException;

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Infers informations about the track with given identifier within the
	 * given bundle.
	 */
	@Override
	public abstract Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException;

	/**
	 * Loads and returns the file of given track located at given location with
	 * given format.
	 * 
	 * @param track
	 * @param location
	 * @param format
	 * @return
	 * @throws JMOPSourceException
	 */
	public abstract File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException;

	/**
	 * Checks and and returns whether the file of given track located at given
	 * location with given format exists or not.
	 * 
	 * @param next
	 * @param location
	 * @param format
	 * @return
	 * @throws JMOPSourceException
	 */
	public abstract boolean exists(Track next, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException;

}
