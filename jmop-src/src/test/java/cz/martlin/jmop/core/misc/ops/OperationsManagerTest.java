package cz.martlin.jmop.core.misc.ops;

import org.junit.Ignore;
import org.junit.Test;

@Deprecated
public class OperationsManagerTest {

	@Test
	@Ignore
	public void testPrimitive() throws Throwable {
		FxTester.runTheTests(() -> {
			OperationsManager man = new OperationsManager();

			TestingOperations.runPrimitive(man);
		});
	}

	@Test
	@Ignore
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
