package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.gui.dial.NewBundleDialog.NewBundleData;
import cz.martlin.jmop.gui.local.Msg;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Dialog prompting for the new bundle.
 * 
 * @author martin
 *
 */
public class NewBundleDialog extends BaseCommonFXMLDialog<NewBundleData> {

	@FXML
	private TextField txtBundleName;
	@FXML
	private ComboBox<SourceKind> cmbSource;
	@FXML
	private TextField txtQuery;

	public NewBundleDialog() throws IOException {
		super();
		load("/cz/martlin/jmop/gui/fx/NewBundleDialog.fxml"); //$NON-NLS-1$
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle(Msg.get("Create_new_bundle")); //$NON-NLS-1$
		setHeaderText(Msg.get("Create_new_bundle_of_music_for_instance_")); //$NON-NLS-1$

		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/new-bundle.png")))); //$NON-NLS-1$
	}

	@Override
	protected void initializeComponents() {
		ObservableList<SourceKind> sources = FXCollections.observableArrayList(SourceKind.values());
		cmbSource.setItems(sources);
		cmbSource.setValue(sources.get(0));

		cmbSource.setButtonCell(new SourceKindListCell());
		cmbSource.setCellFactory((listView) -> new SourceKindListCell());
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected boolean validate() {
		if (txtBundleName.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_bundle_name")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		if (cmbSource.getValue() == null) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_source")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		if (txtQuery.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), Msg.get("Specify_query")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}

		return true;
	}

	@Override
	protected NewBundleData obtainResultFromInputs() {
		String name = txtBundleName.getText();
		SourceKind source = cmbSource.getValue();
		String query = txtQuery.getText();

		return new NewBundleData(name, source, query);
	}

	///////////////////////////////////////////////////////////////////////////

	public static class NewBundleData {
		private final String name;
		private final SourceKind kind;
		private final String query;

		public NewBundleData(String name, SourceKind kind, String query) {
			super();
			this.name = name;
			this.kind = kind;
			this.query = query;
		}

		public String getName() {
			return name;
		}

		public SourceKind getKind() {
			return kind;
		}

		public String getQuery() {
			return query;
		}
	}

	public class SourceKindListCell extends ListCell<SourceKind> {
		@Override
		protected void updateItem(SourceKind item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				String text = item.getName();
				this.setText(text);
			}
		}
	}
}
