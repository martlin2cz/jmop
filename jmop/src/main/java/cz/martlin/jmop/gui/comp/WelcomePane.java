package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class WelcomePane extends VBox implements Initializable {


	public WelcomePane() throws IOException {
		initialize();
	}

	private void initialize() throws IOException {
		loadFXML();
	}

	private void loadFXML() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/WelcomePane.fxml"));
		loader.setController(this);

		Parent root = loader.load();
		getChildren().addAll(root);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// nothing needed here
	}

	///////////////////////////////////////////////////////////////////////////////



}
