package unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import thecollector.model.mtg.card.MtgCardDisplay;

/**
 * Test class for MtgCardDisplay.
 * 
 * @author Ian Claridge
 */
public class TestMtgCardDisplay {

	MtgCardDisplay testCardDisplay01 = new MtgCardDisplay();
	MtgCardDisplay testCardDisplay02 = new MtgCardDisplay();
	
	@Before
	public void setUp() throws Exception {
		
		this.testCardDisplay01.setName("Air Elemental");
		this.testCardDisplay01.setSetName("Limited Edition Alpha");
		this.testCardDisplay01.setType("Creature - Elemental");
		this.testCardDisplay01.setColour("Blue");
		this.testCardDisplay01.setRarity("Rare");
		this.testCardDisplay01.setMultiverseId("10002000");

		this.testCardDisplay02.setName("Paradise Plume");
		this.testCardDisplay02.setSetName("Time Spiral");
		this.testCardDisplay02.setType("Artifact");
		this.testCardDisplay02.setColour("-");
		this.testCardDisplay02.setRarity("Uncommon");
		this.testCardDisplay02.setMultiverseId("20003000");
	}

	@Test
	public void testValues() {
		assertEquals("Get Card Name", "Air Elemental", this.testCardDisplay01.getName());
		assertEquals("Get Set Name", "Limited Edition Alpha", this.testCardDisplay01.getSetName());
		assertEquals("Get Type", "Creature - Elemental", this.testCardDisplay01.getType());
		assertEquals("Get Type", "Blue", this.testCardDisplay01.getColour());
		assertEquals("Get Rarity", "Rare", this.testCardDisplay01.getRarity());
		assertEquals("Get Multiverse ID", "10002000", this.testCardDisplay01.getMultiverseId());

		assertEquals("Get Card Name", "Paradise Plume", this.testCardDisplay02.getName());
		assertEquals("Get Set Name", "Time Spiral", this.testCardDisplay02.getSetName());
		assertEquals("Get Type", "Artifact", this.testCardDisplay02.getType());
		assertEquals("Get Type", "-", this.testCardDisplay02.getColour());
		assertEquals("Get Rarity", "Uncommon", this.testCardDisplay02.getRarity());
		assertEquals("Get Multiverse ID", "20003000", this.testCardDisplay02.getMultiverseId());
	}
	
	@Test
	public void testToString() {
		assertEquals("Get ToString", "Air Elemental, Limited Edition Alpha, Creature - Elemental, Blue, Rare, 10002000", this.testCardDisplay01.toString());
		assertEquals("Get ToString", "Paradise Plume, Time Spiral, Artifact, -, Uncommon, 20003000", this.testCardDisplay02.toString());
	}
}
