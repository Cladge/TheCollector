package thecollector.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import thecollector.model.Settings;
import thecollector.model.TheCollector;
import thecollector.model.mtg.CardLoader;
import thecollector.model.mtg.card.MtgCard;

/**
 * The controller for the main application layout.
 * 
 * @author Ian Claridge
 */
public class MainView extends BaseView {
	
	private static TheCollector theCollector;

	@FXML
	private AnchorPane anchorpaneStartingView;

	@FXML
	private ListView<String> listViewAllCards;
	
	@FXML
	private Label labelStatus;
	
	/**
	 * Return reference to the main AnchorPane.
	 * 
	 * @return Entity AnchorPane.
	 */
	public AnchorPane getEntityPane () {
		return this.anchorpaneStartingView;
	}
	
	/**
	 * Main Initialise method.
	 * <li>Get cast handle of main app.
	 */
	public void initialise() {
		theCollector = (TheCollector) mainApp;
		this.labelStatus.setText("Loading card database...");
		this.loadCards();
		this.labelStatus.setText("Status: Ready");
	}
	
	/**
	 * Load the main card set.
	 * 
	 * @return int - Card Count
	 */
	private int loadCards() {
		int cardCount = 0;

		theCollector.getScene().setCursor(Cursor.WAIT);
		
        // Load the latest MTG collection.
        List<MtgCard> mtgCardList;
		try {
			mtgCardList = CardLoader.loadCards(theCollector.getSettingsDir() + "/" + Settings.MTG_JSON_SET);

			ObservableList<String> cardList = FXCollections.observableArrayList();
	        for (MtgCard mtgCard : mtgCardList) {
	        	String lineFormatted = "Card: " + mtgCard.getName() + ", Set: " + mtgCard.getSetName();
	        	cardList.add(lineFormatted);
				// System.out.println("Card: " + mtgCard.getName() + ", Set: " + mtgCard.getSetName());
			}
	        
	        // Populate the List View.
	        if (!cardList.isEmpty()) {
	        	this.listViewAllCards.setItems(cardList);
	        }
	        
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		theCollector.getScene().setCursor(Cursor.DEFAULT);
		
		return cardCount;
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
