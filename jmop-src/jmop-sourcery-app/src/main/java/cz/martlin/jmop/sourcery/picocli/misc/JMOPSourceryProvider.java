package cz.martlin.jmop.sourcery.picocli.misc;

import java.io.File;
import java.util.Objects;
import java.util.function.Supplier;

import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.sourcery.config.BaseJMOPSourceryConfig;
import cz.martlin.jmop.sourcery.config.TestingConstantSourceryConfiguration;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryBuilder;

/**
 * The singleton referencing and lazilly providing the JMOP sourcery object.
 * 
 * To break dependency loop (picocli need JMOP Sourcery fascade instance, but
 * the fascade cannot be built until the root dir is known, which gets
 * investigated by the picocli).
 * 
 * 
 * 
 * @author martin
 *
 */
public class JMOPSourceryProvider {

	/**
	 * The singleton instance.
	 */
	private static JMOPSourceryProvider instance;

	/**
	 * Returns this class instance.
	 * 
	 * @return
	 */
	public static JMOPSourceryProvider get() {
		if (instance == null) {
			instance = new JMOPSourceryProvider();
		}
		return instance;
	}

	
	private JMOPSourceryProvider() {
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * The supplier of the root dir.
	 * 
	 */
	private Supplier<File> rootSupplier;
	/**
	 * The actual jmop sourcery.
	 */
	private JMOPSourcery jmop;

	/**
	 * Gets the sourcery. Assumes the root was already specified. Fails if not.
	 * 
	 * @return
	 */
	public JMOPSourcery getSourcery() {
		if (jmop == null) {
			Objects.requireNonNull(rootSupplier, "The rootSupplier not yet set");
			File root = rootSupplier.get();

			Objects.requireNonNull(root, "The rootSupplier gave null as the root path");
			jmop = createJMOP(root);
		}

		return jmop;
	}

	/**
	 * Sets the root dir.
	 * 
	 * @param root
	 */
	public void setRoot(Supplier<File> root) {
		this.rootSupplier = root;
	}

	/**
	 * Constructs the actual JMOP sourcery.
	 * 
	 * @param root
	 * @return
	 */
	private JMOPSourcery createJMOP(File root) {
		BaseErrorReporter reporter = new SimpleErrorReporter();
		BaseProgressListener listener = new PrintingListener(System.out);

		BaseJMOPSourceryConfig config = new TestingConstantSourceryConfiguration();

		JMOPSourcery jmopSourcery = JMOPSourceryBuilder.create(root, reporter, config, listener);
		jmopSourcery.config().load();

		return jmopSourcery;
	}

}
