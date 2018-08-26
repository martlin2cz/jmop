package cz.martlin.jmop.mains;

import java.io.IOException;

import cz.martlin.jmop.gui.control.MainFrameController;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JMOPGUIApp extends Application {
	public static void main(String[] args) {
		beforeFX();
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/main.fxml"));
		Parent root = loader.load();

		primaryStage.setTitle("JMOP");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/logo.png")));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();

		MainFrameController controller = loader.getController();
		controller.getActions().specifyScene(scene);

		primaryStage.show();
	}

	private static void beforeFX() {
		SvgImageLoaderFactory.install();
	}
}
