package thecollector.utils;

import java.util.Properties;

public class AppProperties extends Properties {
	
	private String cardDataBase;

	/**
	 * Constructor.
	 * 
	 * @param cardDataBase
	 */
	public AppProperties(String cardDataBase) {
		this.setCardDataBase(cardDataBase);
	}

	public String getCardDataBase() {
		return cardDataBase;
	}

	public void setCardDataBase(String cardDataBase) {
		this.cardDataBase = cardDataBase;
		this.setProperty("cardDataBase", this.cardDataBase);
	}
	
}
