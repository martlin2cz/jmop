package cz.martlin.jmop.gui.comp;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class TwoStateButton extends Button {

	private final StringProperty firstStateText;
	private final StringProperty secondStateText;
	private final ObjectProperty<Node> firstStateGraphics;
	private final ObjectProperty<Node> secondStateGraphics;
	private final ObjectProperty<Tooltip> firstStateTooltip;
	private final ObjectProperty<Tooltip> secondStateTooltip;

	private final BooleanProperty firstStateProperty;
	private final ObjectProperty<EventHandler<ActionEvent>> onFirstStateAction;
	private final ObjectProperty<EventHandler<ActionEvent>> onSecondStateAction;

	public TwoStateButton() {
		super();
		this.firstStateText = new SimpleStringProperty();
		this.secondStateText = new SimpleStringProperty();
		this.firstStateGraphics = new SimpleObjectProperty<>();
		this.secondStateGraphics = new SimpleObjectProperty<>();
		this.firstStateTooltip = new SimpleObjectProperty<>();
		this.secondStateTooltip = new SimpleObjectProperty<>();
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

	public ObjectProperty<Node> firstStateGraphics() {
		return firstStateGraphics;
	}

	public ObjectProperty<Node> secondStateGraphics() {
		return secondStateGraphics;
	}

	public ObjectProperty<Tooltip> firstStateTooltip() {
		return firstStateTooltip;
	}

	public ObjectProperty<Tooltip> secondStateTooltip() {
		return secondStateTooltip;
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

	public Node getFirstStateGraphics() {
		return firstStateGraphics.get();
	}

	public void setFirstStateGraphics(Node graphics) {
		firstStateGraphics.set(graphics);
	}

	public Node getSecondStateGraphics() {
		return firstStateGraphics.get();
	}

	public void setSecondStateGraphics(Node graphics) {
		secondStateGraphics.set(graphics);
	}

	public Tooltip getFirstStateTooltip() {
		return firstStateTooltip.get();
	}

	public void setFirstStateTooltip(Tooltip tooltip) {
		firstStateTooltip.set(tooltip);
	}

	public Tooltip getSecondStateTooltip() {
		return secondStateTooltip.get();
	}

	public void setSecondStateTooltip(Tooltip tooltip) {
		secondStateTooltip.set(tooltip);
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
		firstStateGraphics.addListener((observable, oldVal, newVal) -> updateGraphics());
		secondStateGraphics.addListener((observable, oldVal, newVal) -> updateGraphics());
		firstStateTooltip.addListener((observable, oldVal, newVal) -> updateTooltip());
		secondStateTooltip.addListener((observable, oldVal, newVal) -> updateTooltip());

		firstStateProperty.addListener((observable, oldVal, newVal) -> update());
	}

	private void initializeHandlers() {
		setOnAction((e) -> handleAction(e));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void update() {
		Platform.runLater(() -> {
			updateText();
			updateGraphics();
			updateTooltip();
		});
	}

	private void updateText() {
		Platform.runLater(() -> {
			StringProperty property = getCurrentStateProperty(firstStateText, secondStateText);
			String newText = property.get();
			setText(newText);
		});
	}

	private void updateGraphics() {
		Platform.runLater(() -> {
			ObjectProperty<Node> property = getCurrentStateProperty(firstStateGraphics, secondStateGraphics);
			Node newGraphics = property.get();
			setGraphic(newGraphics);
		});
	}

	private void updateTooltip() {
		Platform.runLater(() -> {
			ObjectProperty<Tooltip> property = getCurrentStateProperty(firstStateTooltip, secondStateTooltip);
			Tooltip newTooltip = property.get();
			setTooltip(newTooltip);
		});
	}

	private void handleAction(ActionEvent event) {
		ObjectProperty<EventHandler<ActionEvent>> property = //
				getCurrentStateProperty(onFirstStateAction, onSecondStateAction);
		EventHandler<ActionEvent> handler = property.get();

		if (handler != null) {
			handler.handle(event);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private <T extends Property<?>> T getCurrentStateProperty(T firstProperty, T secondProperty) {
		if (firstStateProperty.get()) {
			return firstProperty;
		} else {
			return secondProperty;
		}
	}

}
