package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.wrappers.CoreGuiDescriptor;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.control.RequiresJMOP;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class WelcomePane extends VBox implements Initializable, RequiresJMOP {

	@FXML
	private DownloadPane dwnldPane;

	private CoreGuiDescriptor descriptor;

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

	@Override
	public void setupJMOP(JMOPPlayer jmop, CoreGuiDescriptor descriptor, GuiComplexActionsPerformer actions) {
		this.descriptor = descriptor;

		initBindings();
	}

	private void initBindings() {
		Bindings.bindContent(dwnldPane.tasksProperty(), descriptor.currentDownloadTasksProperty());

		this.visibleProperty().addListener((observable, oldVal, newVal) -> onVisibilityChanged(newVal));

	}

	private void onVisibilityChanged(boolean newVisible) {
		if (!newVisible) {
			Bindings.unbindContent(dwnldPane.tasksProperty(), descriptor.currentDownloadTasksProperty());
		}
	}
}
