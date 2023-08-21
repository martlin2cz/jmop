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
import picocli.CommandLine.ParentCommand;

public class JMOPSourceryProvider {
	
	private static JMOPSourceryProvider instance;

	public static JMOPSourceryProvider get() {
		if (instance == null) {
			instance = new JMOPSourceryProvider();
		}
		return instance;
	}

	private JMOPSourceryProvider() {
	}

	///////////////////////////////////////////////////////////////////////////

//
//	public JMOPSourcery getSourcery() {
//		Objects.requireNonNull(jmop, "The JMOP is not yet initialised");
//
//		return jmop;
//	}
//
//	public void setRoot(File root) {
//		this.jmop = createJMOP(root);
//	}

	private Supplier<File> rootSupplier;
	private JMOPSourcery jmop;

	public JMOPSourcery getSourcery() {
		if (jmop == null) {
			Objects.requireNonNull(rootSupplier, "The rootSupplier not yet set");
			File root = rootSupplier.get();

			Objects.requireNonNull(root, "The rootSupplier gave null as the root path");
			jmop = createJMOP(root);
		}

		return jmop;
	}

	public void setRoot(Supplier<File> root) {
		this.rootSupplier = root;
	}

	private JMOPSourcery createJMOP(File root) {
		BaseErrorReporter reporter = new SimpleErrorReporter();
		BaseProgressListener listener = new PrintingListener(System.out);

		BaseJMOPSourceryConfig config = new TestingConstantSourceryConfiguration();

		JMOPSourcery jmopSourcery = JMOPSourceryBuilder.create(root, reporter, config, listener);
		jmopSourcery.config().load();

		return jmopSourcery;
	}

}
