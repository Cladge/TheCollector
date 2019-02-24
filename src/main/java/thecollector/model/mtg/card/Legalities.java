package thecollector.model.mtg.card;

import java.util.ArrayList;

/**
 * A class to represent a collection of Legalities for an MTG card.
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
public class Legalities {
	private ArrayList<Legality> legality;

	public ArrayList<Legality> getLegality() {
		return legality;
	}

	public void setLegality(ArrayList<Legality> legality) {
		this.legality = legality;
	}
}
