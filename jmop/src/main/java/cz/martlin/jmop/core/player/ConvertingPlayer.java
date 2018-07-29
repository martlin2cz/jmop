package cz.martlin.jmop.core.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class ConvertingPlayer implements BasePlayer {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	//TODO make global MP3 format configurable
	public static final TrackFileFormat LOCAL_FORMAT = TrackFileFormat.MP3; 

	protected final BaseLocalSource local;
	private final BasePlayer wrapped;
	private final TrackFileFormat formatOfWrapped;

	public ConvertingPlayer(BaseLocalSource local, BasePlayer wrapped, TrackFileFormat formatOfWrapped) {
		this.local = local;
		this.wrapped = wrapped;
		this.formatOfWrapped = formatOfWrapped;
	}
	
	@Override
	public boolean supports(TrackFileFormat format) {
		return true;
	}

	@Override
	public void startPlayling(Track track) throws JMOPSourceException {
		LOG.info("Starting to convertert and play track " + track.getTitle());

		try {
			FFMPEGConverter converter = new FFMPEGConverter(local, LOCAL_FORMAT, formatOfWrapped, false);
			converter.convert(track);
		} catch (Exception e) {
			e.printStackTrace();
		}

		wrapped.startPlayling(track);
	}

	@Override
	public void stop() {
		wrapped.stop();
	}

	@Override
	public void pause() {
		wrapped.pause();
	}

	@Override
	public void resume() {
		wrapped.resume();
	}

	@Override
	public void setHandler(TrackPlayedHandler handler) {
		wrapped.setHandler(handler);
	}

}