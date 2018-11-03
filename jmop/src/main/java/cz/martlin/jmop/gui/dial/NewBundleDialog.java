package cz.martlin.jmop.gui.dial;

import java.io.IOException;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.gui.dial.NewBundleDialog.NewBundleData;
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
		load("/cz/martlin/jmop/gui/fx/NewBundleDialog.fxml");
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle("Create new bundle");
		setHeaderText("Create new bundle of music, for instance\n" //
				+ "music of one author, album, genre ...");

		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/new-bundle.png"))));
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
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify bundle name");
			return false;
		}
		if (cmbSource.getValue() == null) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify source");
			return false;
		}
		if (txtQuery.getText().isEmpty()) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Specify query");
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
