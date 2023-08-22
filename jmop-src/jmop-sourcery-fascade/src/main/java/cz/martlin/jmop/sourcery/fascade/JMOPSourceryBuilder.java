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
import cz.martlin.jmop.sourcery.local.BasePlaylistImporter;
import cz.martlin.jmop.sourcery.local.BaseTracksFromDirOrFileImporter;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;
import cz.martlin.jmop.sourcery.locals.abstracts.DefaultTracksFromDirOrFileImporter;
import cz.martlin.jmop.sourcery.locals.filesystem.MP3FileMetadataBasedImporter;
import cz.martlin.jmop.sourcery.locals.playlists.FromExternalXSPFPlaylistPlaylistIImporter;
import cz.martlin.jmop.sourcery.locals.playlists.FromXSPFPlaylistTracksImpoter;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSource;

/**
 * The JMOP Sourcery fascade builder.
 * 
 * @author martin
 *
 */
public class JMOPSourceryBuilder {

	/**
	 * Creates one based on the given parameters.
	 * 
	 * @param root          the root directory
	 * @param reporter      the error reporter
	 * @param configuration the configuration
	 * @param listener      the progress listener
	 * @return
	 */
	public static JMOPSourcery create(File root, BaseErrorReporter reporter, BaseJMOPSourceryConfig configuration,
			BaseProgressListener listener) {

		BaseMusicbase mb = DefaultJMOPCommonBuilder.createMusicbase(root, configuration, reporter);
		JMOPSourceryMusicbase musicbase = new JMOPSourceryMusicbase(mb);

		BaseRemoteSource youtubeRemote = YoutubeRemoteSource.create(configuration, listener);
		JMOPRemote youtube = new JMOPRemote(youtubeRemote, mb);

		TrackFileFormat trackFileFormat = TrackFileFormat.MP3; // TODO Pick of player

		MP3FileMetadataBasedImporter tracksFromMP3FileImpoter = new MP3FileMetadataBasedImporter(true); // new
																										// SimpleTrackImporter();
		BaseTracksFromDirOrFileImporter tracksFromDirImpoter = new DefaultTracksFromDirOrFileImporter(
				tracksFromMP3FileImpoter);
		BaseTracksFromFileImporter tracksFromPlaylistImpoter = new FromXSPFPlaylistTracksImpoter();
		BasePlaylistImporter playlistFromPlaylistImpoter = new FromExternalXSPFPlaylistPlaylistIImporter();

		JMOPLocal local = new JMOPLocal(mb, trackFileFormat, tracksFromDirImpoter, tracksFromPlaylistImpoter,
				playlistFromPlaylistImpoter);

		JMOPConfig config = new JMOPConfig(configuration, mb);

		return new JMOPSourcery(musicbase, config, youtube, local);
	}

	/**
	 * Creates testing fascade.
	 * 
	 * @param root
	 * @return
	 */
	public static JMOPSourcery createTesting(File root) {
		BaseJMOPSourceryConfig config = new TestingConstantSourceryConfiguration();
		BaseProgressListener listener = new PrintingListener(System.err);
		BaseErrorReporter reporter = new SimpleErrorReporter();

		return create(root, reporter, config, listener);
	}
}
