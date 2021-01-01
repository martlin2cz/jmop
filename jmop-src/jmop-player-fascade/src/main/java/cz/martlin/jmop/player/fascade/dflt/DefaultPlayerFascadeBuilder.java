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
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.TestingPlayer;

public class DefaultPlayerFascadeBuilder {

	public static JMOPPlayerFascade create(File root, BasePlayer player, BaseDefaultFascadeConfig config) {
		
		BaseDefaultStorageConfig storageConfig = config;
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		
		BaseMusicbaseStorage storage = DefaultStorage.create(root, storageConfig, inmemory);
		
		BaseMusicbase musicbase = new PersistentMusicbase(inmemory, storage);

		BaseDefaultEngineConfig engineConfig = config;
		BasePlayerEngine engine = DefaultEngine.create(player, musicbase, engineConfig);
		
		return new JMOPPlayerFascade(engine, musicbase);
	}
	
	public static JMOPPlayerFascade createTesting() {
		File root = new File(System.getProperty("java.io.tmpdir"), "jmop");
		BasePlayer player = new TestingPlayer();
		BaseDefaultFascadeConfig config = new ConstantDefaultFascadeConfig();
		
		return create(root, player, config);
	}

}
