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
	private MtgOperator cmcOperator;
	private String powerToughness;
	private Integer power;
	private MtgOperator powerOperator;
	private Integer toughness;
	private MtgOperator toughnessOperator;
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
	 * @param cmcOperator - MtgOperator
	 * @param powerToughness - String
	 * @param powerOperator - MtgOperator
	 * @param toughnessOperator - MtgOperator
	 * @param mtgCard - MtgCardDisplay
	 */
	public MtgCardMatcher(String quickSearchText, String expansion,
			String cardType, String cardSubType, String colour, String rarity,
			Integer cmc, MtgOperator cmcOperator,
			String powerToughness, MtgOperator powerOperator, MtgOperator toughnessOperator,
			MtgCardDisplay mtgCard) {
		this.quickSearchText = quickSearchText;
		this.expansion = expansion;
		this.cardType = cardType;
		this.cardSubType = cardSubType;
		this.colour = colour;
		this.rarity = rarity;
		this.cmc = cmc;
		this.cmcOperator = cmcOperator;
		this.powerToughness = powerToughness;
		this.powerOperator = powerOperator;
		this.toughnessOperator = toughnessOperator;
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
	 * Check if the card matches one, some or all of the criteria values.
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
		
		if (this.colourHasValue()) {
			colourLower = this.colour.toLowerCase();
		}
		
		if (this.rarityHasValue()) {
			rarityLower = this.rarity.toLowerCase();
		}
		
		// Match on Quick Search: Name or Text (rules) or Flavour Text.
		match = (this.mtgCard.getName().toLowerCase().contains(quickSearchTextLower) ||
				 this.mtgCard.getCardText().toLowerCase().contains(quickSearchTextLower) ||
				 this.mtgCard.getFlavourText().toLowerCase().contains(quickSearchTextLower));
		
		// Match on Expansion.
		match = match &&
				(this.mtgCard.getExpansion().toLowerCase().contains(expansionSearchTextLower));
		
		// Match on Card Type and/or Sub Type.
		// Split the card's type into its constituent parts.
		String[] cardTypeItems = this.mtgCard.getType().split(" - ");
		boolean matchOnFirstType = false;
		if (cardTypeItems.length == 1) {
			matchOnFirstType = true;
		}
		
		if (matchOnFirstType) {
			match = match &&
					(cardTypeItems[0].toLowerCase().contains(cardTypeLower));
		} else {
			match = match &&
					(cardTypeItems[0].toLowerCase().contains(cardTypeLower) &&
					 cardTypeItems[1].toLowerCase().contains(cardSubTypeLower));
		}
		
		// Match on Colour.
		match = match &&
				(this.mtgCard.getColour().toLowerCase().contains(colourLower));
		
		// Match on Rarity.
		match = match &&
				(this.mtgCard.getRarity().toLowerCase().contains(rarityLower));
		
		// Match on CMC.
		if (this.cmcHasValue()) {
			if (this.cmcOperator.equals(MtgOperator.EQUALS)) {
				match = match &&
						(this.mtgCard.getCmc() == this.cmc.intValue());	
			}
			if (this.cmcOperator.equals(MtgOperator.LESS_THAN)) {
				match = match &&
						(this.mtgCard.getCmc() < this.cmc.intValue());	
			}
			if (this.cmcOperator.equals(MtgOperator.GREATER_THAN)) {
				match = match &&
						(this.mtgCard.getCmc() > this.cmc.intValue());	
			}
			if (this.cmcOperator.equals(MtgOperator.LESS_THAN_OR_EQUAL_TO)) {
				match = match &&
						(this.mtgCard.getCmc() <= this.cmc.intValue());	
			}
			if (this.cmcOperator.equals(MtgOperator.GREATER_THAN_OR_EQUAL_TO)) {
				match = match &&
						(this.mtgCard.getCmc() >= this.cmc.intValue());	
			}
		}
		
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
