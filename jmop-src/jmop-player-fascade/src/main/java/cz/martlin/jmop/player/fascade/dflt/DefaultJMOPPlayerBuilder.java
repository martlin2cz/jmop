package cz.martlin.jmop.player.fascade.dflt;

import java.io.File;

import cz.martlin.jmop.common.fascade.DefaultJMOPCommonBuilder;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.testing.resources.TestingRootDir;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.dflt.BaseDefaultEngineConfig;
import cz.martlin.jmop.player.engine.dflt.DefaultEngine;
import cz.martlin.jmop.player.fascade.JMOPConfig;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.JMOPPlayerMusicbase;
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

		BasePlayer player = new TestingPlayer();
		BaseJMOPPlayerConfig config = new ConstantDefaultFascadeConfig();

		BaseErrorReporter reporter = new SimpleErrorReporter();

		return create(root, player, config, reporter);
	}

	public static JMOPPlayer create(File root, BasePlayer player, BaseJMOPPlayerConfig config,
			BaseErrorReporter reporter) {

		BaseMusicbase musicbase = DefaultJMOPCommonBuilder.createMusicbase(root, config, reporter);

		BasePlayerEngine engine = createEngine(player, config, musicbase);

		return create(config, musicbase, engine);
	}
	

	private static BasePlayerEngine createEngine(BasePlayer player, BaseJMOPPlayerConfig config,
			BaseMusicbase musicbase) {

		BaseDefaultEngineConfig engineConfig = config;
		BasePlayerEngine engine = DefaultEngine.create(player, musicbase, engineConfig);

		return engine;
	}


	private static JMOPPlayer create(BaseJMOPPlayerConfig config, BaseMusicbase musicbase, BasePlayerEngine engine) {

		JMOPConfig configModule = new JMOPConfig(config, musicbase, engine);
		JMOPPlayerMusicbase musicbaseModule = new JMOPPlayerMusicbase(musicbase);
		JMOPPlaying engineModule = new JMOPPlaying(config, musicbase, engine);
		JMOPStatus statusModule = new JMOPStatus(engine);

		return new JMOPPlayer(configModule, musicbaseModule, engineModule, statusModule);
	}
	
}
