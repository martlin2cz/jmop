package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.wrappers.CoreGuiDescriptor;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.control.RequiresJMOP;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class JMOPMainMenu extends MenuBar implements Initializable, RequiresJMOP {
	// @FXML
	// JMOPMainMenu menu;

	@FXML
	private MenuItem miPlay;
	@FXML
	private MenuItem miStop;
	@FXML
	private MenuItem miPause;
	@FXML
	private MenuItem miResume;
	@FXML
	private MenuItem miPrevious;
	@FXML
	private MenuItem miNext;

	@FXML
	private HalfDynamicMenu menuBundle;
	@FXML
	private MenuItem miStartNewBundle;

	@FXML
	private HalfDynamicMenu menuPlaylist;
	@FXML
	private MenuItem miStartNewPlaylist;
	@FXML
	private MenuItem miSaveThisPlaylistAs;

	@FXML
	private HalfDynamicMenu menuTrack;
	@FXML
	private MenuItem miAppendCustomTrack;

	private CoreGuiDescriptor descriptor;
	private GuiComplexActionsPerformer actions;

	public JMOPMainMenu() throws IOException {
		super();

		load();
	}

	private void load() throws IOException {
		loadFXML();
	}

	private void loadFXML() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/MainMenu.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// we need to wait for the JMOP
	}

	@Override
	public void setupJMOP(JMOPPlayer jmop, CoreGuiDescriptor descriptor, GuiComplexActionsPerformer actions) {
		this.descriptor = descriptor;
		this.actions = actions;

		initializeBindings();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	private void initializeBindings() {
		final BooleanBinding hasNoPlaylist = descriptor.hasActiveBundleAndPlaylistProperty().not();

		miStop.disableProperty().bind(descriptor.stoppedProperty().or(hasNoPlaylist));
		miPlay.disableProperty().bind(descriptor.stoppedProperty().not().or(hasNoPlaylist));

		miPause.disableProperty().bind( //
				descriptor.pausedProperty() //
						.or(descriptor.stoppedProperty()) //
						.or(hasNoPlaylist));
		miResume.disableProperty().bind( //
				descriptor.pausedProperty().not() //
						.or(descriptor.stoppedProperty()) //
						.or(hasNoPlaylist));

		miPrevious.disableProperty().bind(hasNoPlaylist.or(descriptor.hasPreviousProperty().not()));
		miNext.disableProperty().bind(hasNoPlaylist.or(descriptor.hasNextProperty().not()));

		menuPlaylist.disableProperty().bind(hasNoPlaylist);
		menuTrack.disableProperty().bind(hasNoPlaylist);
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public void onBundleMenuShowing() {
		List<String> bundleNames = actions.listBundles();
		addDynamicItems(menuBundle, bundleNames, (bn) -> new MenuItem(bn));
	}

	public void onPlaylistMenuShowing() {
		List<String> playlistNames = actions.listPlaylists();
		addDynamicItems(menuPlaylist, playlistNames, (pn) -> new MenuItem(pn));
	}

	public void onTrackMenuShowing() {
		List<Track> tracks = actions.listTracks();
		addDynamicItems(menuTrack, tracks, (t) -> trackMenuItem(t));

	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public void onBundleNameAction(ActionEvent event) {
		MenuItem bundleMenuItem = (MenuItem) event.getSource();
		String bundleName = bundleMenuItem.getText();
		actions.startBundle(bundleName);
	}

	public void onPlaylistNameAction(ActionEvent event) {
		MenuItem playlistMenuItem = (MenuItem) event.getSource();
		String playlistName = playlistMenuItem.getText();
		actions.startPlaylist(playlistName);
	}

	public void onTrackTitleAction() {
		// System.out.println("play track");
		// TODO play selected track
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public void onPlayAction() {
		actions.playButtAction();
	}

	public void onStopAction() {
		actions.stopButtAction();
	}

	public void onPauseAction() {
		actions.pauseButtAction();
	}

	public void onResumeAction() {
		actions.resumeButtAction();
	}

	public void onPreviousAction() {
		actions.prevButtAction();
	}

	public void onNextAction() {
		actions.nextButtAction();
	}

	public void onExitAction() {
		actions.exit();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public void onStartNewBundleAction() {
		actions.startNewBundle();
	}

	public void onStartNewPlaylistAction() {
		actions.newPlaylist();
	}

	public void onSavePlaylistAsAction() {
		actions.savePlaylist();
	}

	public void onAppendCustomTrackAction() {
		actions.addTrack();
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	private MenuItem trackMenuItem(Track track) {
		String title = track.getTitle();
		MenuItem mi = new MenuItem(title);

		mi.setDisable(true); //TODO make it clickable

		return mi;
	}

	private static <E> void addDynamicItems(HalfDynamicMenu menu, List<E> items, Function<E, MenuItem> mapper) {

		List<MenuItem> menuItems = items.stream().map(mapper).collect(Collectors.toList());
		menu.setDynamicItems(menuItems);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public void onHelpAction() {
		actions.openHelp();
	}

	public void onCheckConfigurationAction() {
		actions.checkConfiguration();
	}

	public void onAboutAction() {
		actions.showAboutBox();
	}

}
