package thecollector.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
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
	 */
	public void initialise() {
		theCollector = (TheCollector) mainApp;
		// rectangleBorder.widthProperty().bind(vboxBorder.widthProperty());
	}
	
	/**
	 * Opens an about dialog.
	 */
	public void handleAbout() {
		System.out.println("\nGUI not implemented yet!");
		System.out.println("\nAuthor:\nIan Claridge\n\nWebsite:\nhttp://www.cladge.com");
		System.out.println("\nTheCollector v0.1 (beta)");
	}

}
