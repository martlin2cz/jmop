package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.gui.dial.AddTrackDialog.AddTrackData;
import cz.martlin.jmop.gui.local.Msg;
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

		load("/cz/martlin/jmop/gui/fx/AddTrackDialog.fxml"); //$NON-NLS-1$
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle(Msg.get("Add_track")); //$NON-NLS-1$
		setHeaderText(Msg.get("Specify_the_new_track_to_be_added")); //$NON-NLS-1$

		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/add-track.png")))); //$NON-NLS-1$
	}

	@Override
	protected void initializeComponents() {
		// nothing needed here
	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean validate() {
		if (txtQuery.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_query")); //$NON-NLS-1$ //$NON-NLS-2$
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
