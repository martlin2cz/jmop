package cz.martlin.jmop.player.players;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;

/**
 * The general {@link BasePlayer} with some common functions. Holds all required
 * values in fields and when the internal status changes, fires event.
 * 
 * @author martin
 *
 */
public abstract class AbstractTrackFilePlaingPlayer extends AbstractPlayer implements BasePlayer {

	
	public AbstractTrackFilePlaingPlayer() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void doStartPlaying(Track track)  {
		File file = track.getFile();
		doStartPlaingFile(track, file);
	}

	protected abstract void doStartPlaingFile(Track track, File file);
	
	/////////////////////////////////////////////////////////////////////////////////////

}
