package cz.martlin.jmop.mains;

import java.io.IOException;

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
    	Parent root = FXMLLoader.load(getClass().getResource("/cz/martlin/jmop/gui/fx/main.fxml"));
    	
        primaryStage.setTitle("JMOP");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/logo.png")));
       
       
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();
    }
	
	  
    private static void beforeFX() {
    	SvgImageLoaderFactory.install();
	}
}
