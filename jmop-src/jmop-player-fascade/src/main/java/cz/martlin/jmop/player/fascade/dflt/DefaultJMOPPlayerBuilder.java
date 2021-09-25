package cz.martlin.jmop.player.fascade.dflt;

import java.io.File;
import java.util.function.Function;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.dflt.VerifiingInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.BundleDataFile;
import cz.martlin.jmop.common.storages.builders.MusicdataManipulatorBuilder.PlaylistFileFormat;
import cz.martlin.jmop.common.storages.builders.StorageBuilder;
import cz.martlin.jmop.common.storages.builders.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.common.testing.resources.TestingRootDir;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.dflt.BaseDefaultEngineConfig;
import cz.martlin.jmop.player.engine.dflt.DefaultEngine;
import cz.martlin.jmop.player.fascade.JMOPConfig;
import cz.martlin.jmop.player.fascade.JMOPMusicbase;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.JMOPPlaying;
import cz.martlin.jmop.player.fascade.JMOPStatus;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.TestingPlayer;

/**
 *
 */
public class DefaultJMOPPlayerBuilder {

	public static JMOPPlayer createTesting() {
		TestingRootDir rootDir = new TestingRootDir(DefaultJMOPPlayerBuilder.class);
		File root = rootDir.getFile();
		
		Function<TracksLocator, BasePlayer> playerProducer = (ts) -> new TestingPlayer();
		BaseDefaultJMOPConfig config = new ConstantDefaultFascadeConfig();

		BaseErrorReporter reporter = new SimpleErrorReporter();
		
		return create(root, playerProducer, config, reporter);
	}

	public static JMOPPlayer create(File root, Function<TracksLocator, BasePlayer> playerProducer,  BaseDefaultJMOPConfig config, BaseErrorReporter reporter) {
		
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		BaseInMemoryMusicbase verifiing = new VerifiingInMemoryMusicbase(inmemory);
		
		BaseDefaultStorageConfig storageConfig = config;

		TrackFileFormat format = TrackFileFormat.MP3; //FIXME

//		BaseMusicbaseStorage storage = X_DefaultStorage.create(root, storageConfig, reporter, verifiing);

		BaseMusicbaseStorage storage = new StorageBuilder().create(DirsLayout.BUNDLES_DIR, BundleDataFile.ALL_TRACKS_PLAYLIST, false, PlaylistFileFormat.XSPF, reporter, root, storageConfig, format, inmemory);

//		StorageBuilder builder = new StorageBuilder();
//		BaseMusicbaseStorage storage = builder.create(DirsLayout.BUNDLES_DIR, BundleDataFile.SIMPLE, false, PlaylistFileFormat.TXT, reporter, root, config, TrackFileFormat.MP3, inmemory);
	
		BaseMusicbase musicbase = new PersistentMusicbase(verifiing, storage);
		
		BasePlayer player = playerProducer.apply(musicbase);
		BaseDefaultEngineConfig engineConfig = config;
		BasePlayerEngine engine = DefaultEngine.create(player, musicbase, engineConfig);

		return create(config, musicbase, engine);
	}

	private static JMOPPlayer create(BaseDefaultJMOPConfig config, BaseMusicbase musicbase,
			BasePlayerEngine engine) {

		JMOPConfig configModule = new JMOPConfig(config, musicbase, engine);
		JMOPMusicbase musicbaseModule = new JMOPMusicbase(musicbase);
		JMOPPlaying engineModule = new JMOPPlaying(config, musicbase, engine);
		JMOPStatus statusModule = new JMOPStatus(engine);

		return new JMOPPlayer(configModule, musicbaseModule, engineModule, statusModule);
	}

}
