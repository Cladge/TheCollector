package thecollector.model.mtg.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to generate the HTML for an MTG card details.
 * 
 * @author Ian Claridge
 */
public class MtgCardDetailsHtmlGenerator {

	private static final String QUERY_DATA = "?size=small&name=%s&type=symbol";
	private static final Map<String, String> SYMBOL_MAPPINGS = Collections.unmodifiableMap(
			new HashMap<String, String>() {
				private static final long serialVersionUID = 7762009681861411625L;
			{
				put("{W}", "W");
				put("{U}", "U");
				put("{B}", "B");
				put("{R}", "R");
				put("{G}", "G");
				put("{T}", "tap");
				put("{0}", "0");
				put("{1}", "1");
				put("{2}", "2");
				put("{3}", "3");
				put("{4}", "4");
				put("{5}", "5");
				put("{6}", "6");
				put("{7}", "7");
				put("{8}", "8");
				put("{9}", "9");
			}});
	
	private MtgCardDisplay mtgCardDisplay;
	private StringBuilder htmlContent;

	/**
	 * Constructor.
	 * 
	 * @param mtgCardDisplay - MtgCardDisplay
	 */
	public MtgCardDetailsHtmlGenerator(MtgCardDisplay mtgCardDisplay) {
		this.mtgCardDisplay = mtgCardDisplay;
		this.htmlContent = new StringBuilder("");
		this.createContent();
	}
	
	/**
	 * Create HTML content based on the given card details.
	 */
	private void createContent() {
		this.htmlContent.append("<h3>Card Details</h3>");
		this.htmlContent.append("<p><span style='font-size:14px;'>");
		this.htmlContent.append(String.format("<strong>Name:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getName()));
		this.htmlContent.append(String.format("<strong>Expansion:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getExpansion()));
		this.htmlContent.append(String.format("<strong>Card Type:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getType()));
		this.htmlContent.append(String.format("<strong>Colour:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getColour()));
		this.htmlContent.append(String.format("<strong>Rarity:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getRarity()));
		this.htmlContent.append(String.format("<strong>Mana Cost:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getManaCost()));
		this.htmlContent.append("<strong>Card Text:</strong><br/>");
		this.htmlContent.append("</span>");
		this.htmlContent.append("<table align='left' border='1' cellpadding='1' cellspacing='1' width='100%'><tbody>");
		this.htmlContent.append(String.format("<tr><td style='font-size:14px;'>%s</td></tr>", this.mtgCardDisplay.getCardText()));
		this.htmlContent.append("</tbody></table></p>");
		this.htmlContent.append("<p><span style='font-size:14px;'>");
		this.htmlContent.append(String.format("<strong>Power/Toughness:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getPowerToughness()));
		this.htmlContent.append("<strong>Flavour Text:</strong><br/>");
		this.htmlContent.append("</span>");
		this.htmlContent.append("<table align='left' border='1' cellpadding='1' cellspacing='1' width='100%'><tbody>");
		this.htmlContent.append(String.format("<tr><td style='font-size:14px; font-style: italic;'>%s</td></tr>", this.mtgCardDisplay.getFlavourText()));
		this.htmlContent.append("</tbody></table></p>");
	}
	
	/**
	 * Parse the passed text, looking for symbols that can be translated
	 * to correct parameters to pass to the Gatherer URL.
	 * 
	 * @param textToParse - String
	 * 
	 * @return String
	 */
	private String parseSymbols(String textToParse) {
		String parsedText = null;
		
		return parsedText;
	}
	
	/**
	 * Return the formatted HTML content.
	 * 
	 * @return String
	 */
	public String getContent() {
		return this.htmlContent.toString();
	}
}
