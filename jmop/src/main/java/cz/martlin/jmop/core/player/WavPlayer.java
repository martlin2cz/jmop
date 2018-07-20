package cz.martlin.jmop.core.player;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.AbstractPlayer;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public abstract class WavPlayer implements AbstractPlayer {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final TrackFileFormat PLAYER_FORMAT = TrackFileFormat.WAV;
	public static final TrackFileFormat LOCAL_FORMAT = TrackFileFormat.MP3; //XXX ???
	protected final BaseLocalSource local;

	public WavPlayer(BaseLocalSource local) {
		this.local = local;
	}

	@Override
	public void startPlayling(Track track) {
		LOG.info("Starting to play track " + track.getTitle());

		try {
			FFMPEGConverter converter = new FFMPEGConverter(local, LOCAL_FORMAT, PLAYER_FORMAT, (p) -> {
			}, false);
			converter.convert(track);
			File file = local.fileOfTrack(track, PLAYER_FORMAT);

			LOG.info("Playing wav file " + file);
			playWAVfile(file, track);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void playWAVfile(File file, Track track);

	@Override
	public void stop() {
		LOG.info("Stopping to play");
		stopPlaying();
	}

	public abstract void stopPlaying();

}