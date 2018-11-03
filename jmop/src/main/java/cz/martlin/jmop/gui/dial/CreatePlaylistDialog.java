package cz.martlin.jmop.gui.dial;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.gui.dial.CreatePlaylistDialog.CreatePlaylistData;
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

		load("/cz/martlin/jmop/gui/fx/CreatePlaylistDialog.fxml");
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle("Create new playlist");
		setHeaderText("Specify name of the new playlist and also \n" + "change the bundle if needed.");

		setGraphic(
				new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/new-playlist.png"))));
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
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify playlist name");
			return false;
		}
		if (cmbBundleName.getValue() == null) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify bundle");
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
