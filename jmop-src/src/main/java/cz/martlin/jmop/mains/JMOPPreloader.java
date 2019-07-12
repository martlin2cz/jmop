package cz.martlin.jmop.mains;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JMOPPreloader extends Preloader {

	private Stage splashScreen;

	@Override
	public void start(Stage stage) throws Exception {
		splashScreen = stage;
		splashScreen.setScene(createScene());
		stage.initStyle(StageStyle.UNDECORATED);

		splashScreen.show();
	}

	public Scene createScene() {
		StackPane root = new StackPane();
		root.getChildren().add(new Label("Loading"));

		Scene scene = new Scene(root, 300, 200);

		return scene;
	}

	@Override
	public void handleApplicationNotification(PreloaderNotification notification) {
		System.out.println(notification);
		splashScreen.hide();
	}

}
