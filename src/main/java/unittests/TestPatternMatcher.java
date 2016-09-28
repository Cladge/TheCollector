package unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import thecollector.utils.PatternMatcher;

/**
 * Test class for {@code PatternMatcher}.
 * 
 * <p>
 * Expectations:
 * <ul>
 * <li>Pattern matching finds the following symbols:
 * <ul>
 * <li>{W}</li>
 * <li>{U}</li>
 * <li>{B}</li>
 * <li>{R}</li>
 * <li>{G}</li>
 * <li>{T}</li>
 * <li>{0}</li>
 * <li>{1}</li>
 * <li>{2}</li>
 * <li>{3}</li>
 * <li>{4}</li>
 * <li>{5}</li>
 * <li>{6}</li>
 * <li>{7}</li>
 * <li>{8}</li>
 * <li>{9}</li>
 * <li>{X}</li>
 * <li>{C}</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Ian Claridge
 */
public class TestPatternMatcher {

	private PatternMatcher patternMatcher;
	private ArrayList<Integer> testPair01;
	private ArrayList<Integer> testPair02;
	private ArrayList<Integer> testPair03;
	private ArrayList<Integer> testPair04;
	private ArrayList<ArrayList<Integer>> expectedList;
	
	@Before
	public void setUp() throws Exception {
		this.patternMatcher = new PatternMatcher("\\{[A-Z,a-z,0-9]\\}");
		testPair01 = new ArrayList<Integer>();
		testPair02 = new ArrayList<Integer>();
		testPair03 = new ArrayList<Integer>();
		testPair04 = new ArrayList<Integer>();
		expectedList = new ArrayList<ArrayList<Integer>>();
	}
	
	private void runPatternMatch(String textToParse) {
		ArrayList<ArrayList<Integer>> matches = this.patternMatcher.getMatches(textToParse);
		assertEquals(this.expectedList, matches);
	}
	
	@Test
	public void testSymbolsWhiteBlueBlackRed() {
		this.testPair01.add(6);
		this.testPair01.add(9);
		this.testPair02.add(16);
		this.testPair02.add(19);
		this.testPair03.add(27);
		this.testPair03.add(30);
		this.testPair04.add(36);
		this.testPair04.add(39);
		this.expectedList.add(testPair01);
		this.expectedList.add(testPair02);
		this.expectedList.add(testPair03);
		this.expectedList.add(testPair04);

		this.runPatternMatch("White {W}, Blue {U}, Black {B}, Red {R}");
	}

	@Test
	public void testSymbolsGreenTapZeroOne() {
		this.testPair01.add(6);
		this.testPair01.add(9);
		this.testPair02.add(15);
		this.testPair02.add(18);
		this.testPair03.add(25);
		this.testPair03.add(28);
		this.testPair04.add(34);
		this.testPair04.add(37);
		this.expectedList.add(testPair01);
		this.expectedList.add(testPair02);
		this.expectedList.add(testPair03);
		this.expectedList.add(testPair04);

		this.runPatternMatch("Green {G}, Tap {T}, Zero {0}, One {1}");
	}

	@Test
	public void testSymbolsTwoThreeFourFive() {
		this.testPair01.add(4);
		this.testPair01.add(7);
		this.testPair02.add(15);
		this.testPair02.add(18);
		this.testPair03.add(25);
		this.testPair03.add(28);
		this.testPair04.add(35);
		this.testPair04.add(38);
		this.expectedList.add(testPair01);
		this.expectedList.add(testPair02);
		this.expectedList.add(testPair03);
		this.expectedList.add(testPair04);

		this.runPatternMatch("Two {2}, Three {3}, Four {4}, Five {5}");
	}

	@Test
	public void testSymbolsSixSevenEightNine() {
		this.testPair01.add(4);
		this.testPair01.add(7);
		this.testPair02.add(15);
		this.testPair02.add(18);
		this.testPair03.add(26);
		this.testPair03.add(29);
		this.testPair04.add(36);
		this.testPair04.add(39);
		this.expectedList.add(testPair01);
		this.expectedList.add(testPair02);
		this.expectedList.add(testPair03);
		this.expectedList.add(testPair04);

		this.runPatternMatch("Six {6}, Seven {7}, Eight {8}, Nine {9}");
	}

	@Test
	public void testSymbolsXC() {
		this.testPair01.add(2);
		this.testPair01.add(5);
		this.testPair02.add(9);
		this.testPair02.add(12);
		this.expectedList.add(testPair01);
		this.expectedList.add(testPair02);

		this.runPatternMatch("X {X}, C {C}");
	}
}
