package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.gui.dial.NewPlaylistDialog.NewPlaylistData;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Dialog prompting for the new playlist (starting with by-user-specified
 * track).
 * 
 * @author martin
 *
 */
public class NewPlaylistDialog extends BaseCommonFXMLDialog<NewPlaylistData> {

	@FXML
	private TextField txtPlaylistName;
	@FXML
	private TextField txtQuery;

	public NewPlaylistDialog() throws IOException {
		super();
		load("/cz/martlin/jmop/gui/fx/NewPlaylistDialog.fxml");
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle("Create new playlist");
		setHeaderText("Create new playlist, for instance for album \n" //
				+ "or just you favourite set.");

		setGraphic(
				new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/new-playlist.png"))));
	}

	@Override
	protected void initializeComponents() {
		// nothing needed here
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected boolean validate() {
		if (txtPlaylistName.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify playlist name");
			return false;
		}
		if (txtQuery.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify query");
			return false;
		}

		return true;
	}

	@Override
	protected NewPlaylistData obtainResultFromInputs() {
		String name = txtPlaylistName.getText();
		String query = txtQuery.getText();

		return new NewPlaylistData(name, query);
	}

	///////////////////////////////////////////////////////////////////////////

	public static class NewPlaylistData {
		private final String name;
		private final String query;

		public NewPlaylistData(String name, String query) {
			super();
			this.name = name;
			this.query = query;
		}

		public String getName() {
			return name;
		}

		public String getQuery() {
			return query;
		}
	}

}
