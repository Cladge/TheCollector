package thecollector.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thecollector.controller.MainViewController;
import thecollector.utils.FileUtil;
import thecollector.utils.LoggerInterface;

/**
 * The main application.
 * 
 * @author Ian Claridge
 */
public class TheCollector extends Application {

	private static TheCollector theCollector;
	
	/**
	 * Get the singleton instance of this application.
	 * 
	 * @return The singleton instance
	 */
	public static TheCollector getTheCollector() {
		return theCollector;
	}
	
	private Stage stage;
	private Scene scene;
	private AnchorPane mainLayout;

	private MainViewController controller;

	private String cssPath;
	private String cssSelectedPath;

	private SettingsFile settingsFile;
	private String databasePath;
	private StatusCodes applicationStatus;

	/**
	 * Return the main Stage.
	 * 
	 * @return Stage
	 */
	public Stage getPrimaryStage() {
		return stage;
	}
	
	/**
	 * Return the main scene.
	 * 
	 * @return Scene
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Return the current CSS theme (stylesheet).
	 * 
	 * @return Default CSS stylesheet path.
	 */
	public String getStyle() {
		return this.cssPath;
	}

	/**
	 * Return the current CSS theme (stylesheet) for highlighting (selecting) items.
	 * 
	 * @return Selected CSS stylesheet path.
	 */
	public String getStyleSelected() {
		return this.cssSelectedPath;
	}
	
	/**
	 * Set the cursor pointer.
	 * 
	 * @param cursorType - String
	 */
	public void setCursor(String cursorType) {
		if (cursorType == "WAIT") {
			this.scene.setCursor(Cursor.WAIT);
		}
		if (cursorType == "DEFAULT") {
			this.scene.setCursor(Cursor.DEFAULT);
		}
	}
	
	/**
	 * Return the path to the card database.
	 * 
	 * @return String
	 */
	public String getDatabasePath() {
		return this.databasePath;
	}

    // TODO: IJC - DEBUG
	public void testLogging(String message) {
	    LoggerInterface.logger(this).log(Level.SEVERE, "DEBUG: " + message);
	}
    // TODO: IJC - DEBUG
    	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	public void start(Stage primaryStage) throws Exception {
		theCollector = this;
		this.stage = primaryStage;
		this.stage.setTitle("TheCollector");
		// TODO: Make this configurable - allow the user to choose different themes
		//		 and store this in preferences.
		this.cssPath = TheCollector.class.getResource(Settings.DEFAULT_STYLE).toString();
		this.cssSelectedPath = TheCollector.class.getResource(Settings.DEFAULT_SELECTED_STYLE).toString();
		
		this.applicationStatus = StatusCodes.OK;
		
		// TODO: Get application icon.
		this.stage.getIcons().add(new Image("file:resources/images/book.png"));

		// Set up the settings handler.
		this.settingsFile = new SettingsFile(FileUtil.getUserAppDirectory(Settings.APPLICATION_NAME), Settings.INI_FILE_NAME);
		
		// Check for settings error.
		if (!this.settingsFile.settingsOK()) {
			this.applicationStatus = StatusCodes.SETTINGS_ERROR;
		}
		
		// Create the logs folder if necessary.
		if (this.applicationStatus == StatusCodes.OK) {
			String logsFolder = FileUtil.getUserAppDirectory(Settings.APPLICATION_NAME) + System.getProperty("file.separator") + Settings.LOGGING_FOLDER;
			if (!FileUtil.createDirectory(logsFolder)) {
				this.applicationStatus = StatusCodes.SEVERE_ERROR;
				LoggerInterface.logger(this).log(Level.SEVERE, "Unable to create logging directory!");
			}
		}
		
		// Get the database file location.
		this.databasePath = FileUtil.getResourcePath("thecollector.model.TheCollector", Settings.MTG_JSON_SET);
		File databaseFilePath = new File(this.databasePath);
		if (!databaseFilePath.exists()) {
			this.applicationStatus = StatusCodes.DATABASE_ERROR;
			LoggerInterface.logger(this).log(Level.SEVERE, "Unable to locate the main card database!");
			if (databaseFilePath.getPath() == null | databaseFilePath.getPath() == "") {
				LoggerInterface.logger(this).log(Level.SEVERE, "Could not resolve path to main card database");
			} else {
				LoggerInterface.logger(this).log(Level.SEVERE, "Attempted to find database at location: " + databaseFilePath.getPath());
			}
		}
		
        // Check application status before proceeding.
        if (this.applicationStatus != StatusCodes.OK) {
        	stop();
        }
        
        // TODO: IJC - DEBUG
        LoggerInterface.logger(this).log(Level.SEVERE, "DEBUG: Test message!");
        // TODO: IJC - DEBUG
        
		try {
			// Load the root layout from the "start" view fxml file.
			FXMLLoader loader = new FXMLLoader(TheCollector.class.getResource("/thecollector/view/MainView.fxml"));
			this.mainLayout = (AnchorPane) loader.load();
			this.scene = new Scene(mainLayout);
			this.stage.setScene(scene);

			// Give the controller access to the main app.
			this.controller = (MainViewController) loader.getController();
			this.controller.setMainApp(theCollector);

			// Show the Stage.
			this.stage.show();
			
			// Let the controller perform its setup routines.
			this.controller.setup();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			LoggerInterface.logger(this).log(Level.SEVERE, "Exception occured", e);
		}
	}
	
	public void stop() throws Exception {
		// Save the settings, unless there was a problem with the settings file in the first place.
		if (this.applicationStatus != StatusCodes.SETTINGS_ERROR) {
			this.settingsFile.save(Settings.SETTINGS_COMMENT);
		}
    }

	public static void main(String[] args) {
		launch(args);
	}
	
}
