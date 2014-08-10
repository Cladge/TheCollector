package thecollector.controller;

import javafx.fxml.FXML;

/**
 * The controller for the main application layout.
 * 
 * @author Ian Claridge
 */
public interface BaseController {
	
	/**
	 * Main Initialise method.
	 */
	public void initialise();
	
	/*
	 * Opens an about dialog.
	 */
	@FXML public void handleAbout();

	/*
	 * Closes the application.
	 */
	@FXML public void handleExit();

}
