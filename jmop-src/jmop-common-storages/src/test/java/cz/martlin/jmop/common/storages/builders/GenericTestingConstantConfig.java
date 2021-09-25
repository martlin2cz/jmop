package cz.martlin.jmop.common.storages.builders;

import cz.martlin.jmop.common.storages.configs.BaseStorageConfiguration;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class GenericTestingConstantConfig implements BaseStorageConfiguration, BaseDefaultStorageConfig {

	public static final String ATP_NAME = "all the tracks";

	@Override
	public String getAllTracksPlaylistName() {
		return ATP_NAME;
	}

	@Override
	public TrackFileFormat trackFileFormat() {
		return TrackFileFormat.MP3;
	}

}