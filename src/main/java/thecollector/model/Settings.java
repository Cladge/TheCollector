package thecollector.model;

/**
 * Application-wide settings.
 * 
 * @author Ian Claridge
 *
 */
public final class Settings {

	public static final String APPLICATION_NAME = "TheCollector";
	public static final String APPLICATION_TITLE = "The Collector";
	public static final String APPLICATION_VERSION = "0.1a";

	public static final String INI_FILE_NAME = "TheCollector.xml";
	public static final String SETTINGS_COMMENT = "Application Settings";

	public static final String LOGGING_FOLDER = "logs";
	
	public static final String MAIN_VIEW = "thecollector/view/MainView_v11.fxml";
	public static final String MTG_JSON_SET = "database/AllSets_v4.2.1.json";
	//public static final String MTG_JSON_SET = "database/AllSets_v3.4.0.json";
	
	public static final String DEFAULT_STYLE = "DefaultTheme.css";
	public static final String DEFAULT_SELECTED_STYLE = "DefaultThemeSelected.css";
	
	public static final String GATHERER_URL = "http://gatherer.wizards.com/Handlers/Image.ashx";
	
	public static final String LOADING_IMAGE = "thecollector/view/loading.jpg";
	public static final String ERROR_IMAGE = "thecollector/view/error.jpg";
	
	public static final double IMAGE_SIZE_WIDTH_NORMAL = 223;
	public static final double IMAGE_SIZE_WIDTH_DOUBLE = 446;
	public static final double IMAGE_SIZE_HEIGHT_NORMAL = 310;
	public static final double IMAGE_SIZE_HEIGHT_DOUBLE = 620;
}
