package cz.martlin.jmop.gui.comp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class TwoStateButton extends Button {

	private final StringProperty firstStateText;
	private final StringProperty secondStateText;
	private final BooleanProperty firstStateProperty;
	private final ObjectProperty<EventHandler<ActionEvent>> onFirstStateAction;
	private final ObjectProperty<EventHandler<ActionEvent>> onSecondStateAction;

	public TwoStateButton() {
		super();
		this.firstStateText = new SimpleStringProperty();
		this.secondStateText = new SimpleStringProperty();
		this.firstStateProperty = new SimpleBooleanProperty();
		this.onFirstStateAction = new SimpleObjectProperty<>();
		this.onSecondStateAction = new SimpleObjectProperty<>();

		initializeBindings();
		initializeHandlers();
	}

	public StringProperty firstStateText() {
		return firstStateText;
	}

	public StringProperty secondStateText() {
		return secondStateText;
	}

	public BooleanProperty firstStateProperty() {
		return firstStateProperty;
	}

	public ObjectProperty<EventHandler<ActionEvent>> onFirstStateActionProperty() {
		return onFirstStateAction;
	}

	public ObjectProperty<EventHandler<ActionEvent>> onSecondStateActionProperty() {
		return onSecondStateAction;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public String getFirstStateText() {
		return firstStateText.get();
	}

	public void setFirstStateText(String text) {
		firstStateText.set(text);
	}

	public String getSecondStateText() {
		return secondStateText.get();
	}

	public void setSecondStateText(String text) {
		secondStateText.set(text);
	}

	public boolean getFirstState() {
		return firstStateProperty.get();
	}

	public void setFirstState(boolean state) {
		firstStateProperty.set(state);
	}

	public BooleanProperty getFirstStateProperty() {
		return firstStateProperty;
	}

	public EventHandler<ActionEvent> getOnFirstStateAction() {
		return onFirstStateAction.get();
	}

	public void setOnFirstStateAction(EventHandler<ActionEvent> onFirstStateAction) {
		this.onFirstStateAction.set(onFirstStateAction);
	}

	public EventHandler<ActionEvent> getOnSecondStateAction() {
		return onSecondStateAction.get();
	}

	public void setOnSecondStateAction(EventHandler<ActionEvent> onSecondStateAction) {
		this.onSecondStateAction.set(onSecondStateAction);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void initializeBindings() {
		firstStateText.addListener((observable, oldVal, newVal) -> updateText());
		secondStateText.addListener((observable, oldVal, newVal) -> updateText());

		firstStateProperty.addListener((observable, oldVal, newVal) -> updateText());
	}

	private void initializeHandlers() {
		setOnAction((e) -> handleAction(e));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void updateText() {

		StringProperty property = getCurrentStateProperty(firstStateText, secondStateText);
		String newText = property.get();
		setText(newText);
	}

	private void handleAction(ActionEvent event) {
		ObjectProperty<EventHandler<ActionEvent>> property = //
				getCurrentStateProperty(onFirstStateAction, onSecondStateAction);
		EventHandler<ActionEvent> handler = property.get();

		if (handler != null) {
			handler.handle(event);
		}
	}

	private <T extends Property<?>> T getCurrentStateProperty(T firstProperty, T secondProperty) {
		if (firstStateProperty.get()) {
			return firstProperty;
		} else {
			return secondProperty;
		}
	}

}
