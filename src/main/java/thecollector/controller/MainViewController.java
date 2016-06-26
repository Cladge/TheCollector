package thecollector.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import thecollector.model.ImageHandler;
import thecollector.model.Settings;
import thecollector.model.TheCollector;
import thecollector.model.mtg.CardLoader;
import thecollector.model.mtg.card.MtgCard;
import thecollector.model.mtg.card.MtgCardDisplay;
import thecollector.utils.LoggerUtil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * The controller for the main application layout.
 * 
 * @author Ian Claridge
 */
public class MainViewController extends BaseViewController {
	
	private static TheCollector theCollector;

	@FXML
	private VBox mainView;
	
	@FXML
	private MenuBar topMenu;
	
	@FXML
	private SplitPane mainSplitView;

	@FXML
	private TableView<MtgCardDisplay> allCardsTableView;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardNameColumn;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> expansionColumn;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardTypeColumn;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardColourColumn;

	@FXML
	private TableColumn<MtgCardDisplay, String> cardRarityColumn;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardMultiverseIdColumn;

	@FXML
	private TableColumn<MtgCardDisplay, String> cardFlavourTextColumn;
	
	@FXML
	private TextField textStatus;
	
	@FXML
	private ImageView cardImageView;
	
	@FXML
	private ListView<String> cardDetails;

	// A list of ALL current cards in the collection.
	private List<MtgCard> mtgCardList;
	
	// An observable array list for populating the main TableView control.
	private ObservableList<MtgCardDisplay> displayData = FXCollections.observableArrayList();
	
	// The currently selected row data.
	private MtgCardDisplay currentCardData;
	
	// A collection of viewed images. Can be used to "cache" image objects for rows that
	// have been previously selected.
	private Map<Integer, ImageHandler> imageMap = new HashMap<Integer, ImageHandler>();
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		// Initialise the tableview columns with the appropriate column properties from the MtgCardDisplay class.
		this.cardNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		this.expansionColumn.setCellValueFactory(cellData -> cellData.getValue().getExpansionProperty());
		this.cardTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
		this.cardColourColumn.setCellValueFactory(cellData -> cellData.getValue().getColourProperty());
		this.cardRarityColumn.setCellValueFactory(cellData -> cellData.getValue().getRarityProperty());
		this.cardMultiverseIdColumn.setCellValueFactory(cellData -> cellData.getValue().getMultiverseIdProperty());
		this.cardFlavourTextColumn.setCellValueFactory(cellData -> cellData.getValue().getFlavourTextProperty());

		// Associate handler classes with controls.
		this.allCardsTableView.setOnMouseClicked(new TableViewMouseEventHandler(this.allCardsTableView, this));
		this.allCardsTableView.setOnKeyReleased(new TableViewKeyEventHandler(this.allCardsTableView, this));
		this.cardImageView.setOnMouseClicked(new ImageViewMouseEventHandler(this));
    }
    
	/**
	 * Return reference to the main view.
	 * 
	 * @return Entity VBox.
	 */
	public VBox getEntityPane () {
		return this.mainView;
	}
	
	/**
	 * Main initialisation method (not to be confused with initialize() method used
	 * by FXML loading process).
	 * 
	 * Load all the cards from the JSON file into a TableView.
	 */
	public void setup() {
		theCollector = (TheCollector) mainApp;
		
		this.setStatus("Loading card database...");
		DecimalFormat decimalFormat = new DecimalFormat("Number of cards found: ###,###,##0");
		String statusMessage = decimalFormat.format(this.loadCards());
		this.setStatus(statusMessage);
		LoggerUtil.logger(this).log(Level.INFO, statusMessage);

		this.setImageSize(Settings.IMAGE_SIZE_WIDTH_NORMAL, Settings.IMAGE_SIZE_HEIGHT_NORMAL);
		
		// If there is at least one card to display, select it and set the image.
		if (this.mtgCardList.size() > 0) {
			MtgCard mtgCard = this.mtgCardList.get(0);
			this.allCardsTableView.getSelectionModel().selectFirst();
			this.setCurrentCard(this.allCardsTableView.getSelectionModel().getSelectedItem());
			this.setCurrentImage(mtgCard.getMultiverseid());
		}
	}
	
	/**
	 * Load the main card set.
	 * 
	 * @return int - Card Count
	 */
	private int loadCards() {
		int cardCount = 0;

		theCollector.setCursor("WAIT");
		
        // Load the latest MTG collection.
		try {
			this.mtgCardList = CardLoader.loadCards(theCollector.getDatabasePath());

	        for (MtgCard mtgCard : this.mtgCardList) {
	        	MtgCardDisplay mtgCardRow = new MtgCardDisplay();
	        	mtgCardRow.setName(mtgCard.getName());
	        	mtgCardRow.setExpansion(mtgCard.getExpansion());
	        	
	        	ArrayList<String> types = mtgCard.getTypes();
	        	if (types == null || types.isEmpty()) {
	        		mtgCardRow.setType("-");
	        	} else {
		        	mtgCardRow.setType(types.get(0));	
	        	}

	        	ArrayList<String> subtypes = mtgCard.getSubtypes();
	        	if (subtypes != null && !subtypes.isEmpty()) {
	        		mtgCardRow.setType(mtgCardRow.getType() + " - " + subtypes.get(0));
	        	}
	        	
	        	ArrayList<String> colours = mtgCard.getColors();
	        	if (colours == null || colours.isEmpty()) {
	        		mtgCardRow.setColour("-");
	        	} else {
	        		mtgCardRow.setColour(colours.get(0));
	        	}
	        	
	        	mtgCardRow.setRarity(mtgCard.getRarity());
	        	
	        	if (mtgCard.getMultiverseid() == null) {
		        	mtgCardRow.setMultiverseId("0");
	        	} else {
		        	mtgCardRow.setMultiverseId(mtgCard.getMultiverseid().toString());
	        	}
	        	
	        	mtgCardRow.setFlavourText(mtgCard.getFlavor());
	        	mtgCardRow.setManaCost(mtgCard.getManaCost());
	        	mtgCardRow.setCardText(mtgCard.getText());
	        	
	        	String power = mtgCard.getPower();
	        	String toughness = mtgCard.getToughness();
	        	if (power == null || power.isEmpty()) {
	        		power = "-";
	        	}
	        	if (toughness == null || toughness.isEmpty()) {
	        		toughness = "-";
	        	}
	        	mtgCardRow.setPowerToughness(String.format("%s/%s", power, toughness));
	        	
	        	// Check for null or empty values.
	        	if (mtgCardRow.getName() == null || mtgCardRow.getName().isEmpty()) {
	        		mtgCardRow.setName("-");
	        	}
	        	if (mtgCardRow.getExpansion() == null || mtgCardRow.getExpansion().isEmpty()) {
	        		mtgCardRow.setExpansion("-");
	        	}
	        	if (mtgCardRow.getType() == null || mtgCardRow.getType().isEmpty()) {
	        		mtgCardRow.setType("-");
	        	}
	        	if (mtgCardRow.getColour() == null || mtgCardRow.getColour().isEmpty()) {
	        		mtgCardRow.setColour("-");
	        	}
	        	if (mtgCardRow.getRarity() == null || mtgCardRow.getRarity().isEmpty()) {
	        		mtgCardRow.setRarity("-");
	        	}
	        	if (mtgCardRow.getMultiverseId() == null || mtgCardRow.getMultiverseId().isEmpty()) {
	        		mtgCardRow.setMultiverseId("0");
	        	}
	        	if (mtgCardRow.getFlavourText() == null || mtgCardRow.getFlavourText().isEmpty()) {
	        		mtgCardRow.setFlavourText("-");
	        	}
	        	if (mtgCardRow.getManaCost() == null || mtgCardRow.getManaCost().isEmpty()) {
	        		mtgCardRow.setManaCost("-");
	        	}
	        	if (mtgCardRow.getCardText() == null || mtgCardRow.getCardText().isEmpty()) {
	        		mtgCardRow.setCardText("-");
	        	}
	        	
	        	// Added the display row to the display data list.
	        	this.displayData.add(mtgCardRow);
			}
	        
	        // Populate the List View.
	        if (!this.displayData.isEmpty()) {
	    		// Populate the Table View.
	        	this.allCardsTableView.setItems(this.displayData);
	        	cardCount = this.displayData.size();
	        }
	        
		} catch (JsonParseException e) {
			LoggerUtil.logger(this).log(Level.SEVERE, "Exception occurred", e);
		} catch (JsonMappingException e) {
			LoggerUtil.logger(this).log(Level.SEVERE, "Exception occurred", e);
		} catch (IOException e) {
			LoggerUtil.logger(this).log(Level.SEVERE, "Exception occurred", e);
		}

		theCollector.setCursor("DEFAULT");
		
		return cardCount;
	}
	
	/**
	 * Set the status bar text.
	 * 
	 * @param message - String
	 */
	private void setStatus(String message) {
		this.textStatus.setText(message);
	}
	
	/**
	 * Opens an about dialog.
	 */
	public void handleAbout() {
		System.out.println("\nGUI not implemented yet!");
		System.out.println("\nAuthor:\nIan Claridge\n\nWebsite:\nhttp://www.cladge.com");
		System.out.println("\nTheCollector v0.1 (beta)");
	}
	
	/**
	 * From a TableView event (e.g. mouse event, key event, etc.) set the currently selected card
	 * and display the relevant image.
	 * 
	 * @param currentCardData - MtgCardDisplay
	 */
	public void setCurrentCard(MtgCardDisplay currentCardData) {
		// If the card data passed is null, this generally happens if the user has clicked one of the header columns.
		// In this case, assuming there is a current row (card data), scroll to that current row.
		if (currentCardData == null) {
			if (this.currentCardData != null) {
				this.allCardsTableView.scrollTo(this.currentCardData);	
			}
			
		} else {
			if (currentCardData != null && (!currentCardData.equals(this.currentCardData))) {
				String multiverseIdData = currentCardData.getMultiverseId();
				if (multiverseIdData != null && !multiverseIdData.isEmpty()) {
					int multiverseId = Integer.valueOf(multiverseIdData);
					this.setCurrentImage(multiverseId);
					this.currentCardData = currentCardData;
					
					// Populate the list View.
					ObservableList<String> cardItems = FXCollections.observableArrayList (
						    String.format("Name: %s", this.currentCardData.getName()),
						    String.format("Mana Cost: %s", this.currentCardData.getManaCost()),
						    String.format("Type: %s", this.currentCardData.getType()),
						    String.format("Text: %s", this.currentCardData.getCardText()),
						    String.format("Power/Toughness: %s", this.currentCardData.getPowerToughness()),
						    String.format("Colour: %s", this.currentCardData.getColour()),
						    String.format("Expansion: %s", this.currentCardData.getExpansion()),
						    String.format("Flavour Text: %s", this.currentCardData.getFlavourText()));
					this.cardDetails.setItems(cardItems);	

					// TODO: DEBUG - Show card info for debug purposes.
					LoggerUtil.logger(this).log(Level.INFO, this.currentCardData.toString());
					// TODO: DEBUG - Show card info for debug purposes.	
				}
			}
		}
	}
	
	/**
	 * Use the passed Multiverse ID to set the relevant card image for the currently selected table row.
	 * 
	 * @param multiverseId - int
	 */
	private void setCurrentImage(int multiverseId) {
		ImageHandler imageHandler;
		
		// See if the image handler has already been created for this Multiverse ID. If not,
		// create it and add an entry to the image map.
		if (this.imageMap.containsKey(multiverseId)){
			imageHandler = this.imageMap.get(multiverseId);
		} else {
			imageHandler = new ImageHandler(multiverseId, true);
			this.imageMap.put(multiverseId, imageHandler);
		}
		
		// Use placeholder image first.
		this.cardImageView.setImage(imageHandler.getPlaceholderImage());
		
		// Now create the required image.
		Image cardImage = imageHandler.getImage();
		
		// If the image was previously loaded (successfully), just set the image.
		// Otherwise, use a listener to monitor the loading of the image which
		// eventually sets the image once it has successfully loaded.
		if (imageHandler.imageHasLoaded()) {
			this.cardImageView.setImage(cardImage);
			// TODO: DEBUG
			LoggerUtil.logger(this).log(Level.INFO, String.format("Multiverse ID %d: Image cached....getting image....", multiverseId));
			// TODO: DEBUG
		} else {
			// This listener on the image's progress is used to set the Image View when the image has finally loaded. In the meantime,
			// the Image View will continue to display the "placeholder" image.
			cardImage.progressProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					// TODO: DEBUG
					//LoggerUtil.logger(this).log(Level.INFO, String.format("Multiverse ID %d: Image progress: %s", multiverseId, String.valueOf(newValue)));
					//LoggerUtil.logger(this).log(Level.INFO, String.format("Multiverse ID %d: Image error: %b", multiverseId, cardImage.isError()));
					// TODO: DEBUG
					
					double cardProgress = (double) newValue;
					if (cardProgress == 1.0) {
						if (cardImage.isError()) {
							cardImageView.setImage(imageHandler.getErrorImage());
							// TODO: DEBUG
							LoggerUtil.logger(this).log(Level.INFO, String.format("Multiverse ID %d: Error loading image.", multiverseId));
							// TODO: DEBUG
						} else {
							cardImageView.setImage(cardImage);
							imageHandler.setImageLoaded(true);
							// TODO: DEBUG
							LoggerUtil.logger(this).log(Level.INFO, String.format("Multiverse ID %d: Image loaded successfully!", multiverseId));
							// TODO: DEBUG
						}
					}
				}
			});	
		}
	}
	
	/**
	 * Resize the card image, either by passing a new width value, or letting the method
	 * determine the new size based on the image view's current size (i.e. switching between "normal"
	 * and "double" size).
	 * 
	 * @param newWidth - double
	 */
	public void setImageSize(double newWidth, double newHeight) {
		// TODO: DEBUG - Show image info for debug purposes.
		LoggerUtil.logger(this).log(Level.INFO, String.format("Image's current FitWidth: %s", this.cardImageView.getFitWidth()));
		// TODO: DEBUG - Show image info for debug purposes.
		if (newWidth > 0) {
			this.cardImageView.setFitWidth(newWidth);
			this.cardImageView.setFitHeight(newHeight);
		} else {
			if (this.cardImageView.getFitWidth() == Settings.IMAGE_SIZE_WIDTH_NORMAL) {
				this.cardImageView.setFitWidth(Settings.IMAGE_SIZE_WIDTH_DOUBLE);
				this.cardImageView.setFitHeight(Settings.IMAGE_SIZE_HEIGHT_DOUBLE);
			} else {
				this.cardImageView.setFitWidth(Settings.IMAGE_SIZE_WIDTH_NORMAL);
				this.cardImageView.setFitHeight(Settings.IMAGE_SIZE_HEIGHT_NORMAL);
			}	
		}
		// TODO: DEBUG - Show image info for debug purposes.
		LoggerUtil.logger(this).log(Level.INFO, String.format("Image's new FitWidth: %s", this.cardImageView.getFitWidth()));
		// TODO: DEBUG - Show image info for debug purposes.
	}
	
}

/**
 * A TableView handler class for mouse events.
 */
class TableViewMouseEventHandler implements EventHandler<MouseEvent> {

	private TableView<MtgCardDisplay> cardsTableView;
	private MainViewController controller;
	
	// Constructor.
	public TableViewMouseEventHandler(TableView<MtgCardDisplay> tableView, MainViewController controller) {
		this.cardsTableView = tableView;
		this.controller = controller;
	}
	
	@Override
	public void handle(MouseEvent event) {
    	String eventTargetType = event.getTarget().toString();
    	if (eventTargetType.substring(0, 4).toLowerCase().equalsIgnoreCase("text") ||
    			eventTargetType.substring(0, 12).toLowerCase().equalsIgnoreCase("TableColumn$")) {
        	this.controller.setCurrentCard(this.cardsTableView.getSelectionModel().getSelectedItem());
    	} else {
    		this.controller.setCurrentCard(null);
    	}
	}
	
}

/**
 * A TableView handler class for key (input) events.
 */
class TableViewKeyEventHandler implements EventHandler<KeyEvent> {

	private TableView<MtgCardDisplay> cardsTableView;
	private MainViewController controller;
	
	// Constructor.
	public TableViewKeyEventHandler(TableView<MtgCardDisplay> tableview, MainViewController controller) {
		this.cardsTableView = tableview;
		this.controller = controller;
	}
	
	@Override
	public void handle(KeyEvent event) {
		if (event.getCode().getName().equalsIgnoreCase("up") || event.getCode().getName().equalsIgnoreCase("down"))
		this.controller.setCurrentCard(this.cardsTableView.getSelectionModel().getSelectedItem());
	}
}

/**
 * An ImageView handler class for mouse events.
 */
class ImageViewMouseEventHandler implements EventHandler<MouseEvent> {

	private MainViewController controller;
	
	// Constructor.
	public ImageViewMouseEventHandler(MainViewController controller) {
		this.controller = controller;
	}
	@Override
	public void handle(MouseEvent event) {
		this.controller.setImageSize(0, 0);
	}
}
