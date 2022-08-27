package cz.martlin.jmop.sourcery.fascade;

import cz.martlin.jmop.common.fascade.JMOPCommonMusicbase;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;

public class JMOPSourceryMusicbase extends JMOPCommonMusicbase {

	public JMOPSourceryMusicbase(BaseMusicbase musicbase) {
		super(musicbase, new MusicbaseListingEncapsulator(musicbase), new MusicbaseModyfiingEncapsulator(musicbase));
	}

}
