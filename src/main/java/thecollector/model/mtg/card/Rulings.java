package thecollector.model.mtg.card;

/**
 * A class to represent a Ruling for an MTG card.
 * 
 * Based on MTGJSON rulings structure 4.2.1
 * https://mtgjson.com/structures/rulings/
 * 
 * Changelog:
 * 24 Feb 2019 - Now using MTGJSON structures from 4.2.1.
 * 24 Feb 2019 - Now using MTGJSON structures from 4.3.0.
 * 
 * @author Ian Claridge
 * 
 */
public class Rulings {
	private String date; // Date (OBDC standard) of ruling for the card e.g. "2018-07-13".
	private String text; // Text of ruling for the card e.g. "When Nicol Bolas’s enters-the-battlefield triggered ability resolves, first the next opponent in turn order (or, if it’s an opponent’s turn, that opponent) chooses a card in their hand without revealing it, then each other opponent in turn order does the same. Then all the chosen cards are discarded at the same time.".

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
