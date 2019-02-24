package thecollector.model.mtg.card;

/**
 * A class to represent foreign text data for an MTG card.
 * 
 * Based on MTGJSON version structure 4.2.1
 * https://mtgjson.com/structures/foreign-data/
 * 
 * Changelog:
 * 24 Feb 2019 - Now using MTGJSON structures from 4.2.1.
 * 
 * @author Ian Claridge
 * 
 */
public class ForeignData {
	private String flavorText; // Flavor text in foreign language e.g. "Schwester von Nicol Bolas. Überlebende des Krieges der Drachenältesten. Die bösartigste ihrer Art.".
	private String language; // Foreign language of card. Can be English, Spanish, French, German, Italian, Portuguese, Japanese, Korean, Russian, Simplified Chinese, or Traditional Chinese. Promo cards can be Hebrew, Latin, Ancient Greek, Arabic, Sanskrit, or Phyrexian.
	private Integer multiverseId; // Multiverse ID of the card e.g. 447637.
	private String name; // Name of the card in foreign language e.g. "Palladia-Mors, die Verwüsterin".
	private String text; // Rules text of the card in foreign language e.g. "Fliegend, Wachsamkeit, verursacht Trampelschaden\nPalladia-Mors, die Verwüsterin, hat Fluchsicherheit, falls sie noch keinen Schaden zugefügt hat.".
	private String type; // Type in foreign language e.g. "Legendäre Kreatur — Ältester, Drache".
	 
	public String getFlavorText() {
		return flavorText;
	}
	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
