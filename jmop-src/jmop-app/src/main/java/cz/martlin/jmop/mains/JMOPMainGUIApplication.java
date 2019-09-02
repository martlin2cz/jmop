package cz.martlin.jmop.mains;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.control.MainFrameController;
import cz.martlin.jmop.gui.local.Msg;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
//XXX import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.application.Preloader.StateChangeNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JMOPMainGUIApplication extends Application {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final JMOPPlayer jmop;

	public JMOPMainGUIApplication() {
		this.jmop = JMOPGUIApp.getJmop();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			LOG.info("Starting GUI application ..."); //$NON-NLS-1$
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/main.fxml")); //$NON-NLS-1$

			loader.setResources(Msg.getResourceBundle());

			Parent root = loader.load();

			primaryStage.setTitle("JMOP"); //$NON-NLS-1$
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/logo.png"))); //$NON-NLS-1$

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
			LOG.error("Application GUI could not start!", e); //$NON-NLS-1$
		} finally {
			notifyPreloader(new StateChangeNotification(null));
		}
	}

	private void initializeController(MainFrameController controller, Scene scene) {
		GuiComplexActionsPerformer actions = new GuiComplexActionsPerformer(jmop);
		actions.specifyScene(scene);

		controller.setupJMOP(jmop, actions);
	}

}
