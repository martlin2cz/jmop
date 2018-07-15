package cz.martlin.jmop.gui.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainFrameController implements Initializable {
	@FXML
	private Button butt;
	
	
	public MainFrameController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	
	public void buttOnClick() {
		System.out.println("Play!");
	}
}
