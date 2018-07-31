package cz.martlin.jmop.gui.comp;

import java.io.IOException;

import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;

public class JMOPMainMenu extends MenuBar {

	private final ObjectProperty<JMOPPlayer> jmopProperty;

	public JMOPMainMenu() {
		super();

		this.jmopProperty = new SimpleObjectProperty<>();

		loadFXML();
	}

	public JMOPPlayer getJmop() {
		return jmopProperty.get();
	}

	public ObjectProperty<JMOPPlayer> jmopProperty() {
		return jmopProperty;
	}

	///////////////////////////////////////////////////////////////////////////

	private void loadFXML() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/MainMenu.fxml"));

		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.load();
		} catch (IOException e) {
			// TODO report error
			e.printStackTrace();
		}

	}
}
