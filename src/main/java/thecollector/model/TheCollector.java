package thecollector.model;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import thecollector.controller.StartingView;

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
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Stage stage;
	private Scene scene;
	private VBox mainLayout;

	private StartingView controller;

	private String cssPath;
	private String cssSelectedPath;
	
	private static final String DEFAULT_STYLE = "DefaultTheme.css";
	private static final String DEFAULT_SELECTED_STYLE = "DefaultThemeSelected.css";
	
	private static final String INI_FILE_NAME = "TheCollector.xml";	//TheCollector.class.getResource("/thecollector/model/TheCollector.xml").toString();	
	private File iniFilePath;

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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		theCollector = this;
		this.stage = primaryStage;
		this.stage.setTitle("TheCollector");
		// TODO: Make this configurable - allow the user to choose different themes
		//		 and store this in preferences.
		this.cssPath = TheCollector.class.getResource(DEFAULT_STYLE).toString();
		this.cssSelectedPath = TheCollector.class.getResource(DEFAULT_SELECTED_STYLE).toString();
		
		// TODO: Get application icon.
		this.stage.getIcons().add(new Image("file:resources/images/book.png"));

		// DEBUG
		// Get the INI file.
		System.out.println(System.getProperty("user.home"));
		
		String path = this.getClass().getResource("").toString();
		System.out.println("App path = " + path);
		
    	File f = new File(this.getClass().getResource("").getPath().toString() + INI_FILE_NAME);
        System.out.println(f.getAbsolutePath()); //  
        System.out.println(f.getCanonicalPath());  
        System.out.println(f.isAbsolute());  
        System.out.println(f.canRead());  
        System.out.println(f.isFile());  
        System.out.println(f.exists());  
        System.out.println(f.getAbsoluteFile().exists()); 
		// DEBUG
		
		try {
			// Load the root layout from the "start" view fxml file.
			FXMLLoader loader = new FXMLLoader(TheCollector.class.getResource("/thecollector/view/StartingView.fxml"));
			mainLayout = (VBox) loader.load();
			scene = new Scene(mainLayout);
			this.stage.setScene(scene);
			this.stage.setResizable(false);

			// Give the controller access to the main app.
			controller = loader.getController();
			controller.setMainApp(theCollector);
			
			// Let the controller perform its initialisation routines.
			controller.initialise();

			// And finally...show the Stage.
			this.stage.show();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}
	
}
