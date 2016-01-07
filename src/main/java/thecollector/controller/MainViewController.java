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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import thecollector.model.Settings;
import thecollector.model.TheCollector;
import thecollector.model.mtg.CardLoader;
import thecollector.model.mtg.card.MtgCard;
import thecollector.model.mtg.card.MtgCardDisplay;
import thecollector.utils.AbstractLogger;

/**
 * The controller for the main application layout.
 * 
 * @author Ian Claridge
 */
public class MainViewController extends BaseViewController {
	
	private static TheCollector theCollector;

	@FXML
	private AnchorPane anchorpaneStartingView;

	@FXML
	private TableView<MtgCardDisplay> allCardsTableView;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardNameColumn;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> setNameColumn;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardTypeColumn;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardColourColumn;

	@FXML
	private TableColumn<MtgCardDisplay, String> cardRarityColumn;
	
	@FXML
	private Label labelStatus;

	// A list of ALL current cards in the collection.
	private List<MtgCard> mtgCardList;
	
	// An observable array list for populating the main TableView control.
	private ObservableList<MtgCardDisplay> displayData = FXCollections.observableArrayList();
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		// Initialise the tableview columns with the appropriate column properties from the MtgCardDisplay class.
		this.cardNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		this.setNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSetNameProperty());
		this.cardTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
		this.cardColourColumn.setCellValueFactory(cellData -> cellData.getValue().getColourProperty());
		this.cardRarityColumn.setCellValueFactory(cellData -> cellData.getValue().getRarityProperty());

		// Associate handler classes with controls.
		this.allCardsTableView.setOnMouseClicked(new TableViewEventHandler(this.allCardsTableView));
    }
    
	/**
	 * Return reference to the main AnchorPane.
	 * 
	 * @return Entity AnchorPane.
	 */
	public AnchorPane getEntityPane () {
		return this.anchorpaneStartingView;
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
		logger().log(Level.INFO, statusMessage);
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
			this.mtgCardList = CardLoader.loadCards(theCollector.getSettingsDir() + "/" + Settings.MTG_JSON_SET);

	        for (MtgCard mtgCard : this.mtgCardList) {
	        	MtgCardDisplay mtgCardRow = new MtgCardDisplay();
	        	mtgCardRow.setName(mtgCard.getName());
	        	mtgCardRow.setSetName(mtgCard.getSetName());
	        	
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
	        	
	        	// Check for null or empty values.
	        	if (mtgCardRow.getName() == null || mtgCardRow.getName().isEmpty()) {
	        		mtgCardRow.setName("-");
	        	}
	        	if (mtgCardRow.getSetName() == null || mtgCardRow.getSetName().isEmpty()) {
	        		mtgCardRow.setSetName("-");
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
	        	
	        	// Added the displayable row to the display data list.
	        	this.displayData.add(mtgCardRow);
			}
	        
	        // Populate the List View.
	        if (!this.displayData.isEmpty()) {
	    		// Populate the Table View.
	        	this.allCardsTableView.setItems(this.displayData);
	        	cardCount = this.displayData.size();
	        }
	        
		} catch (JsonParseException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		} catch (JsonMappingException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		} catch (IOException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
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

}

class TableViewEventHandler extends AbstractLogger implements EventHandler<MouseEvent> {

	private TableView<MtgCardDisplay> cardsTableView;
	
	// Constructor.
	public TableViewEventHandler(TableView<MtgCardDisplay> tableView) {
		this.cardsTableView = tableView;
	}
	
	@Override
	public void handle(MouseEvent event) {
    	String eventTargetType = event.getTarget().toString();
    	if (eventTargetType.substring(0, 4).toLowerCase().equalsIgnoreCase("text") ||
    			eventTargetType.substring(0, 12).toLowerCase().equalsIgnoreCase("TableColumn$")) {
        	MtgCardDisplay mtgCardDisplay = cardsTableView.getSelectionModel().getSelectedItem();
        	System.out.println(mtgCardDisplay.toString());
        	logger().log(Level.INFO, mtgCardDisplay.toString());
    	}		
	}
	
}
