package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.gui.dial.AddTrackDialog.AddTrackData;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Dialog prompting for the new track.
 * 
 * @author martin
 *
 */
public class AddTrackDialog extends BaseCommonFXMLDialog<AddTrackData> {
	@FXML
	private TextField txtQuery;

	public AddTrackDialog() throws IOException {
		super();

		load("/cz/martlin/jmop/gui/fx/AddTrackDialog.fxml");
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle("Add track");
		setHeaderText("Specify the new track to be added.");

		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/add-track.png"))));
	}

	@Override
	protected void initializeComponents() {
		// nothing needed here
	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean validate() {
		if (txtQuery.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify query");
			return false;
		}

		return true;
	}

	@Override
	protected AddTrackData obtainResultFromInputs() {
		String query = txtQuery.getText();

		return new AddTrackData(query);
	}

	///////////////////////////////////////////////////////////////////////////

	public static class AddTrackData {
		private final String query;

		public AddTrackData(String query) {
			super();
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}
}
