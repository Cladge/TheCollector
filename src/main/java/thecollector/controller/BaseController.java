package thecollector.controller;

import javafx.fxml.FXML;

/**
 * A base controller for a main application layout.
 * 
 * @author Ian Claridge
 */
public interface BaseController {
	
	/**
	 * Main Initialise method.
	 */
	public void setup();
	
	/**
	 * Opens an about dialog.
	 */
	@FXML public void handleAbout();

	/**
	 * Closes the application.
	 */
	@FXML public void handleExit();

}
