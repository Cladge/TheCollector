package thecollector.model.mtg.card;

import java.util.ArrayList;

/**
 * A class to represent an MTG card set.
 * 
 * @author Ian Claridge
 */
public class MtgSet {
	private String name;
	private String code;
	private String releaseDate;
	private String border;
	private String type;
	private String block;
	private String gathererCode;
	private ArrayList<MtgCard> cards;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public ArrayList<MtgCard> getCards() {
		return cards;
	}

	public void setCards(ArrayList<MtgCard> cards) {
		this.cards = cards;
	}

	public String getGathererCode() {
		return gathererCode;
	}

	public void setGathererCode(String gathererCode) {
		this.gathererCode = gathererCode;
	}
}
