package cz.martlin.jmop.gui.dial;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.gui.local.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public abstract class BaseCommonFXMLDialog<R> extends Dialog<R> implements Initializable {

	private static final ButtonType OK_BUTTON = ButtonType.OK;
	private static final ButtonType CANCEL_BUTTON = ButtonType.CANCEL;

	public BaseCommonFXMLDialog() throws IOException {
		super();

		initializeDialog();
	}

	protected void load(String fxmlFilePath) throws IOException {
		initializeContent(fxmlFilePath);
	}

	///////////////////////////////////////////////////////////////////////////

	protected void initializeDialog() throws IOException {
		getDialogPane().getButtonTypes().add(OK_BUTTON);
		getDialogPane().getButtonTypes().add(CANCEL_BUTTON);

		setResultConverter((b) -> toResult(b));

		specifyCustomDialogSettings();
	}

	protected abstract void specifyCustomDialogSettings();

	///////////////////////////////////////////////////////////////////////////

	protected void initializeContent(String fxmlFilePath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
		
		loader.setResources(Msg.getResourceBundle());
		loader.setController(this);

		Parent root = loader.load();
		getDialogPane().setContent(root);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeDialogComponents();
		initializeComponents();
	}

	private void initializeDialogComponents() {
		// https://stackoverflow.com/a/32287080/3797793
		final Button okButton = (Button) getDialogPane().lookupButton(OK_BUTTON);
		okButton.addEventFilter(ActionEvent.ACTION, (event) -> {
			if (!validate()) {
				event.consume();
			}
		});
	}

	protected abstract void initializeComponents();

	///////////////////////////////////////////////////////////////////////////

	private R toResult(ButtonType button) {
		if (OK_BUTTON.equals(button)) {
			return checkAndObtainResultFromInputs();
		} else {
			return null;
		}
	}

	private R checkAndObtainResultFromInputs() {
		return obtainResultFromInputs();
	}

	protected abstract boolean validate();

	protected abstract R obtainResultFromInputs();

	///////////////////////////////////////////////////////////////////////////

}