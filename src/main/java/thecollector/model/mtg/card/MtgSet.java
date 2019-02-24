package thecollector.model.mtg.card;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A class to represent an MTG card set.
 * 
 * Based on MTGJSON set structure 4.2.1
 * https://mtgjson.com/structures/set/
 * 
 * Changelog:
 * 24 Feb 2019 - Now using MTGJSON structures from 4.2.1.
 * 24 Feb 2019 - Now using MTGJSON structures from 4.3.0.
 * 
 * @author Ian Claridge
 * 
 */
public class MtgSet {
	private Integer baseSetSize; // Number of cards in the set. Note that Wizards sometimes prints extra cards beyond the set size into promos or supplemental products. E.g. 280.
	private String block; // Block the set was in e.g. "Core Set 2019".
	private ArrayList<String> boosterV3; // Contents of a typical booster e.g. "boosterV3": [ [ "rare", "mythic rare" ], "uncommon", "uncommon", "uncommon", "common", "common", "common", "common", "common", "common", "common", "common", "common", "common", "land", "marketing" ].
	private ArrayList<MtgCard> cards; // List of cards. (See Card class.)
	private String code; // Set code for the set e.g. "M19".
	private String codeV3; // Alternate set code Wizards uses for a select few duel deck sets. (If there is not an alternate set code, this field is omitted.)
	private boolean isFoilOnly; // Set available only in foil. (If false, it is usually omitted.)
	private boolean isOnlineOnly; // Set available online only. Can be true or false. (If false, it is usually omitted.)
	private Version meta; // Keys are date and version. Date (OBDC standard) is date of build. Version is version of MTGJSON release. E.g. {"date": "2018-12-18","version": "4.2.0"}. (See Version class.)
	private String mtgoCode; // Set code for the set as it appears on Magic: The Gathering Online e.g. "M19".
	private String name; // Name of the set e.g. "Core Set 2019".
	private String releaseDate; // Date of release for the set e.g. "2018-07-13".
	private Integer tcgplayerGroupId; // Group ID of the set on TCGPlayer e.g. 2199.
	private ArrayList<Token> tokens; // List of tokens for the set. (See Token class.)
	private Integer totalSetSize; // Total number of cards in the set, including promos and related supplemental products e.g. 314.
	private String type; // Type of set. Can be core, expansion, masters, masterpiece, from_the_vault, spellbook, premium_deck, duel_deck, draft_innovation, treasure_chest, commander, planechase, archenemy, vanguard, funny, starter, box, promo, token, or memorabilia.
	
	public Integer getBaseSetSize() {
		return baseSetSize;
	}
	
	public void setBaseSetSize(Integer baseSetSize) {
		this.baseSetSize = baseSetSize;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public ArrayList<String> getBoosterV3() {
		return boosterV3;
	}

	@JsonIgnore
	public void setBoosterV3(ArrayList<String> boosterV3) {
		this.boosterV3 = boosterV3;
	}

	public ArrayList<MtgCard> getCards() {
		return cards;
	}

	public void setCards(ArrayList<MtgCard> cards) {
		this.cards = cards;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeV3() {
		return codeV3;
	}

	public void setCodeV3(String codeV3) {
		this.codeV3 = codeV3;
	}

	public boolean getIsFoilOnly() {
		return isFoilOnly;
	}

	public void setIsFoilOnly(boolean isFoilOnly) {
		this.isFoilOnly = isFoilOnly;
	}

	public boolean getIsOnlineOnly() {
		return isOnlineOnly;
	}

	public void setIsOnlineOnly(boolean isOnlineOnly) {
		this.isOnlineOnly = isOnlineOnly;
	}

	public Version getMeta() {
		return meta;
	}

	public void setMeta(Version meta) {
		this.meta = meta;
	}
	
	public String getMtgoCode() {
		return mtgoCode;
	}

	public void setMtgoCode(String mtgoCode) {
		this.mtgoCode = mtgoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public Integer getTcgplayerGroupId() {
		return tcgplayerGroupId;
	}

	public void setTcgplayerGroupId(Integer tcgplayerGroupId) {
		this.tcgplayerGroupId = tcgplayerGroupId;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}

	public Integer getTotalSetSize() {
		return totalSetSize;
	}

	public void setTotalSetSize(Integer totalSetSize) {
		this.totalSetSize = totalSetSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
