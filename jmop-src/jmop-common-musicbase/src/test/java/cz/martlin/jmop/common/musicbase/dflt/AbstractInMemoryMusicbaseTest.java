package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseLoading;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.testdata.TestingMusicdataWithMusicbase;

/**
 * Just an abstract base test for the inmemory musicbases.
 * @author martin
 *
 */
public abstract class AbstractInMemoryMusicbaseTest {
	
	@RegisterExtension
	public TestingMusicdataExtension tme;
	
	public AbstractInMemoryMusicbaseTest() {
		tme = TestingMusicdataExtension.withMusicbase(this::createMusicbase, null);
	}
	
	protected abstract BaseInMemoryMusicbase createMusicbase();
	
	@Test
	void testJustCreate() {
		BaseMusicbaseModifing musicbase = tme.getMusicbase();
		BaseMusicbaseLoading loading = (BaseMusicbaseLoading) musicbase;
		System.out.println(musicbase);
		
		assertTrue(loading.bundles().stream().map(Bundle::getName).collect(Collectors.toSet()).contains("Daft Punk"));
	}
	
	@Test
	void testCreateAndCompareToItself() {
		BaseMusicbaseModifing musicbase = tme.getMusicbase();
		
		assertEquals(musicbase, musicbase);
	}
	
	@Test
	void testCreateAndCompareToDifferent() throws Exception {
		BaseMusicbaseModifing musicbase = tme.getMusicbase();
		
		BaseMusicbaseModifing another = createMusicbase();
		TestingMusicdataWithMusicbase testing = new TestingMusicdataWithMusicbase(another, null);
		
		assertEquals(musicbase.toString(), another.toString());
		assertEquals(musicbase, another);
		
		testing.close();
	}

	
	
}
