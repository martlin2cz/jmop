package cz.martlin.jmop.common.storages.musicdatafile;

import java.io.File;
import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.storages.fileobjects.BaseFileObjectIO;
import cz.martlin.jmop.common.storages.fileobjects.BaseFileObjectManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The common extended playlist manipulator.
 * 
 * All it does it delegates the load/create/save to {@link BaseFileObjectIO}
 * instance, and all the get/set bundle/playlist data to
 * {@link BaseFileObjectManipulator} instance.
 * 
 * It works with some abstract entity (which can be loaded, updated and saved),
 * for instance some kind of playlist internal representation, JSON object or
 * so.
 * 
 * @author martin
 *
 * @param <PT>
 */
public class CommonMusicdataFileManipulator<PT> implements BaseMusicdataFileManipulator {

	private final String fileExtension;
	private final BaseFileObjectIO<PT> io;
	private final BaseFileObjectManipulator<PT> extender;

	public CommonMusicdataFileManipulator(String fileExtension, BaseFileObjectIO<PT> io,
			BaseFileObjectManipulator<PT> extender) {

		super();
		this.fileExtension = fileExtension;
		this.io = io;
		this.extender = extender;
	}

	@Override
	public String fileExtension() {
		return fileExtension;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void saveBundleData(Bundle bundle, Set<Track> bundleTracks, File file, TracksLocator tracksSource)
			throws JMOPPersistenceException {
	
		PT xfile = io.tryLoadOrCreate(file);
	
		extender.setBundleDataAndTracks(bundle, bundleTracks, tracksSource, xfile);

		io.save(xfile, file);
	}

	@Override
	public void savePlaylistData(Playlist playlist, File file, TracksLocator tracksSource)
			throws JMOPPersistenceException {

		PT xfile = io.tryLoadOrCreate(file);

		extender.setPlaylistData(playlist, tracksSource, xfile);

		io.save(xfile, file);
	}

	@Override
	public Bundle loadBundleData(File file) throws JMOPPersistenceException {

		PT xfile = io.load(file);

		return extender.getBundleData(xfile);
	}

	@Override
	public Set<Track> loadBundleTracks(File file, Bundle bundle) throws JMOPPersistenceException {
		PT xfile = io.load(file);

		return extender.getBundleTracks(xfile, bundle);
	}
	
	@Override
	public Playlist loadPlaylistData(Bundle bundle, Map<String, Track> tracks, File file)
			throws JMOPPersistenceException {

		PT xfile = io.load(file);

		return extender.getPlaylistData(xfile, bundle, tracks);
	}

}