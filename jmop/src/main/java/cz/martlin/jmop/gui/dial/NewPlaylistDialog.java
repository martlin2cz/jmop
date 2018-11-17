package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.gui.dial.NewPlaylistDialog.NewPlaylistData;
import cz.martlin.jmop.gui.local.Msg;
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
		load("/cz/martlin/jmop/gui/fx/NewPlaylistDialog.fxml"); //$NON-NLS-1$
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle(Msg.get("Create_new_playlist")); //$NON-NLS-1$
		setHeaderText(Msg.get("Create_new_playlist_for_instance_")); //$NON-NLS-1$

		setGraphic(
				new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/new-playlist.png")))); //$NON-NLS-1$
	}

	@Override
	protected void initializeComponents() {
		// nothing needed here
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected boolean validate() {
		if (txtPlaylistName.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_playlist_name")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		if (txtQuery.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_query")); //$NON-NLS-1$ //$NON-NLS-2$
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
