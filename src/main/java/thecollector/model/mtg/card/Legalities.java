package thecollector.model.mtg.card;

/**
 * A class to represent a collection of Legalities for an MTG card.
 * 
 * Based on MTGJSON legalities structure 4.2.1
 * https://mtgjson.com/structures/legalities/
 * 
 * Changelog:
 * 24 Feb 2019 - Now using MTGJSON structures from 4.2.1.
 * 24 Feb 2019 - Now using MTGJSON structures from 4.3.0.
 * 
 * @author Ian Claridge
 * 
 */
public class Legalities {
	private String commander; // Card legality in the Commander play format e.g. "Legal".
	private String duel; // Card legality in the Duel Commander play format e.g. "Banned".
	private String frontier; // (Omitted.) Card not legal in the Standard play format.
	private String legacy; // Card legality in the Legacy play format e.g. "Banned".
	private String modern; // (Omitted.) Card not legal in the Standard play format.
	private String pauper; // (Omitted.) Card not legal in the Pauper play format.
	private String penny; // (Omitted.) Card not legal in the Penny play format.
	private String standard; // (Omitted.) Card not legal in the Standard play format.
	private String vintage; // Card legality in the Vintage play format e.g. "Restricted".
	
	public String getCommander() {
		return commander;
	}
	public void setCommander(String commander) {
		this.commander = commander;
	}
	public String getDuel() {
		return duel;
	}
	public void setDuel(String duel) {
		this.duel = duel;
	}
	public String getFrontier() {
		return frontier;
	}
	public void setFrontier(String frontier) {
		this.frontier = frontier;
	}
	public String getLegacy() {
		return legacy;
	}
	public void setLegacy(String legacy) {
		this.legacy = legacy;
	}
	public String getModern() {
		return modern;
	}
	public void setModern(String modern) {
		this.modern = modern;
	}
	public String getPauper() {
		return pauper;
	}
	public void setPauper(String pauper) {
		this.pauper = pauper;
	}
	public String getPenny() {
		return penny;
	}
	public void setPenny(String penny) {
		this.penny = penny;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getVintage() {
		return vintage;
	}
	public void setVintage(String vintage) {
		this.vintage = vintage;
	}
}
