package cz.martlin.jmop.common.musicbase;

import cz.martlin.jmop.common.utils.Lifecycle;

/**
 * The musicbase. The database with all your favourite music.
 * 
 * Encapsulates all the nescessary read-write, load-save, create/update/delete
 * operations.
 * 
 * Either work on the fly (like queriing some SQL database), or load all at once
 * at start into in-memory-musicbase and then just apply the particular changes
 * (by the storage).
 * 
 * @author martin
 *
 */
public interface BaseMusicbase //
		extends BaseMusicbaseLoading, BaseMusicbaseModifing, Lifecycle {

}
