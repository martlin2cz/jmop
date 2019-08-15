package cz.martlin.jmop.core.misc;

import cz.martlin.jmop.core.misc.ops.BaseOperation;
import cz.martlin.jmop.core.misc.ops.BaseOperationsChain;
import cz.martlin.jmop.core.misc.ops.OperationsManager;
import cz.martlin.jmop.core.misc.ops.TestingCountingLongOperation;
import cz.martlin.jmop.core.misc.ops.TestingCountingOperationsChain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Do not start directly, use {@link ReTestingFxAppMain} instead.
 * 
 * @author martin
 *
 */
public class TestingFXApplication extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Testing app");

		FlowPane root = new FlowPane();
		initializeControls(root);

		primaryStage.setScene(new Scene(root, 200, 200));
		primaryStage.show();
	}

	private void initializeControls(FlowPane root) {
		createRunOperationButt(root);
		createRunOperationsChainButt(root);
	}

	///////////////////////////////////////////////////////////////////////////

	private void createRunOperationButt(FlowPane root) {
		Button butt = new Button("Run Operation");
		butt.setOnAction((e) -> runOperation());

		root.getChildren().add(butt);
	}

	private void runOperation() {
		OperationsManager manager = new OperationsManager();
		BaseOperation<Integer, Integer> operation = new TestingCountingLongOperation("xyz", 5);

		manager.start(operation, (r) -> System.out.println("The operation completed with: " + r));
	}

	///////////////////////////////////////////////////////////////////////////
	private void createRunOperationsChainButt(FlowPane root) {
		Button butt = new Button("Run Operations chain");
		butt.setOnAction((e) -> runOperationsChain());

		root.getChildren().add(butt);
	}

	private void runOperationsChain() {
		OperationsManager manager = new OperationsManager();
		BaseOperationsChain<Integer> chain = new TestingCountingOperationsChain("xyzw", 5);

		try {
			manager.start(0, chain, (r) -> System.out.println("The operations chain completed with " + r));
		} catch (JMOPSourceException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////

}
