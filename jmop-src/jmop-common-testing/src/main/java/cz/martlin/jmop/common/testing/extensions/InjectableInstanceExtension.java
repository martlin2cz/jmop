package cz.martlin.jmop.common.testing.extensions;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

/**
 * Use the RegisterExtension annotation instead.
 * 
 * @author martin
 *
 */
@Deprecated
public class InjectableInstanceExtension implements Extension, TestInstancePostProcessor {

	public InjectableInstanceExtension() {
		super();
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		if (testInstance instanceof TestWithInjection) {
			TestWithInjection<InjectableInstanceExtension> testWithInjection = //
					(TestWithInjection<InjectableInstanceExtension>) testInstance;

			testWithInjection.inject(this);
		} else {
			System.err.println("Warning, " + testInstance + " does not support injection. " //
					+ "An " + this + " would not be injected into the test.");
		}
	}

	public static interface TestWithInjection<T extends InjectableInstanceExtension> {
		public void inject(T extension);
	}

}