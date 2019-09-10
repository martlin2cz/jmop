package cz.martlin.jmop.core.sources.locals;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jmop.core.sources.locals.funky.FunkyFilesNamer;

public class DefaultFilesNamerTest {

	@Test
	public void test() {
		assertEquals("abc", FunkyFilesNamer.clean("abc")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("ABC", FunkyFilesNamer.clean("ABC")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("foo42BAR", FunkyFilesNamer.clean("foo42BAR")); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals("Lorem__ipsum_-_dolor__Sit_amet_", // //$NON-NLS-1$
				FunkyFilesNamer.clean("Lorem, ipsum - dolor? Sit amet!")); //$NON-NLS-1$

		assertEquals("P__li___lu_ou_k__k____p_l___belsk___dy", // //$NON-NLS-1$
				FunkyFilesNamer.clean("Příliš žluťoučký kůň úpěl ďábelské ódy")); //$NON-NLS-1$

		assertEquals("_________", FunkyFilesNamer.clean("¯\\_(ツ)_/¯")); //$NON-NLS-1$ //$NON-NLS-2$

	}

}
