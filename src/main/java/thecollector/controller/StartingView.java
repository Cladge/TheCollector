package thecollector.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.AnchorPane;
import thecollector.model.TheCollector;

/**
 * The controller for the main application layout.
 * 
 * @author Ian Claridge
 */
public class StartingView extends BaseView {
	
	private static TheCollector theCollector;

	@FXML
	private AnchorPane anchorpaneStartingView;
	
	@FXML
	private ComboBox<String> comboboxClient;
	
	/**
	 * Return reference to the main AnchorPane.
	 * 
	 * @return Entity AnchorPane.
	 */
	public AnchorPane getEntityPane () {
		return this.anchorpaneStartingView;
	}
	
	/**
	 * Main Initialise method.
	 * <li>Get cast handle of main app.
	 * <li>Populate the Client Combo Box.
	 */
	public void initialise() {
		// TODO Read required values from INI file.
		theCollector = (TheCollector) mainApp;
		comboboxClient.setItems(FXCollections.observableArrayList("Naples", "Seville", "Trident"));
	}
	
	/**
	 * Opens an about dialog.
	 */
	public void handleAbout() {
		Dialogs.showInformationDialog(theCollector.getPrimaryStage(), "Author:\nIan Claridge\n\nWebsite:\nhttp://www.cladge.com", "TheCollector v0.1 (beta)", "About");
	}

}
