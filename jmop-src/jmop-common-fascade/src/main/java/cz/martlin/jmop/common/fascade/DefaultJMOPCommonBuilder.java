package cz.martlin.jmop.common.fascade;

import java.io.File;

import cz.martlin.jmop.common.fascade.config.BaseJMOPCommonConfig;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.dflt.AdditionalActionsPerformingMusicbase;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.dflt.VerifiingInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.builder.StorageBuilder;
import cz.martlin.jmop.common.storages.builder.LocatorsBuilder.BundleDataFile;
import cz.martlin.jmop.common.storages.builder.MusicdataManipulatorBuilder.PlaylistFileFormat;
import cz.martlin.jmop.common.storages.builder.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.config.BaseDefaultStorageConfig;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The builder of the JMOP Common fascade. Actually just its musicbvase component.
 * 
 * @author martin
 *
 */
public class DefaultJMOPCommonBuilder {

	/**
	 * Builds the musicbase based on the given parameters.
	 * 
	 * @param root the root directory
	 * @param config the configuration
	 * @param reporter the error reporter.
	 * @return
	 */
	public static BaseMusicbase createMusicbase(File root, BaseJMOPCommonConfig config, BaseErrorReporter reporter) {
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		BaseInMemoryMusicbase verifiing = new VerifiingInMemoryMusicbase(inmemory);
		BaseInMemoryMusicbase additionalPerforming = new AdditionalActionsPerformingMusicbase(verifiing);

		BaseDefaultStorageConfig storageConfig = config;

		TrackFileFormat format = TrackFileFormat.MP3; // FIXME config?

//		BaseMusicbaseStorage storage = X_DefaultStorage.create(root, storageConfig, reporter, verifiing);

		BaseMusicbaseStorage storage = new StorageBuilder().create(DirsLayout.BUNDLES_DIR,
				BundleDataFile.ALL_TRACKS_PLAYLIST, true, PlaylistFileFormat.XSPF, reporter, root, storageConfig,
				additionalPerforming);

//		StorageBuilder builder = new StorageBuilder();
//		BaseMusicbaseStorage storage = builder.create(DirsLayout.BUNDLES_DIR, BundleDataFile.SIMPLE, false, PlaylistFileFormat.TXT, reporter, root, config, TrackFileFormat.MP3, inmemory);

		BaseMusicbase musicbase = new PersistentMusicbase(additionalPerforming, storage);
		return musicbase;
	}



}