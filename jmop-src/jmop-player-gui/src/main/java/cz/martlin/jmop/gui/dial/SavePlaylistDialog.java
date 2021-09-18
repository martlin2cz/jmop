package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.gui.dial.SavePlaylistDialog.SavePlaylistData;
import cz.martlin.jmop.gui.local.Msg;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Dialog prompting for the new playlist name.
 * 
 * @author martin
 *
 */
public class SavePlaylistDialog extends BaseCommonFXMLDialog<SavePlaylistData> {
	@FXML
	private TextField txtPlaylistName;

	private final String currentPlaylistName;

	public SavePlaylistDialog(String currentPlaylistName) throws IOException {
		super();

		this.currentPlaylistName = currentPlaylistName;

		load("/cz/martlin/jmop/gui/fx/SavePlaylistDialog.fxml"); //$NON-NLS-1$
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle(Msg.get("Save_playlist")); //$NON-NLS-1$
		setHeaderText(Msg.get("Specify_the_new_name_for_this_playlist")); //$NON-NLS-1$

		setGraphic(
				new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/save-playlist.png")))); //$NON-NLS-1$
	}

	@Override
	protected void initializeComponents() {
		txtPlaylistName.setText(currentPlaylistName);
	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean validate() {
		if (txtPlaylistName.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_playlist_name")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}

		return true;
	}

	@Override
	protected SavePlaylistData obtainResultFromInputs() {
		String name = txtPlaylistName.getText();

		return new SavePlaylistData(name);
	}

	///////////////////////////////////////////////////////////////////////////

	public static class SavePlaylistData {
		private final String name;

		public SavePlaylistData(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
