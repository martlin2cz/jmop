package cz.martlin.jmop.common.musicbase.persistent;

import cz.martlin.jmop.common.musicbase.BaseMusicbaseLoading;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;

/**
 * The inmemory musicbase. Somehow (implementation specific) keeps stored all
 * the music data, in-memory (nonpersistent).
 * 
 * @author martin
 *
 */
public interface BaseInMemoryMusicbase extends BaseMusicbaseLoading, BaseMusicbaseModifing {

}
