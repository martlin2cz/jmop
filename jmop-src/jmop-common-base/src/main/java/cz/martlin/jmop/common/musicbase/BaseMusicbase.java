package cz.martlin.jmop.common.musicbase;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicbase extends BaseMusicbaseLoading, BaseMusicbaseModifing, TracksSource {

	public void load() throws JMOPMusicbaseException;
	
	
}
