package cz.martlin.jmop.gui.comp;

import java.util.List;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

@DefaultProperty("staticItems")
public class HalfDynamicMenu extends Menu {

	private final ObservableList<MenuItem> staticItems;
	private final ObservableList<MenuItem> dynamicItems;

	private final ObjectProperty<EventHandler<ActionEvent>> onDynamicItemAction;

	public HalfDynamicMenu() {
		staticItems = FXCollections.observableArrayList();
		dynamicItems = FXCollections.observableArrayList();
		onDynamicItemAction = new SimpleObjectProperty<>();

		initializeHandlers();

	}

	public ObservableList<MenuItem> getStaticItems() {
		return staticItems;
	}

	public ObservableList<MenuItem> getDynamicItems() {
		return dynamicItems;
	}

	public void setDynamicItems(List<MenuItem> items) {
		dynamicItems.clear();
		dynamicItems.addAll(items);
	}

	public ObjectProperty<EventHandler<ActionEvent>> dynamicItemActionProperty() {
		return onDynamicItemAction;
	}

	public EventHandler<ActionEvent> getOnDynamicItemAction() {
		return onDynamicItemAction.get();
	}

	public void setOnDynamicItemAction(EventHandler<ActionEvent> onDynamicItemAction) {
		this.onDynamicItemAction.set(onDynamicItemAction);
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	private void initializeHandlers() {
		staticItems.addListener((ListChangeListener<? super MenuItem>) (ch) -> staticItemsChanged(ch));
		dynamicItems.addListener((ListChangeListener<? super MenuItem>) (ch) -> dynamicItemsChanged(ch));

	}

	///////////////////////////////////////////////////////////////////////////////////////////

	private void staticItemsChanged(Change<? extends MenuItem> change) {
		itemsChanged(change);
	}

	private void dynamicItemsChanged(Change<? extends MenuItem> change) {
		itemsChanged(change);
		updateChangedHandlers(change);
	}

	private void updateChangedHandlers(Change<? extends MenuItem> change) {
		EventHandler<ActionEvent> handler = getOnDynamicItemAction();

		while (change.next()) {

			change.getAddedSubList().forEach((mi) -> mi.setOnAction(handler));
			change.getRemoved().forEach((mi) -> mi.setOnAction(null));
		}
	}

	private void itemsChanged(Change<? extends MenuItem> change) {
		List<MenuItem> items = getItems();

		items.clear();
		items.addAll(staticItems);

		if (!dynamicItems.isEmpty()) {
			items.addAll(dynamicItems);
		} else {
			items.add(createNoItemsItem());
		}

		// FIXME: such unnefective solution above
		// while (change.next()) {
		//
		// change.getAddedSubList().forEach((mi) -> items.add(mi));
		// change.getRemoved().forEach((mi) -> items.remove(mi));
		// }
	}

	private static MenuItem createNoItemsItem() {
		MenuItem mi = new MenuItem("No items");
		mi.setDisable(true);
		return mi;
	}

}
