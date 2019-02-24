package thecollector.model.mtg.card;

/**
 * A class to represent an MTG card set version.
 * 
 * Based on MTGJSON version structure 4.2.1
 * https://mtgjson.com/files/version/
 * 
 * Changelog:
 * 24 Feb 2019 - Now using MTGJSON structures from 4.2.1.
 * 
 * @author Ian Claridge
 * 
 */
public class Version {
	private String date; // Date of the MTGJSON build e.g. "2018-12-18".
	private String version; // Version of the MTGJSON build e.g. "4.2.0".

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
}
