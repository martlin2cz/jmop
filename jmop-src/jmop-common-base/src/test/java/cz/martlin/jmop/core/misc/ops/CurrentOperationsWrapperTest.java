package cz.martlin.jmop.core.misc.ops;

import org.junit.Ignore;
import org.junit.Test;

import javafx.collections.ListChangeListener;

public class CurrentOperationsWrapperTest {

	@Test
	@Ignore
	public void testPrimitive() throws Throwable {
		FxTester.runTheTests(() -> {
			OperationsManager man = new OperationsManager();
			CurrentOperationsWrapper wrapper = new CurrentOperationsWrapper(man);
			bindTheWrapper(wrapper);

			TestingOperations.runPrimitive(man);

		});
	}
	
	@Test
	public void testMore() throws Throwable {
		FxTester.runTheTests(() -> {
			OperationsManager man = new OperationsManager();
			CurrentOperationsWrapper wrapper = new CurrentOperationsWrapper(man);
			bindTheWrapper(wrapper);

			TestingOperations.runMore(man);

		});
	}

	private void bindTheWrapper(CurrentOperationsWrapper wrapper) {
		wrapper.primaryOperationName().addListener((obs, oldVal, newVal) -> System.out.println("NAME: " + newVal));
		wrapper.primaryOperationData().addListener((obs, oldVal, newVal) -> System.out.println("DATA: " + newVal));
		wrapper.primaryOperationProgress().addListener((obs, oldVal, newVal) -> System.out.println("prgs: " + newVal));
		wrapper.allOperations().addListener((ListChangeListener<OperationTask<?, ?>>) //
				(e) -> System.out.println("change: " + e.next() + " +" + e.getAddedSize() + " -" + e.getRemovedSize()));
	}

}
