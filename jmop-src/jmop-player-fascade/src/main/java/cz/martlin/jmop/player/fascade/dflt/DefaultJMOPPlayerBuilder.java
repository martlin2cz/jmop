package cz.martlin.jmop.player.fascade.dflt;

import java.io.File;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.common.storages.dflt.DefaultStorage;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.dflt.BaseDefaultEngineConfig;
import cz.martlin.jmop.player.engine.dflt.DefaultEngine;
import cz.martlin.jmop.player.fascade.JMOPConfig;
import cz.martlin.jmop.player.fascade.JMOPMusicbase;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.JMOPPlaying;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.TestingPlayer;

public class DefaultJMOPPlayerBuilder {

	public static JMOPPlayer createTesting() {
		File root = new File(System.getProperty("java.io.tmpdir"), "jmop");
		
		BasePlayer player = new TestingPlayer();
		BaseDefaultJMOPConfig config = new ConstantDefaultFascadeConfig();

		return create(root, player, config);
	}

	public static JMOPPlayer create(File root, BasePlayer player, BaseDefaultJMOPConfig config) {
		
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();

		BaseDefaultStorageConfig storageConfig = config;
		BaseMusicbaseStorage storage = DefaultStorage.create(root, storageConfig, inmemory);

		BaseMusicbase musicbase = new PersistentMusicbase(inmemory, storage);

		BaseDefaultEngineConfig engineConfig = config;
		BasePlayerEngine engine = DefaultEngine.create(player, musicbase, engineConfig);

		return create(config, musicbase, engine);
	}

	private static JMOPPlayer create(BaseDefaultJMOPConfig config, BaseMusicbase musicbase,
			BasePlayerEngine engine) {

		JMOPConfig configModule = new JMOPConfig(config, musicbase, engine);
		JMOPMusicbase musicbaseModule = new JMOPMusicbase(musicbase);
		JMOPPlaying engineModule = new JMOPPlaying(musicbase, engine);

		return new JMOPPlayer(configModule, musicbaseModule, engineModule);
	}

}
