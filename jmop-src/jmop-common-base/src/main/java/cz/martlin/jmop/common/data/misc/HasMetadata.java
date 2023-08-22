package cz.martlin.jmop.common.data.misc;

import cz.martlin.jmop.common.data.model.Metadata;

/**
 * An indicator of element having metadata (bundle, playlist and track may
 * implement that).
 * 
 * Usefull to avoid unncessary class casts with manipulating with the metadata,
 * no matter its owner.
 * 
 * @author martin
 *
 */
public interface HasMetadata {
	
	/**
	 * Returns the metadata.
	 * 
	 * @return
	 */
	public Metadata getMetadata();
}
