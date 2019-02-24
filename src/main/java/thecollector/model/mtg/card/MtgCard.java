package thecollector.model.mtg.card;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A class to represent an MTG card.
 * 
 * @author Ian Claridge
 * 
 * Based on MTGJSON card structure 4.2.1
 * https://mtgjson.com/structures/card/
 * 
 * Changelog:
 * 24 Feb 2019 - Now using MTGJSON structures from 4.2.1.
 * 
 */
public class MtgCard {
	private String artist; // Name of artist e.g. "Svetlin Velinov".
	private String borderColor; // Color of the border. Can be black, borderless, gold, silver, or white.
	private ArrayList<String> colorIdentity; // List of all colors in card’s mana cost, rules text and any color indicator e.g. ["B","R","U"].
	private ArrayList<String> colorIndicator; // List of all colors in card’s color indicator (a symbol showing the colors of the card). Usually found only on cards without mana costs and other special cards.
	private ArrayList<String> colors; // List of all colors in card’s mana cost and any color indicator. Some cards are special (such as Devoid cards or other cards with certain rules text). E.g. ["B","R","U"].
	private float convertedManaCost; // The converted mana cost of the card e.g. 4.0.
	private String duelDeck; // If the card is in a duel deck product, can be a or b. (If not in duel deck product, duelDeck is usually ommitted.)
	private float faceConvertedManaCost; // The converted mana cost of the face (half, or part) of the card e.g. 4.0.
	private String flavorText; // Italicized text found below the rules text that has no game function e.g. 'Whatever hatred destroys, a single act of trust can revive.'.
	private ArrayList<ForeignData> foreignData; // All foreign versions of text data. (See ForeignData class.)
	private String frameEffect; // Effect found on the card frame style. Can be legendary, miracle, nyxtouched, draft, devoid, tombstone, colorshifted, sunmoondfc, compasslanddfc, originpwdfc, or mooneldrazidfc. (If none, it is usually omitted.)
	private String frameVersion; // Version of the card frame style. Can be 1993, 1997, 2003, 2015, or future.
	private String hand; // Starting maximum hand size total modifier. A plus or minus character preceeds an integer. Used only on Vanguard cards. E.g. "+0".
	private boolean hasFoil; // Can the card be found in foil? Can be true or false. (If false, it is usually omitted.)
	private boolean hasNonFoil; // Can the card be found in non-foil? Can be true or false. (If false, it is usually omitted.)
	private boolean isAlternative; // Is the card a "secret foil" in the set? This is a feature found in sets such as UNH, 10E, CN2, BBD, and PLS. Can be true or false. (If false, it is usually omitted.)
	private boolean isFoilOnly; // Can the card only be found in foil? Can be true or false. (If false, it is usually omitted.)
	private boolean isOnlineOnly; // Is the card only available online? Can be true or false. (If false, it is usually omitted.)
	private boolean isOversized; // Is the card oversized? Can be true or false. (If false, it is usually omitted.)
	private boolean isReserved; // Is the card on the Reserved List? Can be true or false. (If false, isReserved is usually omitted.)
	private boolean isTimeshifted; // Card is “timeshifted”, a feature from Time Spiral block. Can be true or false. (If false, it is usually omitted.)
	private String layout; // Type of card. Can be normal, split, flip, transform, meld, leveler, saga, planar, scheme, vanguard, token, double_faced_token, emblem, augment, or host. (If normal, it is usually omitted.)
	private ArrayList<Object> legalities; // Keys are Magic play formats. Can be 1v1, brawl, commander, duel, frontier, future, legacy, modern, oldschool, pauper, penny, standard, or vintage. Values can be Legal, Restricted, Banned, or Future. (“Future” is used for a revision of the format in which the card will be legal soon. If the format is not listed, it is assumed the card is not legal in that format.)
	private String life; // Starting life total modifier. A plus or minus character preceeds an integer. Used only on Vanguard cards. E.g. "+0"
	private String loyalty; // Planeswalker loyalty value e.g. "7".
	private String manaCost; // Mana cost of the card e.g. "{1}{U}{B}{R}".
	private Integer multiverseId; // An integer most cards have which Wizards uses as a card identifier e.g. 447354.
	private String name; // Name of the card. (If the card is in an Un-set and has multiple printings, a space and letter enclosed in parentheses, starting with (b), follows the name.) E.g. "Nicol Bolas, the Ravager".
	private ArrayList<String> names; // Names of each face on the card. Meld cards are listed in the order of CardA, Meld, CardB. E.g. ["Nicol Bolas, the Ravager","Nicol Bolas, the Arisen"].
	private String number; // Number of the card e.g. "218".
	private String originalText; // Text on the card as originally printed e.g. "Flying\r\nWhen Nicol Bolas, the Ravager enters the battlefield, each opponent discards a card.\r\n{4}{U}{B}{R}: Exile Nicol Bolas, the Ravager, then return him to the battlefield transformed under his owner's control. Activate this ability only any time you could cast a sorcery."
	private String originalType; // Type as originally printed. Includes any supertypes and subtypes. E.g. "Legendary Creature — Elder Dragon".
	private ArrayList<String> printings; // List of sets the card was printed in, in uppercase, e.g. ["M19","PM19"].
	private String power; // Power of the creature e.g. "4".
	private String rarity; // Rarity. Can be basic, common, uncommon, rare, or mythic.
	private ArrayList<Rulings> rulings; // All the rulings for this card. (See Rulings class.)
	private String scryfallId; // A universal unique id (v4) generated by Scryfall. Note that cards with multiple faces are not unique. E.g. "7b215968-93a6-4278-ac61-4e3e8c3c3943".
	private String side; // Identifier of the side. Used on cards with multiple faces, such as flip, split, transform cards. Can be a, b, or c.
	private boolean starter; // Is the card only not found in a booster pack? Can be true or false. (If false, it is usually omitted.) Will be changed to isStarter in 4.2.2.
	private ArrayList<String> subtypes; // List of card subtypes found after em-dash e.g. ["Elder","Dragon"].
	private ArrayList<String> supertypes; // List of card supertypes found before em-dash e.g. ["Legendary"].
	private Integer tcgplayerProductId; // Numeric identifier for the card for TCGPlayer e.g. 168451.
	private String tcgplayerPurchaseUrl; // URL which redirects to TCGPlayer website’s card page e.g. "https://mtgjson.com/links/c9231e9296c7917d".
	private String text; // Rules text of the card e.g. "Flying\nWhen Nicol Bolas, the Ravager enters the battlefield, each opponent discards a card.\n{4}{U}{B}{R}: Exile Nicol Bolas, the Ravager, then return him to the battlefield transformed under his owner's control. Activate this ability only any time you could cast a sorcery.".
	private String toughness; // Toughness of the card e.g. "4".
	private String type; // Type of the card. Includes any supertypes and subtypes. E.g. "Legendary Creature — Elder Dragon".
	private ArrayList<String> types; // List of types of the card e.g. ["Creature"].
	private String uuid; // A universal unique id (v5) generated by MTGJSON. Each entry is unique. E.g. "3e429ea6-ba18-5c2f-ab17-66fff0820ef2".
	private ArrayList<String> variations; // uuid field of cards with alternate printings with the same set code (excluding Un-sets).
	private String watermark; // Name of the watermark on the card. Can be one of many different values, including a guild name, clan name, or wotc for the shooting star. (If there isn’t one, it can be an empty string, but it is usually omitted.)

	// Not part of JSON, needs to be set later.
	private String setCode;
	private String expansion;
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public ArrayList<String> getColorIdentity() {
		return colorIdentity;
	}

	public void setColorIdentity(ArrayList<String> colorIdentity) {
		this.colorIdentity = colorIdentity;
	}

	public ArrayList<String> getColorIndicator() {
		return colorIndicator;
	}

	public void setColorIndicator(ArrayList<String> colorIndicator) {
		this.colorIndicator = colorIndicator;
	}

	public ArrayList<String> getColors() {
		return colors;
	}

	public void setColors(ArrayList<String> colors) {
		this.colors = colors;
	}

	public float getConvertedManaCost() {
		return convertedManaCost;
	}

	public void setConvertedManaCost(float convertedManaCost) {
		this.convertedManaCost = convertedManaCost;
	}

	public String getDuelDeck() {
		return duelDeck;
	}

	public void setDuelDeck(String duelDeck) {
		this.duelDeck = duelDeck;
	}

	public float getFaceConvertedManaCost() {
		return faceConvertedManaCost;
	}

	public void setFaceConvertedManaCost(float faceConvertedManaCost) {
		this.faceConvertedManaCost = faceConvertedManaCost;
	}

	public String getFlavorText() {
		return flavorText;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	public ArrayList<ForeignData> getForeignData() {
		return foreignData;
	}

	public void setForeignData(ArrayList<ForeignData> foreignData) {
		this.foreignData = foreignData;
	}

	public String getFrameEffect() {
		return frameEffect;
	}

	public void setFrameEffect(String frameEffect) {
		this.frameEffect = frameEffect;
	}

	public String getFrameVersion() {
		return frameVersion;
	}

	public void setFrameVersion(String frameVersion) {
		this.frameVersion = frameVersion;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public boolean isHasFoil() {
		return hasFoil;
	}

	public void setHasFoil(boolean hasFoil) {
		this.hasFoil = hasFoil;
	}

	public boolean isHasNonFoil() {
		return hasNonFoil;
	}

	public void setHasNonFoil(boolean hasNonFoil) {
		this.hasNonFoil = hasNonFoil;
	}

	public boolean isAlternative() {
		return isAlternative;
	}

	public void setAlternative(boolean isAlternative) {
		this.isAlternative = isAlternative;
	}

	public boolean isFoilOnly() {
		return isFoilOnly;
	}

	public void setFoilOnly(boolean isFoilOnly) {
		this.isFoilOnly = isFoilOnly;
	}

	public boolean isOnlineOnly() {
		return isOnlineOnly;
	}

	public void setOnlineOnly(boolean isOnlineOnly) {
		this.isOnlineOnly = isOnlineOnly;
	}

	public boolean isOversized() {
		return isOversized;
	}

	public void setOversized(boolean isOversized) {
		this.isOversized = isOversized;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public boolean isTimeshifted() {
		return isTimeshifted;
	}

	public void setTimeshifted(boolean isTimeshifted) {
		this.isTimeshifted = isTimeshifted;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public ArrayList<Object> getLegalities() {
		return legalities;
	}

	@JsonIgnore
	public void setLegalities(ArrayList<Object> legalities) {
		this.legalities = legalities;
	}

	public String getLife() {
		return life;
	}

	public void setLife(String life) {
		this.life = life;
	}

	public String getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(String loyalty) {
		this.loyalty = loyalty;
	}

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}

	public Integer getMultiverseId() {
		return multiverseId;
	}

	public void setMultiverseId(Integer multiverseId) {
		this.multiverseId = multiverseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalType() {
		return originalType;
	}

	public void setOriginalType(String originalType) {
		this.originalType = originalType;
	}

	public ArrayList<String> getPrintings() {
		return printings;
	}

	public void setPrintings(ArrayList<String> printings) {
		this.printings = printings;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public ArrayList<Rulings> getRulings() {
		return rulings;
	}

	public void setRulings(ArrayList<Rulings> rulings) {
		this.rulings = rulings;
	}

	public String getScryfallId() {
		return scryfallId;
	}

	public void setScryfallId(String scryfallId) {
		this.scryfallId = scryfallId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public boolean isStarter() {
		return starter;
	}

	public void setStarter(boolean starter) {
		this.starter = starter;
	}

	public ArrayList<String> getSubtypes() {
		return subtypes;
	}

	public void setSubtypes(ArrayList<String> subtypes) {
		this.subtypes = subtypes;
	}

	public ArrayList<String> getSupertypes() {
		return supertypes;
	}

	public void setSupertypes(ArrayList<String> supertypes) {
		this.supertypes = supertypes;
	}

	public Integer getTcgplayerProductId() {
		return tcgplayerProductId;
	}

	public void setTcgplayerProductId(Integer tcgplayerProductId) {
		this.tcgplayerProductId = tcgplayerProductId;
	}

	public String getTcgplayerPurchaseUrl() {
		return tcgplayerPurchaseUrl;
	}

	public void setTcgplayerPurchaseUrl(String tcgplayerPurchaseUrl) {
		this.tcgplayerPurchaseUrl = tcgplayerPurchaseUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToughness() {
		return toughness;
	}

	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ArrayList<String> getVariations() {
		return variations;
	}

	public void setVariations(ArrayList<String> variations) {
		this.variations = variations;
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	// ======================================================================
	// The following are custom methods used for getting card data
	// in a required format.
	// ======================================================================
	
	public String getSetCode() {
		return setCode;
	}

	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}

	/**
	 * NB: This method returns a formatted string of the card's Types AND Subtypes, e.g.:
	 * <br>
	 * <br>
	 * <b>"Artifact Creature - Cat Ally"</b>
	 * 
	 * @return String
	 */
	public String getAllTypesFormatted() {
		return type;
	}

	/**
	 * Special helper method to retrieve all elements in the types array, each separated by a space.
	 * 
	 * @return String
	 */
	public String getTypesFormatted() {
		StringBuilder types = new StringBuilder("");
		if (this.types != null && this.types.size() > 0) {
			for (String type : this.types) {
				types.append(type);
				types.append(" ");
			}
		}
		
		return types.toString().trim();
	}

	/**
	 * Special helper method to retrieve all elements in the subtypes array, each separated by a space.
	 * 
	 * @return String
	 */
	public String getSubtypesFormatted() {
		StringBuilder subtypes = new StringBuilder("");
		if (this.subtypes != null && this.subtypes.size() > 0) {
			for (String subtype : this.subtypes) {
				subtypes.append(subtype);
				subtypes.append(" ");
			}
		}
		
		return subtypes.toString().trim();
	}
}
