package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public abstract class AbstractFilesNamer implements BaseFilesNamer {


	protected static final String DOT = ".";

	private final File rootDir;

	public AbstractFilesNamer(File rootDir) {
		this.rootDir = rootDir;
	}

	@Override
	public File fileOfTrack(Bundle bundle, Track track, TrackFileFormat format) {
		File bundleDir = directoryOfBundle(bundle);

		String trackFileName = filenameOfTrack(track);

		String extension = format.getExtension();

		String trackFile = trackFileName + DOT + extension;
		return new File(bundleDir, trackFile);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	protected abstract String filenameOfTrack(Track track);

	@Override
	public File directoryOfBundle(Bundle bundle) {
		String directoryName = dirnameOfBundle(bundle);
		return new File(rootDir, directoryName);
	}

	protected abstract String dirnameOfBundle(Bundle bundle);

	@Override
	public String dirToBundleName(File directory) {
		String directoryName = directory.getName();
		return bundleNameOfDirectory(directoryName);
	}

	protected abstract String bundleNameOfDirectory(String directoryName);

	@Override
	public List<File> listBundles() {
		File[] children = rootDir.listFiles((f) -> isBundleDir(f));

		return Arrays.asList(children);
	}

	private boolean isBundleDir(File fileOrDir) {
		if (!fileOrDir.isDirectory()) {
			return false;
		}

		String name = fileOrDir.getName();
		return isBundleDir(name);
	}

	protected abstract boolean isBundleDir(String directoryName);

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String fileToPlaylistName(File file) {
		String filename = file.getName();

		return nameOfPlaylist(filename);
	}

	protected abstract String nameOfPlaylist(String filename);

	@Override
	public File fileOfPlaylist(Bundle bundle, String name) {
		File bundleDir = directoryOfBundle(bundle);
		String playlistFileName = nameOfPlaylist(name);

		return new File(bundleDir, playlistFileName);
	}

	@Override
	public List<File> listPlaylists(Bundle bundle) {
		File bundleDir = directoryOfBundle(bundle);
		File[] children = bundleDir.listFiles((f) -> isPlaylistFile(f));

		return Arrays.asList(children);
	}

	private boolean isPlaylistFile(File fileOrDir) {
		if (!fileOrDir.isFile()) {
			return false;
		}

		String name = fileOrDir.getName();
		return isPlaylistFile(name);
	}

	protected abstract boolean isPlaylistFile(String directoryName);
	/*
	 * 
	 * @Override public File directoryOfBundle(Bundle bundle) { String
	 * bundleName = dirnameOfBundle(bundle); return new File(rootDir,
	 * bundleName); }
	 * 
	 * protected abstract String dirnameOfBundle(Bundle bundle);
	 * 
	 * protected abstract String filenameOfTrack(Track track);
	 * 
	 * @Override public List<File> listBundles() { File[] children =
	 * rootDir.listFiles((f) -> isBundle(f));
	 * 
	 * return Arrays.asList(children); }
	 * 
	 * protected abstract boolean isBundle(File fileOrDir);
	 * 
	 * 
	 * @Override public boolean isBundleName(String directory) { return
	 * !directory.startsWith(HIDDEN_FILE_PREFIX); }
	 * 
	 * @Override public String dirToBundleName(String dirname) { return dirname;
	 * }
	 * 
	 * @Override public List<File> listPlaylists(Bundle bundle) { File[]
	 * children = rootDir.listFiles((f) -> isPlaylist(f));
	 * 
	 * return Arrays.asList(children); }
	 * 
	 * private boolean isPlaylist(File f) { // TODO Auto-generated method stub
	 * return true; }
	 * 
	 * @Override public String fileToPlaylistName(String filename) { return
	 * filename; //TODO remove suffix }
	 * 
	 * @Override public File fileOfPlaylist(Bundle bundle, String name) { //
	 * TODO Auto-generated method stub return null; }
	 */

}
