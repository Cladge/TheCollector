package unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import thecollector.model.mtg.card.MtgCardDetailsHtmlGenerator;
import thecollector.model.mtg.card.MtgCardDisplay;

/**
 * Test class for {@code MtgCardDetailsHtmlGenerator}.
 * 
 * <p>
 * Expectations:
 * <ul>
 * <li>The HTML generator returns expected format for given card details.</li>
 * </ul>
 * 
 * @author Ian Claridge
 */
public class TestMtgCardDetailsHtmlGenerator {

	private MtgCardDisplay testCardDisplay01 = new MtgCardDisplay();
	private MtgCardDisplay testCardDisplay02 = new MtgCardDisplay();
	
	private MtgCardDetailsHtmlGenerator mtgCardDetailsHtmlGenerator01 = new MtgCardDetailsHtmlGenerator(this.testCardDisplay01);
	private MtgCardDetailsHtmlGenerator mtgCardDetailsHtmlGenerator02 = new MtgCardDetailsHtmlGenerator(this.testCardDisplay02);
	
	@Before
	public void setUp() throws Exception {
		
		this.testCardDisplay01.setName("Air Elemental");
		this.testCardDisplay01.setExpansion("Limited Edition Alpha");
		this.testCardDisplay01.setType("Creature - Elemental");
		this.testCardDisplay01.setColour("Blue");
		this.testCardDisplay01.setRarity("Rare");
		this.testCardDisplay01.setMultiverseId("10002000");
		this.testCardDisplay01.setFlavourText("These spirits of the air are winsome and wild, and cannot be truly contained. Only marginally intelligent, they often substitute whimsy for strategy, delighting in mischief and mayhem.");

		this.testCardDisplay02.setName("Paradise Plume");
		this.testCardDisplay02.setExpansion("Time Spiral");
		this.testCardDisplay02.setType("Artifact");
		this.testCardDisplay02.setColour("-");
		this.testCardDisplay02.setRarity("Uncommon");
		this.testCardDisplay02.setMultiverseId("20003000");
		this.testCardDisplay02.setFlavourText("A last wisp of paradise in a fallen world.");
		
	}

	@Test
	public void testHtmlContent() {
		String htmlContent01 = "test";
		String htmlContent02 = "test";
		
		assertEquals(htmlContent01, this.mtgCardDetailsHtmlGenerator01.getContent());
		assertEquals(htmlContent02, this.mtgCardDetailsHtmlGenerator02.getContent());
	}
}
