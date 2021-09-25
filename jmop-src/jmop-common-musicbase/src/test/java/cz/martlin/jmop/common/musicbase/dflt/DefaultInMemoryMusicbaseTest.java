package cz.martlin.jmop.common.musicbase.dflt;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;

/**
 * The simple test of the {@link DefaultInMemoryMusicbase}.
 * 
 * @author martin
 *
 */
class DefaultInMemoryMusicbaseTest extends AbstractInMemoryMusicbaseTest {

	@Override
	protected BaseInMemoryMusicbase createMusicbase() {
		return new DefaultInMemoryMusicbase();
	}
}
