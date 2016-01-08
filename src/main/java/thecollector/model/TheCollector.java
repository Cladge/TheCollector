package thecollector.model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thecollector.controller.MainViewController;
import thecollector.utils.FileUtil;

/**
 * The main application.
 * 
 * @author Ian Claridge
 */
public class TheCollector extends Application {

	private static TheCollector theCollector;
	
	// Logging utility.
	protected static Logger logger() { return Logger.getLogger(TheCollector.class.getName()); }
	
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

	private File settingsDir;
	
	private File iniFilePath;
	private Properties appProperties;
	
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
	 * Return the settings path.
	 * 
	 * @return File
	 */
	public File getSettingsDir() {
		return this.settingsDir;
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

		// Get the settings path.
		try {
	        this.settingsDir = FileUtil.getSettingsDirectory(Settings.APPLICATION_NAME);	
		} catch (IllegalStateException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
			this.applicationStatus = StatusCodes.SETTINGS_ERROR;
		}
		
		// Get the settings file.
		if (this.applicationStatus == StatusCodes.OK) {
	        if (this.settingsDir != null) {
	            this.iniFilePath = new File(this.settingsDir + "/" + Settings.INI_FILE_NAME);
	            
	            // If the settings file does not exist, create it and set default properties.
	            if (!FileUtil.checkFile(this.iniFilePath)) {
	            	try {
	                	this.iniFilePath.createNewFile();
	                	this.appProperties = new Properties();
	    				this.appProperties.setProperty("Version", Settings.APPLICATION_VERSION);
	    				FileUtil.writePropertiesXmlFile(this.iniFilePath, this.appProperties, "Application Settings");
	            	} catch (IOException e){
	            		logger().log(Level.SEVERE, "Exception occured", e);
	            		this.applicationStatus = StatusCodes.SEVERE_ERROR;
	            	}
	            }
	            
	            // Load the properties file.
	            this.appProperties = FileUtil.readPropertiesXmlFile(this.iniFilePath);
	            
	        } else {
	        	this.applicationStatus = StatusCodes.SETTINGS_ERROR;
	        	logger().log(Level.SEVERE, "Unable to locate settings directory!");
	        }
		}
		
        // Check application status before proceeding.
        if (this.applicationStatus != StatusCodes.OK) {
        	stop();
        }
        
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
    		logger().log(Level.SEVERE, "Exception occured", e);
		}
	}
	
	public void stop() throws Exception {
		// Write out the properties file.
		if (this.applicationStatus != StatusCodes.SETTINGS_ERROR) {
			try {
				this.appProperties.setProperty("Version", Settings.APPLICATION_VERSION);
				FileUtil.writePropertiesXmlFile(this.iniFilePath, this.appProperties, "Application Settings");	
			} catch (Exception e) {
        		logger().log(Level.SEVERE, "Exception occured", e);
			}	
		}
    }

	public static void main(String[] args) {
		launch(args);
	}
	
}
