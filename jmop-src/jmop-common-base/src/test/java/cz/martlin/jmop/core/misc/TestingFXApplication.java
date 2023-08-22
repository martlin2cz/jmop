package cz.martlin.jmop.core.misc;

/**
 * Do not start directly, use {@link ReTestingFxAppMain} instead.
 * 
 * @author martin
 *
 */
public class TestingFXApplication /*extends Application*/ {

	public static void main(String[] args) {
		/*Application.launch(args);*/
	}
/*
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Testing app");

		FlowPane root = new FlowPane();
		initializeControls(root);

		primaryStage.setScene(new Scene(root, 800, 300));
		primaryStage.show();
	}

	private void initializeControls(FlowPane root) {
		createRunOperationButt(root);
		createRunMoreOperationsButt(root);
		createRunOperationsChainButt(root);
		createTestConsoleInteractorButt(root);
	}

	///////////////////////////////////////////////////////////////////////////

	private void createRunOperationButt(FlowPane root) {
		addTestingActionButton(root, "Run Operation", (v) -> runOperation());

	}

	private void runOperation() {
		OperationsManager manager = new OperationsManager();
		BaseOperation<Integer, Integer> operation = new TestingCountingLongOperation("xyz", 5);

		manager.start(operation, (r) -> System.out.println("The operation completed with: " + r));
	}

	///////////////////////////////////////////////////////////////////////////

	private void createRunMoreOperationsButt(FlowPane root) {
		addTestingActionButton(root, "Run more operations", (v) -> runMoreOperations());

	}

	private void runMoreOperations() throws InterruptedException {
		OperationsManager manager = new OperationsManager();

		Thread t = new Thread(() -> {
			try {
				TestingOperations.runMore(manager);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "StartMoreOperationsThread");

		t.start();
	}

	///////////////////////////////////////////////////////////////////////////
	private void createRunOperationsChainButt(FlowPane root) {
		addTestingActionButton(root, "Run Operations chain", (e) -> runOperationsChain());
	}

	private void runOperationsChain() {
		OperationsManager manager = new OperationsManager();
		BaseOperationsChain<Integer> chain = new TestingCountingOperationsChain("xyzw", 5);

		manager.start(0, chain, (r) -> System.out.println("The operations chain completed with " + r));
	}
///////////////////////////////////////////////////////////////////////////

	private void createTestConsoleInteractorButt(FlowPane root) {
		ConsoleUIInteractor interactor = new ConsoleUIInteractor();
		addTestingActionButton(root, "Interact via console", (e) -> runInteractions(interactor));
	}

	private void runInteractions(BaseUIInterractor interactor) {
		boolean ready = interactor.confirm("Are you ready?");
		String text = interactor.prompt("Gimme some text, please");
		File file = interactor.promptFile("Locate file:", "*");

		interactor.displayError("Failure: you have filled " + ready + ", " + text + ", " + file);
		
		System.out.println("Filled " + ready + ", " + text + ", " + file);
	}

///////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////
	private static void addTestingActionButton(FlowPane root, String name, ConsumerWithException<Void> action) {
		Button butt = new Button(name);
		butt.setOnAction((ev) -> {
			try {
				action.consume(null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		root.getChildren().add(butt);
	}
	*/
}
