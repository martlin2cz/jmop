package cz.martlin.jmop.mains;

import java.io.IOException;

import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.control.MainFrameController;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JMOPMainGUIApplication extends Application {

	static {
		beforeFX();
	}

	private final JMOPPlayer jmop;

	public JMOPMainGUIApplication() {
		this.jmop = JMOPGUIApp.getJmop();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/main.fxml"));
		Parent root = loader.load();

		primaryStage.setTitle("JMOP");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/logo.png")));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(true);
		primaryStage.setMinHeight(scene.getHeight());
		primaryStage.setMinWidth(scene.getWidth());

		MainFrameController controller = loader.getController();
		initializeController(controller, scene);

		primaryStage.show();
		} catch (Exception e) {
			System.err.println("Application GUI could not start: " + e.toString());
			//TODO LOG
		}
	}

	private void initializeController(MainFrameController controller, Scene scene) {
		GuiComplexActionsPerformer actions = new GuiComplexActionsPerformer(jmop);
		actions.specifyScene(scene);

		controller.setupJMOP(jmop, actions);
	}

	private static void beforeFX() {
		SvgImageLoaderFactory.install();
	}

}
