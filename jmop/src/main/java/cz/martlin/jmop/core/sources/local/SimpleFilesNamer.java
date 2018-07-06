package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.SourceKind;

public abstract class SimpleFilesNamer implements BaseFilesNamer {

	protected static final String DOT = ".";


	public SimpleFilesNamer() {
	}

	@Override
	public File fileOfTrack(File root, Bundle bundle, Track track, TrackFileFormat format) {
		File bundleDir = directoryOfBundle(root, bundle.getKind(), bundle.getName());

		String trackFileName = filenameOfTrack(track);

		String extension = format.getExtension();

		String trackFile = trackFileName + DOT + extension;
		return new File(bundleDir, trackFile);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	protected abstract String filenameOfTrack(Track track);

	@Override
	public File directoryOfBundle(File root, SourceKind source, String name) {
		String directoryName = dirnameOfBundle(source, name);
		return new File(root, directoryName);
	}

	protected abstract String dirnameOfBundle(SourceKind source, String name);

	@Override
	public String dirToBundleName(File directory) {
		String directoryName = directory.getName();
		return bundleNameOfDirectory(directoryName);
	}

	protected abstract String bundleNameOfDirectory(String directoryName);


	@Override
	public boolean isBundleDirectory(File directory) {
		String name = directory.getName();
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
	public File fileOfPlaylist(File root, SourceKind source, String bundleName, String playlistName) {
		File bundleDir = directoryOfBundle(root, source, bundleName);
		String playlistFileName = filenameOfPlaylist(playlistName);

		return new File(bundleDir, playlistFileName);
	}

	protected abstract String filenameOfPlaylist(String name);

	@Override
	public boolean isPlaylistFile(File file) {
		String name = file.getName();
		return isPlaylistFile(name);
	}

	protected abstract boolean isPlaylistFile(String fileName);

}
