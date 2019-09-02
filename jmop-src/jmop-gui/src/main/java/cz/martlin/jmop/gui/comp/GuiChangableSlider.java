package cz.martlin.jmop.gui.comp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GuiChangableSlider extends Slider {

	private final BooleanProperty guiChanging;

	public GuiChangableSlider() {
		super();

		this.guiChanging = new SimpleBooleanProperty();

		initializeHandlers();
	}

	public ReadOnlyBooleanProperty guiChangingProperty() {
		return guiChanging;
	}

	public boolean isGuiChanging() {
		return guiChanging.get();
	}

	/////////////////////////////////////////////////////////////////

	private void initializeHandlers() {
		this.setOnMousePressed((e) -> onMousePressed(e));
		this.setOnMouseReleased((e) -> onMouseReleased(e));

		this.setOnKeyPressed((e) -> onKeyPressed(e));
		this.setOnKeyReleased((e) -> onKeyReleased(e));

		// TODO anything more here?

	}
	/////////////////////////////////////////////////////////////////

	private void onKeyReleased(KeyEvent e) {
		changeGuiChangingStatus(false);
	}

	private void onKeyPressed(KeyEvent e) {
		// TODO filter only arrows, etc.
		changeGuiChangingStatus(true);
	}

	private void onMouseReleased(MouseEvent e) {
		changeGuiChangingStatus(false);
	}

	private void onMousePressed(MouseEvent e) {
		changeGuiChangingStatus(true);
	}

	/////////////////////////////////////////////////////////////////

	private void changeGuiChangingStatus(boolean isChanging) {
		guiChanging.set(isChanging);
	}

}
