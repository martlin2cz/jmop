package cz.martlin.jmop.core.sources.locals.electronic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ElectronicFilesNamerTest {

	@Test
	public void test() {
		assertEquals("abc", ElectronicFilesNamer.clean("abc")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("ABC", ElectronicFilesNamer.clean("ABC")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("foo42BAR", ElectronicFilesNamer.clean("foo42BAR")); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals("Lorem__ipsum_-_dolor__Sit_amet_", // //$NON-NLS-1$
				ElectronicFilesNamer.clean("Lorem, ipsum - dolor? Sit amet!")); //$NON-NLS-1$

		assertEquals("P__li___lu_ou_k__k____p_l___belsk___dy", // //$NON-NLS-1$
				ElectronicFilesNamer.clean("Příliš žluťoučký kůň úpěl ďábelské ódy")); //$NON-NLS-1$

		assertEquals("_________", ElectronicFilesNamer.clean("¯\\_(ツ)_/¯")); //$NON-NLS-1$ //$NON-NLS-2$

	}

}
