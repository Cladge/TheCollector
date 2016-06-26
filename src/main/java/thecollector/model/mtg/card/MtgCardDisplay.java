package thecollector.model.mtg.card;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A class to represent the displayable version of an MTG card, for example
 * to populate a TableView control.
 * 
 * @author Ian Claridge
 */
public class MtgCardDisplay {
	private final StringProperty name;
	private final StringProperty manaCost;
	private final StringProperty type;
	private final StringProperty cardText;
	private final StringProperty powerToughness;
	private final StringProperty expansion;
	private final StringProperty rarity;
	private final StringProperty colour;
	private final StringProperty multiverseId;
	private final StringProperty flavourText;
	
	// Constructor.
	// Initialise the member properties.
	public MtgCardDisplay() {
		this.name = new SimpleStringProperty("");
		this.manaCost = new SimpleStringProperty("");
		this.type = new SimpleStringProperty("");
		this.cardText = new SimpleStringProperty("");
		this.powerToughness = new SimpleStringProperty("");
		this.expansion = new SimpleStringProperty("");
		this.rarity = new SimpleStringProperty("");
		this.colour = new SimpleStringProperty("");
		this.multiverseId = new SimpleStringProperty("");
		this.flavourText = new SimpleStringProperty("");
	}

	// Getter and setter methods.
	//
	// As the member variables are properties, they must be set as the relevant property type, e.g. StringProperty.
	
	public String getName() {
		return this.name.get();
	}
	
	public StringProperty getNameProperty() {
		return this.name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getManaCost() {
		return this.manaCost.get();
	}

	public StringProperty getManaCostProperty() {
		return this.manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost.set(manaCost);
	}

	public String getExpansion() {
		return this.expansion.get();
	}
	
	public StringProperty getExpansionProperty() {
		return this.expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion.set(expansion);
	}

	public String getType() {
		return this.type.get();
	}

	public StringProperty getTypeProperty() {
		return this.type;
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public String getCardText() {
		return this.cardText.get();
	}

	public StringProperty getCardTextProperty() {
		return this.cardText;
	}

	public void setCardText(String text) {
		this.cardText.set(text);
	}

	public String getPowerToughness() {
		return this.powerToughness.get();
	}

	public StringProperty getPowerToughnessProperty() {
		return this.powerToughness;
	}

	public void setPowerToughness(String powerToughness) {
		this.powerToughness.set(powerToughness);
	}

	public String getColour() {
		return this.colour.get();
	}
	
	public StringProperty getColourProperty() {
		return this.colour;
	}

	public void setColour(String colour) {
		this.colour.set(colour);
	}

	public String getRarity() {
		return this.rarity.get();
	}
	
	public StringProperty getRarityProperty() {
		return this.rarity;
	}

	public void setRarity(String rarity) {
		this.rarity.set(rarity);
	}

	public String getMultiverseId() {
		return this.multiverseId.get();
	}
	
	public StringProperty getMultiverseIdProperty() {
		return this.multiverseId;
	}

	public void setMultiverseId(String multiverseId) {
		this.multiverseId.set(multiverseId);
	}

	public String getFlavourText() {
		return this.flavourText.get();
	}

	public StringProperty getFlavourTextProperty() {
		return this.flavourText;
	}

	public void setFlavourText(String flavourText) {
		this.flavourText.set(flavourText);
	}

	// Overrides
	public String toString() {
		return this.getName() + ", " + this.getExpansion() + ", " + this.getType() + ", " + this.getColour() + ", " + this.getRarity() + ", " + this.getMultiverseId();
	}
	
	/**
	 * Implement an equality method. This overrides the standard equals() method.
	 * <p>
	 * Two MtgCardDisplay objects are considered equal if their Multiverse ID values are the same.
	 */
	public boolean equals(Object compareObject) {
		boolean returnValue = true;
		
		if (compareObject == null) {
			returnValue = false;
		} else if (!(compareObject instanceof MtgCardDisplay)) {
			returnValue = false;
		} else {
			// MtgCardDisplay objects are considered equal if they have the same Multiverse ID.
			MtgCardDisplay compareMtgCardDisplay = (MtgCardDisplay) compareObject;
			if (!this.getMultiverseId().equals(compareMtgCardDisplay.getMultiverseId())) {
				returnValue = false;
			}
		}
		
		return returnValue;
	}
	
	/**
	 * Implement a hashcode method. This overrides the standard hashCode() method.
	 * <p>
	 * An MtgCardDisplay objects is uniquely identified by its Multiverse ID.
	 */
	public int hashCode() {
		int multiverseId = Integer.valueOf(this.getMultiverseId());
		
		return multiverseId;
	}

}
