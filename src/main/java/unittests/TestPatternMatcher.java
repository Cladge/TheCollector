package unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import thecollector.utils.PatternMatcher;

/**
 * Test class for {@code PatternMatcher}.
 * 
 * @author Ian Claridge
 */
public class TestPatternMatcher {

	private PatternMatcher patternMatcher;
	private ArrayList<Integer> testPair01;
	private ArrayList<Integer> testPair02;
	private ArrayList<Integer> testPair03;
	private ArrayList<ArrayList<Integer>> expectedList;
	
	@Before
	public void setUp() throws Exception {
		this.patternMatcher = new PatternMatcher("\\{[A-Z,a-z,0-9]\\}");
		testPair01 = new ArrayList<Integer>();
		testPair02 = new ArrayList<Integer>();
		testPair03 = new ArrayList<Integer>();
		expectedList = new ArrayList<ArrayList<Integer>>();
	}

	@Test
	public void testValue01() {
		this.testPair01.add(11);
		this.testPair01.add(14);
		this.testPair02.add(14);
		this.testPair02.add(17);
		this.testPair03.add(17);
		this.testPair03.add(20);
		this.expectedList.add(testPair01);
		this.expectedList.add(testPair02);
		this.expectedList.add(testPair03);
		
		ArrayList<ArrayList<Integer>> matches = this.patternMatcher.getMatches("Mana cost: {3}{U}{U}");
		
		assertEquals(this.expectedList, matches);
	}

	@Test
	public void testValue02() {
		this.testPair01.add(8);
		this.testPair01.add(11);
		this.expectedList.add(testPair01);
		
		ArrayList<ArrayList<Integer>> matches = this.patternMatcher.getMatches("Colour: {W}");
		
		assertEquals(this.expectedList, matches);
	}

	@Test
	public void testValue03() {
		this.testPair01.add(0);
		this.testPair01.add(3);
		this.expectedList.add(testPair01);
		
		ArrayList<ArrayList<Integer>> matches = this.patternMatcher.getMatches("{T}: Sacrifice this card");
		
		assertEquals(this.expectedList, matches);
	}
}
