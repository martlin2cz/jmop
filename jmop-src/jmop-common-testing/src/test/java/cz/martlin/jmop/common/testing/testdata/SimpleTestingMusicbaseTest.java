package cz.martlin.jmop.common.testing.testdata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.SimpleTestingMusicdata;

/**
 * Just the simple test of the {@link AbstractTestingMusicdata}, in fact its direct
 * subclass, {@link SimpleTestingMusicdata}.
 * 
 * @author martin
 *
 */
public class SimpleTestingMusicbaseTest {

	@Test
	void testWithNoMusicbase() throws Exception {
		AbstractTestingMusicdata tmb = new SimpleTestingMusicdata();
		System.out.println(tmb);

		System.out.println(tmb.daftPunk);
		assertNotNull(tmb.daftPunk);

		System.out.println(tmb.justOneSecond);
		assertNotNull(tmb.justOneSecond);

		System.out.println(tmb.seventeen);
		assertNotNull(tmb.seventeen);

		tmb.close();
	}

}
