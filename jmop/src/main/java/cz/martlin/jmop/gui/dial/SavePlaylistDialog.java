package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.gui.dial.SavePlaylistDialog.SavePlaylistData;
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
		
		load("/cz/martlin/jmop/gui/fx/SavePlaylistDialog.fxml");
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle("Save playlist");
		setHeaderText("Specify the new name for this playlist.");
		
		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/save-playlist.png"))));
	}

	@Override
	protected void initializeComponents() {
		txtPlaylistName.setText(currentPlaylistName);
	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean validate() {
		if (txtPlaylistName.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify playlist name");
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
