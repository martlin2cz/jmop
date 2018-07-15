package cz.martlin.jmop.mains;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JMOPGUIApp extends Application {
    public static void main(String[] args) {
    	Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/cz/martlin/jmop/gui/fx/main.fxml"));
    	
        primaryStage.setTitle("JMOP");
       
       
        primaryStage.setScene(new Scene(root, 480, 320));
        primaryStage.show();
    }
}
