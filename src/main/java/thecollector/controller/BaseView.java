package thecollector.controller;

import thecollector.controller.BaseController;

/**
 * Abstract controller class.
 * 
 * @author Ian Claridge
 */
public class BaseView implements BaseController {

	protected Object mainApp;					// Reference to the main application

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Object mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Main Initialise method.
	 */
	public void initialise() {
	}
	
	/**
	 * Opens an about dialog.
	 */
	public void handleAbout() {
	}

	/**
	 * Closes the application.
	 */
	public void handleExit() {
		System.exit(0);
	}

}
