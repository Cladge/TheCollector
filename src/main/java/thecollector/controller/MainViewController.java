package thecollector.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import thecollector.model.ImageHandler;
import thecollector.model.TheCollector;
import thecollector.model.mtg.CardLoader;
import thecollector.model.mtg.card.MtgCard;
import thecollector.model.mtg.card.MtgCardDisplay;
import thecollector.utils.LoggerUtil;

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
	private Label labelStatus;
	
	@FXML
	private ImageView cardImageView;

	// A list of ALL current cards in the collection.
	private List<MtgCard> mtgCardList;
	
	// An observable array list for populating the main TableView control.
	private ObservableList<MtgCardDisplay> displayData = FXCollections.observableArrayList();
	
	// The currently selected row data.
	private MtgCardDisplay currentCardData;
	
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

		// Associate handler classes with controls.
		this.allCardsTableView.setOnMouseClicked(new TableViewMouseEventHandler(this.allCardsTableView, this));
		this.allCardsTableView.setOnKeyReleased(new TableViewKeyEventHandler(this.allCardsTableView, this));
    }
    
	/**
	 * Return reference to the main AnchorPane.
	 * 
	 * @return Entity AnchorPane.
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
		String statusMessage = String.format("Number of cards found: %s", this.loadCards());
		this.setStatus(statusMessage);
		LoggerUtil.logger(this).log(Level.INFO, statusMessage);
		
		// TODO: DEBUG
		// Load a test image.
		this.mainSplitView.setDividerPosition(0, 0.8);
		Image image = new Image("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=370812&type=card");
		this.cardImageView.setImage(image);
		// TODO: DEBUG
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
		this.labelStatus.setText(message);
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
		this.currentCardData = currentCardData;
		
		if (this.currentCardData != null) {
			String multiverseIdData = this.currentCardData.getMultiverseId();
			if (multiverseIdData != null && !multiverseIdData.isEmpty()) {
				int multiverseId = Integer.valueOf(multiverseIdData);
				ImageHandler imageHandler = new ImageHandler(multiverseId, true);
				Image cardImage = imageHandler.createImage();
				this.cardImageView.setImage(cardImage);
			}
		}
		
		// TODO: Show card info for debug purposes.
		LoggerUtil.logger(this).log(Level.INFO, this.currentCardData.toString());
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
        	this.controller.setCurrentCard(cardsTableView.getSelectionModel().getSelectedItem());
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
		this.controller.setCurrentCard(cardsTableView.getSelectionModel().getSelectedItem());
	}
	
}
