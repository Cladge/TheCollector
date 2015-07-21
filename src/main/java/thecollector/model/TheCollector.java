package thecollector.model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thecollector.controller.MainView;
import thecollector.utils.FileUtil;

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

	private MainView controller;

	private String cssPath;
	private String cssSelectedPath;

	private File settingsDir;
	
	private File iniFilePath;
	private Properties appProperties;

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
		
		// TODO: Get application icon.
		this.stage.getIcons().add(new Image("file:resources/images/book.png"));

		// Get the settings path and settings file.
		boolean settingsOK = true;
        this.settingsDir = FileUtil.getSettingsDirectory(Settings.APPLICATION_NAME);
        if (this.settingsDir != null) {
            this.iniFilePath = new File(this.settingsDir + "/" + Settings.INI_FILE_NAME);
            if (FileUtil.checkFile(this.iniFilePath)) {
                this.appProperties = FileUtil.readPropertiesXmlFile(this.iniFilePath);	
            } else {
            	settingsOK = false;
            }
        } else {
        	settingsOK = false;
        }
		
        if (!settingsOK) {
        	System.out.println("Unable to locate settings file!");
        }
        
		try {
			// Load the root layout from the "start" view fxml file.
			FXMLLoader loader = new FXMLLoader(TheCollector.class.getResource("/thecollector/view/MainView.fxml"));
			this.mainLayout = (AnchorPane) loader.load();
			this.scene = new Scene(mainLayout);
			this.stage.setScene(scene);

			// Give the controller access to the main app.
			this.controller = (MainView) loader.getController();
			this.controller.setMainApp(theCollector);

			// Show the Stage.
			this.stage.show();
			
			// Let the controller perform its initialisation routines.
			this.controller.initialise();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
