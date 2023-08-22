package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.fascade.JMOPCommonMusicbase;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;

/**
 * The jmop player musicbase fascade. Does just the generic things, nothing
 * special.
 * 
 * 
 * @author martin
 *
 */
public class JMOPPlayerMusicbase extends JMOPCommonMusicbase {

	public JMOPPlayerMusicbase(BaseMusicbase musicbase) {
		super(musicbase, new MusicbaseListingEncapsulator(musicbase), new MusicbaseModyfiingEncapsulator(musicbase));
	}

/////////////////////////////////////////////////////////////////

}
