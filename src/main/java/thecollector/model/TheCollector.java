package thecollector.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thecollector.controller.StartingView;
import thecollector.model.mtg.CardLoader;
import thecollector.model.mtg.card.MtgCard;
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

	private StartingView controller;

	private String cssPath;
	private String cssSelectedPath;

	private static final String APPLICATION_NAME = "TheCollector";
	
	private static final String DEFAULT_STYLE = "DefaultTheme.css";
	private static final String DEFAULT_SELECTED_STYLE = "DefaultThemeSelected.css";
	
	private static final String INI_FILE_NAME = "TheCollector.xml";
	private static final String MTG_JSON_SET = "json/AllSets.json";	
	
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
		this.cssPath = TheCollector.class.getResource(DEFAULT_STYLE).toString();
		this.cssSelectedPath = TheCollector.class.getResource(DEFAULT_SELECTED_STYLE).toString();
		
		// TODO: Get application icon.
		this.stage.getIcons().add(new Image("file:resources/images/book.png"));

		// Get the INI file.
        File settingsDir = FileUtil.getSettingsDirectory(APPLICATION_NAME);
        if (settingsDir != null) {
            this.iniFilePath = new File(settingsDir + "/" + INI_FILE_NAME);
            FileUtil.checkFile(this.iniFilePath);
            FileUtil.writePropertiesXmlFile(this.iniFilePath);
            this.appProperties = FileUtil.readPropertiesXmlFile(this.iniFilePath);

            // Load the latest MTG collection.
            List<MtgCard> mtgCardList = CardLoader.loadCards(settingsDir + "/" + MTG_JSON_SET);
            
            for (MtgCard mtgCard : mtgCardList) {
				System.out.println("Card: " + mtgCard.getName() + ", Set: " + mtgCard.getSetName());
			}
        }
        else {
        	System.out.println("Unable to locate User directory!");
        }
		
        // Test JSON handling.
        // TODO: DEBUG
        // FileUtil.testEncodeJSONObject();
        // FileUtil.testEncodeJSONObjectStream();
		// TODO: DEBUG
        
		try {
			// Load the root layout from the "start" view fxml file.
			FXMLLoader loader = new FXMLLoader(TheCollector.class.getResource("/thecollector/view/StartingView.fxml"));
			this.mainLayout = (AnchorPane) loader.load();
			this.scene = new Scene(mainLayout);
			this.stage.setScene(scene);

			// Give the controller access to the main app.
			this.controller = (StartingView) loader.getController();
			this.controller.setMainApp(theCollector);
			
			// Let the controller perform its initialisation routines.
			this.controller.initialise();

			// And finally...show the Stage.
			this.stage.show();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
