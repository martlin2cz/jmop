package cz.martlin.jmop.sourcery.fascade;

import java.io.File;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.core.sources.remote.youtube.YoutubeRemoteSource;
import cz.martlin.jmop.player.fascade.JMOPMusicbase;
import cz.martlin.jmop.player.fascade.dflt.BaseDefaultJMOPConfig;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;

public class JMOPSourceryBuilder {

	public static JMOPSourcery create(File root, BaseErrorReporter reporter, BaseRemotesConfiguration configuration, BaseProgressListener listener, BaseDefaultJMOPConfig musicbaseConfig) {
		
		BaseMusicbase mb = DefaultJMOPPlayerBuilder.createMusicbase(root, musicbaseConfig, reporter);
		JMOPMusicbase musicbase = new JMOPMusicbase(mb);
		
		BaseRemoteSource youtubeRemote = YoutubeRemoteSource.create(configuration, listener);
		JMOPRemote youtube = new JMOPRemote(youtubeRemote, mb);

		JMOPConfig config = new JMOPConfig(configuration, mb);
		return new JMOPSourcery(musicbase, config, youtube);
	}
	
	public static JMOPSourcery createTesting(File root) {
		BaseDefaultJMOPConfig musicbaseConfig = new ConstantDefaultFascadeConfig();
		BaseRemotesConfiguration config = new TestingRemotesConfiguration();
		
		BaseProgressListener listener = new PrintingListener(System.err);
		BaseErrorReporter reporter = new SimpleErrorReporter();
		
		return create(root, reporter, config, listener, musicbaseConfig);
	}
}
