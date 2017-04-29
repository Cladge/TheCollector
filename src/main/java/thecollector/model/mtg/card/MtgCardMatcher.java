package thecollector.model.mtg.card;

import java.util.Arrays;
import java.util.List;

/**
 * A class to perform a properties match on a given MTG card.
 * <p>
 * The types of properties to match on include:
 * <ul>
 * <li>"Quick search" text - text that can be searched for in the card's Name, Text and Flavour Text.</li>
 * <li>Expansion - e.g. "Mirrodin Besieged".</li>
 * <li>Card Type - e.g. "Creature"</li>
 * <li>Card Subtype - e.g. "Elemental"</li>
 * <li>Colour - e.g. "White"</li>
 * <li>Rarity - e.g. "Common"</li>
 * <li>CMC (Converted Mana Cost) - e.g. CMC >= 2</li>
 * <li>Power - e.g. Power >= 2</li>
 * <li>Toughness - e.g. Toughness >= 2</li>
 * </ul>
 * 
 * @author Ian Claridge
 */
public class MtgCardMatcher {
	
	private String quickSearchText;
	private String expansion;
	private String cardType;
	private String cardSubType;
	private String colour;
	private String rarity;
	private Integer cmc;
	private String powerToughness;
	private Integer power;
	private Integer toughness;
	private MtgCardDisplay mtgCard;

	// Constructor.
	/**
	 * 
	 * @param quickSearchText - String
	 * @param expansion - String
	 * @param cardType - String
	 * @param cardSubType - String
	 * @param colour - String
	 * @param rarity - String
	 * @param cmc - Integer
	 * @param powerToughness - String
	 * @param mtgCard - MtgCardDisplay
	 */
	public MtgCardMatcher(String quickSearchText, String expansion,
			String cardType, String cardSubType, String colour, String rarity,
			Integer cmc, String powerToughness, MtgCardDisplay mtgCard) {
		this.quickSearchText = quickSearchText;
		this.expansion = expansion;
		this.cardType = cardType;
		this.cardSubType = cardSubType;
		this.colour = colour;
		this.rarity = rarity;
		this.cmc = cmc;
		this.powerToughness = powerToughness;
		this.mtgCard = mtgCard;
		
		// Set up individual Power and Toughness values.
		this.power = null;
		this.toughness = null;
		
		if (this.powerToughnessHasValue()) {
			String[] powerToughnessItems = this.powerToughness.split("/");
			List<String> itemList = Arrays.asList(powerToughnessItems);
			if (itemList.size() == 2) {
				if (itemList.get(0) != "-") {
					this.power = new Integer(itemList.get(0));
				}
				if (itemList.get(1) != "-") {
					this.toughness = new Integer(itemList.get(1));
				}
			}
		}
	}
	
	/**
	 * Check if the card matches some or all of the criteria values.
	 * 
	 * @return boolean - card matches criteria
	 */
	public boolean cardMatches() {
		boolean match = false;
		
		String quickSearchTextLower = "";
		String expansionSearchTextLower = "";
		String cardTypeLower = "";
		String cardSubTypeLower = "";
		String colourLower = "";
		String rarityLower = "";
		
		if (this.quickSearchTextHasValue()) {
			quickSearchTextLower = this.quickSearchText.toLowerCase();
		}
		
		if (this.expansionHasValue()) {
			expansionSearchTextLower = this.expansion.toLowerCase();
		}
		
		if (this.cardTypeHasValue()) {
			cardTypeLower = this.cardType.toLowerCase();
		}

		if (this.cardSubTypeHasValue()) {
			cardSubTypeLower = this.cardSubType.toLowerCase();
		}
		
		match = (this.mtgCard.getName().toLowerCase().contains(quickSearchTextLower) ||
				 this.mtgCard.getCardText().toLowerCase().contains(quickSearchTextLower) ||
				 this.mtgCard.getFlavourText().toLowerCase().contains(quickSearchTextLower));
		
		match = match &&
				(this.mtgCard.getExpansion().toLowerCase().contains(expansionSearchTextLower));
		
		return match;
	}
	
	/**
	 * Check if at least one of the filter values has an actual value.
	 * 
	 * @return boolean - At least one filter values has a value.
	 */
	public boolean filterValuesExist() {
		// If there are no values in the filter fields, then it can be
		// considered that the data is no longer filtered.
		boolean filterValuesExist = (this.quickSearchTextHasValue() ||
									 this.expansionHasValue() ||
									 this.cardTypeHasValue() ||
									 this.cardSubTypeHasValue() ||
									 this.colourHasValue() ||
									 this.rarityHasValue() ||
									 this.cmcHasValue() ||
									 this.powerHasValue() ||
									 this.toughnessHasValue());
		
		return filterValuesExist;
	}
	
	/**
	 * Check contents of Quick Search Text.
	 * 
	 * @return boolean - Has value
	 */
	private boolean quickSearchTextHasValue() {
		return (this.quickSearchText != null && (!this.quickSearchText.isEmpty()));
	}

	/**
	 * Check contents of Expansion.
	 * 
	 * @return boolean - Has value
	 */
	private boolean expansionHasValue() {
		return (this.expansion != null && (!this.expansion.isEmpty()));
	}

	/**
	 * Check contents of Card Type.
	 * 
	 * @return boolean - Has value
	 */
	private boolean cardTypeHasValue() {
		return (this.cardType != null && (!this.cardType.isEmpty()));
	}

	/**
	 * Check contents of Card Subtype.
	 * 
	 * @return boolean - Has value
	 */
	private boolean cardSubTypeHasValue() {
		return (this.cardSubType != null && (!this.cardSubType.isEmpty()));
	}

	/**
	 * Check contents of Colour.
	 * 
	 * @return boolean - Has value
	 */
	private boolean colourHasValue() {
		return (this.colour != null && (!this.colour.isEmpty()));
	}

	/**
	 * Check contents of Rarity.
	 * 
	 * @return boolean - Has value
	 */
	private boolean rarityHasValue() {
		return (this.rarity != null && (!this.rarity.isEmpty()));
	}

	/**
	 * Check contents of CMC.
	 * 
	 * @return boolean - Has value
	 */
	private boolean cmcHasValue() {
		return (this.cmc != null);
	}
	
	/**
	 * Check contents of Power/Toughness.
	 * 
	 * @return boolean - Has value
	 */
	private boolean powerToughnessHasValue() {
		return (this.powerToughness != null && (!this.powerToughness.isEmpty()));
	}

	/**
	 * Check contents of Power.
	 * 
	 * @return boolean - Has value
	 */
	private boolean powerHasValue() {
		return (this.power != null);
	}

	/**
	 * Check contents of Toughness.
	 * 
	 * @return boolean - Has value
	 */
	private boolean toughnessHasValue() {
		return (this.toughness != null);
	}

}
