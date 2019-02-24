package thecollector.model.mtg.card;

/**
 * A class to represent a single Legality for an MTG card.
 * 
 * Based on MTGJSON legalities structure 4.2.1
 * https://mtgjson.com/structures/legalities/
 * 
 * Changelog:
 * 24 Feb 2019 - Now using MTGJSON structures from 4.2.1.
 * 
 * @author Ian Claridge
 * 
 */
public class Legality {
	private String playFormat; // Magic play formats. Can be 1v1, brawl, commander, duel, frontier, future, legacy, modern, oldschool, pauper, penny, standard, or vintage.
	private String Legality; // Can be Legal, Restricted, Banned, or Future.
	
	public String getPlayFormat() {
		return playFormat;
	}
	public void setPlayFormat(String playFormat) {
		this.playFormat = playFormat;
	}
	public String getLegality() {
		return Legality;
	}
	public void setLegality(String legality) {
		Legality = legality;
	}
}
