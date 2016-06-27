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
		this.createContent();
	}
	
	/**
	 * Create HTML content based on the given card details.
	 */
	private void createContent() {
		/**
		 * <title></title>
<h2>Card Details</h2>

<p><span style="font-size:14px;"><strong>Name:</strong> &lt;name&gt;</span><br style="line-height: 20.8px;" />
<strong style="line-height: 20.8px; font-size: 14px;">Expansion:</strong><span style="line-height: 20.8px; font-size: 14px;">&nbsp;&lt;expansion&gt;</span><br style="line-height: 20.8px;" />
<strong style="line-height: 20.8px; font-size: 14px;">Card Type:</strong><span style="line-height: 20.8px; font-size: 14px;">&nbsp;&lt;type&gt; - &lt;sub type&gt;<br />
<strong style="line-height: 20.8px; font-size: 14px;">Colour:</strong><span style="line-height: 20.8px; font-size: 14px;">&nbsp;&lt;colour&gt;</span></span><br style="font-size: 14px; line-height: 20.8px;" />
<strong style="font-size: 14px; line-height: 20.8px;">Rarity:</strong><span style="line-height: 20.8px; font-size: 14px;">&nbsp;&lt;rarity&gt;​<br />
<span style="line-height: 20.8px; font-size: 14px;"><strong style="font-size: 14px; line-height: 20.8px;">Mana Cost:</strong><span style="font-size: 14px; line-height: 20.8px;">&nbsp;&lt;mana&gt;</span><br />
<span style="font-size: 14px; line-height: 20.8px;">​</span><strong style="font-size: 14px; line-height: 20.8px;">Card Text:</strong><span style="font-size: 14px; line-height: 20.8px;">&nbsp;&lt;text&gt;</span><br />
<strong style="line-height: 20.8px; font-size: 14px;">Power/Toughness:</strong><span style="line-height: 20.8px; font-size: 14px;">&nbsp;&lt;power&gt;&lt;toughness&gt;</span><br />
<strong style="line-height: 20.8px; font-size: 14px;">Flavour Text:</strong></span></span></p>

<table align="left" border="1" cellpadding="1" cellspacing="1" width="100%">
	<tbody>
		<tr>
			<td>&lt;Flavour Text&gt;</td>
		</tr>
	</tbody>
</table>

		 */
		this.htmlContent.append("<h3>Card Details</h3>");
		this.htmlContent.append("<p><span style='font-size:14px;'>");
		this.htmlContent.append(String.format("<strong>Name:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getName()));
		this.htmlContent.append(String.format("<strong>Expansion:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getExpansion()));
		this.htmlContent.append(String.format("<strong>Card Type:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getType()));
		this.htmlContent.append(String.format("<strong>Colour:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getColour()));
		this.htmlContent.append(String.format("<strong>Rarity:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getRarity()));
		this.htmlContent.append(String.format("<strong>Mana Cost:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getManaCost()));
		this.htmlContent.append(String.format("<strong>Card Text:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getCardText()));
		this.htmlContent.append(String.format("<strong>Power/Toughness:</strong>&nbsp;%s<br/>", this.mtgCardDisplay.getPowerToughness()));
		this.htmlContent.append("<strong>Flavour Text:</strong><br/>");
		this.htmlContent.append("</span>");
		this.htmlContent.append("<table align='left' border='1' cellpadding='1' cellspacing='1' width='100%'><tbody>");
		this.htmlContent.append(String.format("<tr><td style='font-size:14px; font-style: italic;'>%s</td></tr>", this.mtgCardDisplay.getFlavourText()));
		this.htmlContent.append("</tbody></table></p>");
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
