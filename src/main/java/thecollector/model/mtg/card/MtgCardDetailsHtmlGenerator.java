package thecollector.model.mtg.card;

/**
 * A class to generate the HTML for an MTG card details.
 * 
 * @author Ian Claridge
 */
public class MtgCardDetailsHtmlGenerator {
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
	}
	
	/**
	 * Add content to the HTML content.
	 * 
	 * @param content - String
	 */
	public void addContent(String content) {
		this.htmlContent.append(content);
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
