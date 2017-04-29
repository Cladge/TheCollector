package thecollector.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import thecollector.model.ImageHandler;
import thecollector.model.Settings;
import thecollector.model.TheCollector;
import thecollector.model.mtg.CardLoader;
import thecollector.model.mtg.card.MtgCard;
import thecollector.model.mtg.card.MtgCardDetailsHtmlGenerator;
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
	private TableColumn<MtgCardDisplay, String> cardExpansionColumn;
	
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
	private TableColumn<MtgCardDisplay, Number> cardCmc;
	
	@FXML
	private TableColumn<MtgCardDisplay, String> cardPowerToughness;
	
	@FXML
	private TextField textStatus;
	
	@FXML
	private ImageView cardImageView;
	
	@FXML
	private WebView cardDetails;
	
	@FXML
	private TextField quickSearch;
	
	@FXML
	private Button buttonClearQuickSearch;
	
	@FXML
	private HBox quickSearchContainer;
	
	@FXML
	private VBox filterContainer;
	
	@FXML
	private ComboBox<String> comboBoxFilterExpansion;

	// A list of ALL current cards in the collection.
	private List<MtgCard> mtgCardList;
	
	// An observable array list for populating the main TableView control.
	private ObservableList<MtgCardDisplay> cardCollectionData = FXCollections.observableArrayList();
	
	// The currently selected row data.
	private MtgCardDisplay currentCardData;
	
	// A collection of viewed images. Can be used to "cache" image objects for rows that
	// have been previously selected.
	private Map<Integer, ImageHandler> imageMap = new HashMap<Integer, ImageHandler>();
	
	// Used for controlling timed update of card count (when filtering data).
	private boolean cardCountScheduled = false;
	private boolean cardsCurrentlyFiltered = false;
	private Timer cardCountTimer;
	private TimerTask timerTask;
	
	/**
	 * Set (or reset) the Timer Task used for getting the current (displayed) Card Count.
	 */
	private void createNewTimerTask() {
		this.timerTask = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
			        public void run() {
			        	getCardCount();
						scrollToCurrentCard();
			        }
				});
			}
		};
	}

	/**
	 *********************************************************************************
	 * The following methods are annotated FXML handlers for different JavaFX controls
	 * (usually referenced by the UI FXML file).
	 */

    /**
	 * Set up required control tooltips.
	 */
	private void createTooltips() {
		Tooltip imageViewTooltip = new Tooltip("Click image to enlarge or reduce");
		Tooltip.install(this.cardImageView, imageViewTooltip);

		Tooltip quickSearchTooltip = new Tooltip("Use Quick Search to look for values in Card Names, Rules Text or Flavor Text");
		Tooltip.install(this.quickSearch, quickSearchTooltip);
		
		Tooltip clearQuickSearchTooltip = new Tooltip("Use this button to clear the current Quick Search text");
		Tooltip.install(this.buttonClearQuickSearch, clearQuickSearchTooltip);
	}
    
	/**
	 * Get the current card count based on the number of items in the Table View.
	 * 
	 * @param filtered - boolean
	 */
	private void getCardCount() {
		boolean cardsCurrentlyFiltered = this.cardsCurrentlyFiltered;
		int cardCount = this.allCardsTableView.getItems().size();
		if (cardCount == this.cardCollectionData.size()) {
			cardsCurrentlyFiltered = false;
		}
		
		this.setCardCountMessage(cardCount, cardsCurrentlyFiltered);
		this.cardCountScheduled = false;
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
	 * The below methods are annotated FXML handlers for different JavaFX controls
	 * (usually referenced by the UI FXML file).
	 * 
	 *********************************************************************************
	 */

	
	/**
	 * Opens an about dialog.
	 */
	@FXML
	public void handleAbout() {
		System.out.println("\nGUI not implemented yet!");
		System.out.println("\nAuthor:\nIan Claridge\n\nWebsite:\nhttp://www.cladge.com");
		System.out.println("\nTheCollector v0.1 (beta)");
	}
	
	/**
	 * Handler for the Clear Quick Search button.
	 * 
	 * @param event - ActionEvent
	 */
	@FXML
	public void handleButtonClearQuickSearchAction(ActionEvent event) {
		this.quickSearch.clear();
		this.quickSearch.requestFocus();
    }
	
	/**
	 * Handler for the Quick Search menu item.
	 * 
	 * @param event - ActionEvent
	 */
	@FXML
	public void handleMenuItemQuickSearchAction(ActionEvent event) {
		this.showOrHideQuickSearch();
	}

	/**
	 * Handler for the Card Filter menu item.
	 * 
	 * @param event - ActionEvent
	 */
	@FXML
	public void handleMenuItemFilterAction(ActionEvent event) {
		this.showOrHideFilter();
	}
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		// Initialise the tableview columns with the appropriate column properties from the MtgCardDisplay class.
		this.cardNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		this.cardExpansionColumn.setCellValueFactory(cellData -> cellData.getValue().getExpansionProperty());
		this.cardTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
		this.cardColourColumn.setCellValueFactory(cellData -> cellData.getValue().getColourProperty());
		this.cardRarityColumn.setCellValueFactory(cellData -> cellData.getValue().getRarityProperty());
		this.cardMultiverseIdColumn.setCellValueFactory(cellData -> cellData.getValue().getMultiverseIdProperty());
		this.cardFlavourTextColumn.setCellValueFactory(cellData -> cellData.getValue().getFlavourTextProperty());
		this.cardCmc.setCellValueFactory(cellData -> cellData.getValue().getCmcProperty());
		this.cardPowerToughness.setCellValueFactory(cellData -> cellData.getValue().getPowerToughnessProperty());

		// Associate handler classes with controls.
		this.allCardsTableView.setOnMouseClicked(new TableViewMouseEventHandler(this.allCardsTableView, this));
		this.allCardsTableView.setOnKeyReleased(new TableViewKeyEventHandler(this.allCardsTableView, this));
		this.cardImageView.setOnMouseClicked(new ImageViewMouseEventHandler(this));
		//this.quickSearch.setOnKeyReleased(new TextFieldKeyEventHandler(this));
		
		// No need for context menu on Web View control.
		this.cardDetails.setContextMenuEnabled(false);
		
		// Hide the Quick Search and Filter controls.
		this.showOrHideQuickSearch();
		this.showOrHideFilter();
    }
	
	/**
	 * Load the main card set.
	 * 
	 * @return int - Card Count
	 */
	private int loadCards() {
		int cardCount = 0;

		Set<String> expansionSet = new TreeSet<String>();
		ObservableList<String> expansionFilterData = FXCollections.observableArrayList();
		
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
	        	
	        	if (mtgCard.getCmc() == null) {
	        		mtgCardRow.setCmc(0);
	        	} else {
	        		mtgCardRow.setCmc(mtgCard.getCmc());
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
	        	this.cardCollectionData.add(mtgCardRow);
	        	
	        	// Add the relevant values to the Filter control lists.
	        	expansionSet.add(mtgCard.getExpansion());
			}
	        
	        // Populate the List View.
	        if (!this.cardCollectionData.isEmpty()) {
	        	expansionFilterData.addAll(expansionSet);
	    		this.comboBoxFilterExpansion.setItems(expansionFilterData);
	    		this.comboBoxFilterExpansion.getItems().add(0, "");
	        	this.setDataFilter();
	        	cardCount = this.allCardsTableView.getItems().size();
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
	 * Scroll the table view to the row containing the current card.
	 */
	public void scrollToCurrentCard() {
		if (this.currentCardData != null) {
			this.allCardsTableView.scrollTo(this.currentCardData);
			this.allCardsTableView.getSelectionModel().select(this.currentCardData);
		}
	}
	
	/**
	 * Use the passed value to set the status message to display a card count.
	 * 
	 * @param cardCount - int
	 * @param filtered - boolean
	 */
	private void setCardCountMessage(int cardCount, boolean filtered) {
		String messageFormat = "";
		if (filtered) {
			messageFormat = "Cards found: ###,###,##0";
		} else {
			messageFormat = "Total cards in database: ###,###,##0";
		}
		DecimalFormat decimalFormat = new DecimalFormat(messageFormat);
		String statusMessage = decimalFormat.format(cardCount);
		this.setStatus(statusMessage);
		
		// TODO: IJC - DEBUG
		LoggerUtil.logger(this).log(Level.INFO, statusMessage);
		// TODO: IJC - DEBUG
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
			this.scrollToCurrentCard();
		} else {
			if (currentCardData != null && (!currentCardData.equals(this.currentCardData))) {
				String multiverseIdData = currentCardData.getMultiverseId();
				if (multiverseIdData != null && !multiverseIdData.isEmpty()) {
					int multiverseId = Integer.valueOf(multiverseIdData);
					this.setCurrentImage(multiverseId);
					this.currentCardData = currentCardData;
					
					// Populate the Web View.
					MtgCardDetailsHtmlGenerator htmlDetails = new MtgCardDetailsHtmlGenerator(this.currentCardData);
			        this.cardDetails.getEngine().loadContent(htmlDetails.getContent());

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

		theCollector.setCursor("WAIT");
		
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

		theCollector.setCursor("DEFAULT");
	}
	
	/**
	 * Set the filter to be used on the card data. More than one control can be used as a filter candidate.
	 */
	private void setDataFilter() {
		// Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<MtgCardDisplay> filteredData = new FilteredList<>(this.cardCollectionData, mtgCardDisplay -> true);

		// Set the filter predicate whenever the filter conditions change.
		// The filter changes based on the following controls:
		//	1. quickSearch (TextField)
		//	2. comoBoxFilterExpansion (ComboBox)
		//
		// The following approach allows multiple bindings (that is, multiple filtering) by listing the conditions
		// and properties of the controls that you want to use to filter the data.

		// 2. Set the filter Predicate whenever the filters change.
		this.quickSearch.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					filteredData.setPredicate(mtgCard -> {
						return lookForMatch(mtgCard);
					});
				});
		
		this.comboBoxFilterExpansion.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					filteredData.setPredicate(mtgCard -> {
						return lookForMatch(mtgCard);
					});
				});
		
		//filteredData.predicateProperty().bind(Bindings.createObjectBinding(() ->
		//	mtgCardDisplay ->
		//		(  mtgCardDisplay.getName().toLowerCase().contains(this.quickSearch.getText().toLowerCase())
		//		|| mtgCardDisplay.getCardText().toLowerCase().contains(this.quickSearch.getText().toLowerCase())
		//		|| mtgCardDisplay.getFlavourText().toLowerCase().contains(this.quickSearch.getText().toLowerCase())),
				//|| mtgCardDisplay.getExpansion().toLowerCase().contains(this.comoBoxFilterExpansion.getValue().toLowerCase())),
			
			//this.quickSearch.textProperty())
			//this.comoBoxFilterExpansion.valueProperty())
			
		//);
		
		// Wrap the FilteredList in a SortedList.
		SortedList<MtgCardDisplay> sortedData = new SortedList<>(filteredData);

		// Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(this.allCardsTableView.comparatorProperty());
		
		// Populate the Table View.
    	this.allCardsTableView.setItems(sortedData);
	}

	/**
	 * Method used by the data filter. Various controls will filter the MTG Card data: tbd
	 * 
	 * @param mtgCard - MtgCardDisplay
	 * 
	 * @return boolean - Criteria matches
	 */
	private boolean lookForMatch(MtgCardDisplay mtgCard) {
		boolean match = false;
		boolean filterValuesExist = true;
		
		String quickSearchText = this.quickSearch.getText().toLowerCase();
		String expansionSearchText = this.comboBoxFilterExpansion.getValue();
		if (expansionSearchText == null) {
			expansionSearchText = "";
		} else {
			expansionSearchText = expansionSearchText.toLowerCase();
		}
		
		// If there are no values in the filter controls, then it can be
		// considered that the data is no longer filtered.
		if (quickSearchText.isEmpty() && expansionSearchText.isEmpty()) {
			filterValuesExist = false;
		}
		
		match = (mtgCard.getName().toLowerCase().contains(quickSearchText) ||
				 mtgCard.getCardText().toLowerCase().contains(quickSearchText) ||
				 mtgCard.getFlavourText().toLowerCase().contains(quickSearchText));
		
		match = match &&
				(mtgCard.getExpansion().toLowerCase().contains(expansionSearchText));
		
		this.updateCardCount(filterValuesExist);
		
		//String lowerCaseFilterName = filterField.getText().toLowerCase();
		//String lowerCaseFilterExpansion = filterField2.getText().toLowerCase();
		
		//match = ((mtgCard.getName().toLowerCase().indexOf(lowerCaseFilterName) != -1) &&
		//		(mtgCard.getExpansion().toLowerCase().indexOf(lowerCaseFilterExpansion) != -1) &&
		//		(mtgCard.getManaCost().indexOf(filterCombo.getValue()) != -1));
		
		return match;
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
	
	/**
	 * Set the status bar text.
	 * 
	 * @param message - String
	 */
	private void setStatus(String message) {
		this.textStatus.setText(message);
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
		this.setCardCountMessage(this.loadCards(), false);
		this.setImageSize(Settings.IMAGE_SIZE_WIDTH_NORMAL, Settings.IMAGE_SIZE_HEIGHT_NORMAL);
		
		// If there is at least one card to display, select it and set the image.
		if (this.mtgCardList.size() > 0) {
			this.allCardsTableView.getSelectionModel().selectFirst();
			this.setCurrentCard(this.allCardsTableView.getSelectionModel().getSelectedItem());
		}
		
		// Instantiate the Timer Task which can be used by controls to initiate a current card count.
		this.cardCountTimer = new Timer();
		this.createNewTimerTask();
		
		// Tooltips.
		this.createTooltips();
	}
	
	/**
	 * Show or hide the Quick Search control.
	 */
	private void showOrHideQuickSearch() {
		if (this.quickSearchContainer.isVisible()) {
			this.quickSearchContainer.setManaged(false);
			this.quickSearchContainer.setVisible(false);
		} else {
			this.quickSearchContainer.setManaged(true);
			this.quickSearchContainer.setVisible(true);
			this.quickSearch.requestFocus();
		}
	}

	/**
	 * Show or hide the Card Filter control.
	 */
	private void showOrHideFilter() {
		if (this.filterContainer.isVisible()) {
			this.filterContainer.setManaged(false);
			this.filterContainer.setVisible(false);
		} else {
			this.filterContainer.setManaged(true);
			this.filterContainer.setVisible(true);
			this.filterContainer.requestFocus();
		}
	}
	

	/**
	 * Perform any clean-up tasks required. For example, closing down threads.
	 */
	public void shutdown() {
		this.timerTask.cancel();
		this.cardCountTimer.cancel();
	}

	/**
	 * Schedule a timer task to display the current card count.
	 * 
	 * @param filtered - boolean
	 */
	public void updateCardCount(boolean filtered) {
		if (!this.cardCountScheduled) {
			this.cardsCurrentlyFiltered = filtered;
			this.cardCountScheduled = true;
			this.cardCountTimer.purge();
			this.timerTask.cancel();
			this.createNewTimerTask();
			this.cardCountTimer.schedule(this.timerTask, 320);	
		}

		if (this.quickSearch.getText().isEmpty()) {
			this.buttonClearQuickSearch.setDisable(true);
		} else {
			this.buttonClearQuickSearch.setDisable(false);
		}
	}
}



/**
 * The following classes are Event Handler classes for different JavaFX controls.
 */

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
		if (event.getCode().getName().equalsIgnoreCase("up") || event.getCode().getName().equalsIgnoreCase("down") ||
				event.getCode().getName().equalsIgnoreCase("page up") || event.getCode().getName().equalsIgnoreCase("page down") ||
				event.getCode().getName().equalsIgnoreCase("end") || event.getCode().getName().equalsIgnoreCase("home"))
			this.controller.setCurrentCard(this.cardsTableView.getSelectionModel().getSelectedItem());
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
 * A TextField handler class for key (input) events.
 */
class TextFieldKeyEventHandler implements EventHandler<KeyEvent> {

	private MainViewController controller;
	
	// Constructor.
	public TextFieldKeyEventHandler(MainViewController controller) {
		this.controller = controller;
	}
	
	@Override
	public void handle(KeyEvent event) {
		if (!event.isAltDown() && !event.isControlDown()) {
			// TODO: DEBUG
	        LoggerUtil.logger(this).log(Level.INFO, String.format("DEBUG - Key pressed: %s", event.getText()));
	        // TODO: DEBUG
			//this.controller.updateCardCount(true);
		}
	}
}
