package cz.martlin.jmop.gui.dial;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.gui.dial.CreatePlaylistDialog.CreatePlaylistData;
import cz.martlin.jmop.gui.local.Msg;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Dialog prompting for (new) playlist name (and also allowing to change the
 * bundle).
 * 
 * @author martin
 *
 */
public class CreatePlaylistDialog extends BaseCommonFXMLDialog<CreatePlaylistData> {
	@FXML
	private ComboBox<String> cmbBundleName;
	@FXML
	private TextField txtPlaylistName;

	private final List<String> bundles;

	public CreatePlaylistDialog(List<String> bundles) throws IOException {
		super();

		this.bundles = bundles;

		load("/cz/martlin/jmop/gui/fx/CreatePlaylistDialog.fxml"); //$NON-NLS-1$
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle(Msg.get("Create_new_playlist")); //$NON-NLS-1$
		setHeaderText(Msg.get("Specify_name_of_the_new_playlist_and_also_")); //$NON-NLS-1$ //$NON-NLS-2$

		setGraphic(
				new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/new-playlist.png")))); //$NON-NLS-1$
	}

	@Override
	protected void initializeComponents() {
		ObservableList<String> bundleNames = FXCollections.observableArrayList(bundles);
		cmbBundleName.setItems(bundleNames);
	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean validate() {
		if (txtPlaylistName.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_playlist_name")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		if (cmbBundleName.getValue() == null) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_bundle")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}

		return true;
	}

	@Override
	protected CreatePlaylistData obtainResultFromInputs() {
		String bundleName = cmbBundleName.getValue();
		String playlistName = txtPlaylistName.getText();

		return new CreatePlaylistData(bundleName, playlistName);
	}

	///////////////////////////////////////////////////////////////////////////

	public static class CreatePlaylistData {
		private final String bundleName;
		private final String playlistName;

		public CreatePlaylistData(String bundleName, String playlistName) {
			super();
			this.bundleName = bundleName;
			this.playlistName = playlistName;
		}

		public String getBundleName() {
			return bundleName;
		}

		public String getPlaylistName() {
			return playlistName;
		}
	}
}
