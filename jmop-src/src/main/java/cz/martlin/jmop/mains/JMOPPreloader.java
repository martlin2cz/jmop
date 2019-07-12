package cz.martlin.jmop.mains;

import java.io.InputStream;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The splash screen displaing JavaFX preloader.
 * 
 * @author martin
 *
 */
public class JMOPPreloader extends Preloader {

	private static final String SPLASH_IMAGE_PATH = "/cz/martlin/jmop/gui/img/splash.png";
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

		ImageView imageViewer = createSplashImageView();
		root.getChildren().add(imageViewer);

		Scene scene = new Scene(root, //
				imageViewer.getImage().getWidth(), //
				imageViewer.getImage().getHeight());

		return scene;
	}

	/**
	 * Creates the splash image view.
	 * 
	 * @return
	 */
	private ImageView createSplashImageView() {
		InputStream source = getClass().getResourceAsStream(SPLASH_IMAGE_PATH);
		Image image = new Image(source);
		ImageView imageViewer = new ImageView(image);
		return imageViewer;
	}

	@Override
	public void handleApplicationNotification(PreloaderNotification notification) {
		splashScreen.hide();
	}

}
