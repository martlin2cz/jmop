package cz.martlin.jmop.sourcery.fascade;

import java.io.File;

import cz.martlin.jmop.common.fascade.DefaultJMOPCommonBuilder;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remote.youtube.YoutubeRemoteSource;
import cz.martlin.jmop.sourcery.config.BaseJMOPSourceryConfig;
import cz.martlin.jmop.sourcery.config.TestingConstantSourceryConfiguration;
import cz.martlin.jmop.sourcery.local.BaseTracksImpoter;
import cz.martlin.jmop.sourcery.locals.sourceries.MP3FileMetadataBasedImporter;
import cz.martlin.jmop.sourcery.locals.sourceries.SimpleDirectoryImporter;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSource;

public class JMOPSourceryBuilder {

	public static JMOPSourcery create(File root, BaseErrorReporter reporter, BaseJMOPSourceryConfig configuration,
			BaseProgressListener listener) {

		BaseMusicbase mb = DefaultJMOPCommonBuilder.createMusicbase(root, configuration, reporter);
		JMOPSourceryMusicbase musicbase = new JMOPSourceryMusicbase(mb);

		BaseRemoteSource youtubeRemote = YoutubeRemoteSource.create(configuration, listener);
		JMOPRemote youtube = new JMOPRemote(youtubeRemote, mb);

		BaseTracksImpoter directory = new MP3FileMetadataBasedImporter(true); // new SimpleDirectoryImporter();
		TrackFileFormat trackFileFormat = TrackFileFormat.MP3; // TODO Pick of player
		JMOPLocal local = new JMOPLocal(mb, trackFileFormat, directory);
		
		JMOPConfig config = new JMOPConfig(configuration, mb);
		
		return new JMOPSourcery(musicbase, config, youtube, local);
	}

	public static JMOPSourcery createTesting(File root) {
		BaseJMOPSourceryConfig config = new TestingConstantSourceryConfiguration();
		BaseProgressListener listener = new PrintingListener(System.err);
		BaseErrorReporter reporter = new SimpleErrorReporter();

		return create(root, reporter, config, listener);
	}
}
