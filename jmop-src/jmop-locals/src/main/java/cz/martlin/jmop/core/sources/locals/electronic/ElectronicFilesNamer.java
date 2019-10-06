package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public class ElectronicFilesNamer implements BaseFilesNamer {

	@Override
	public String directoryNameOfBundle(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fileNameOfPlaylist(String playlistName, String playlistFileExtension) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fileNameOfTrack(Track track, TrackFileFormat format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String nameOfTempDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String nameOfCacheDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File bundleDirOfBundleDirName(File root, String bundleDirName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File bundleDirOfBundleName(File root, String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File cacheBundleDir(File root, String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File tempBundleDir(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File playlistFileOfPlaylist(File root, String bundleDirName, String playlistName,
			String playlistFileExtension) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File playlistFileOfFile(File root, String bundleDirName, String playlistFileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
