package cz.martlin.jmop.core.misc.ops;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Do not, please.
 * @author martin
 *
 */
@Disabled
@Deprecated
public class OperationsManagerTest {

	@Test
	@Disabled
	public void testPrimitive() throws Throwable {
		FxTester.runTheTests(() -> {
			OperationsManager man = new OperationsManager();

			TestingOperations.runPrimitive(man);
		});
	}

	@Test
	@Disabled
	public void testMore() throws Throwable {
		FxTester.runTheTests(() -> {
			OperationsManager man = new OperationsManager();

			TestingOperations.runMore(man);
		});
	}

	

	@Test
	public void testChain() throws Throwable {
		FxTester.runTheTests(() -> {
			OperationsManager man = new OperationsManager();

			TestingOperations.runChain(man);
		});
	}
}
