package cz.martlin.jmop.core.sources.locals;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jmop.core.sources.locals.DefaultFilesNamer;

public class DefaultFilesNamerTest {

	@Test
	public void test() {
		assertEquals("abc", DefaultFilesNamer.clean("abc"));
		assertEquals("ABC", DefaultFilesNamer.clean("ABC"));
		assertEquals("foo42BAR", DefaultFilesNamer.clean("foo42BAR"));

		assertEquals("Lorem__ipsum_-_dolor__Sit_amet_", //
				DefaultFilesNamer.clean("Lorem, ipsum - dolor? Sit amet!"));

		assertEquals("P__li___lu_ou_k__k____p_l___belsk___dy", //
				DefaultFilesNamer.clean("Příliš žluťoučký kůň úpěl ďábelské ódy"));

		assertEquals("_________", DefaultFilesNamer.clean("¯\\_(ツ)_/¯"));

	}

}
