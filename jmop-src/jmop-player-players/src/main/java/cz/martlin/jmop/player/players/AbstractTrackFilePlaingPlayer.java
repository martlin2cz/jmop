package cz.martlin.jmop.player.players;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.players.BasePlayer;

/**
 * The general {@link BasePlayer} with some common functions. Holds all required
 * values in fields and when the internal status changes, fires event.
 * 
 * @author martin
 *
 */
public abstract class AbstractTrackFilePlaingPlayer extends AbstractPlayer implements BasePlayer {

	private final TracksSource local;
	
	public AbstractTrackFilePlaingPlayer(TracksSource local) {
		super();
		this.local = local;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void doStartPlaying(Track track) throws JMOPMusicbaseException {
		File file = local.trackFile(track);
		doStartPlaingFile(track, file);
	}

	protected abstract void doStartPlaingFile(Track track, File file);
	
	/////////////////////////////////////////////////////////////////////////////////////

}
