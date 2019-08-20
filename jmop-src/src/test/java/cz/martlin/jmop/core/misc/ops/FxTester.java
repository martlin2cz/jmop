package cz.martlin.jmop.core.misc.ops;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
/**
 * More problems that positives are bound with this class.
 * @author martin
 *
 */
@Deprecated
public class FxTester extends Application {

	private static RunnableWithException theCodeToRun;
	private static Throwable theCaughtException;

	public FxTester() {
		super();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			theCodeToRun.run();
		} catch (Throwable e) {
			theCaughtException = e;
		} finally {
			primaryStage.close();
			Platform.exit();
		}
	}

	public static void runTheTests(RunnableWithException theFxTestCodeToRun) throws Throwable {
		FxTester.theCodeToRun = theFxTestCodeToRun;

		Application.launch();

		if (theCaughtException != null) {
			throw theCaughtException;
		}
	}

}