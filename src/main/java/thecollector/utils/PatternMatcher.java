package thecollector.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A pattern matching utility.
 * 
 * @author Ian Claridge
 *
 */
public class PatternMatcher {
	
	private String patternString;
	private Pattern pattern;

	/**
	 * Constructor.
	 * 
	 * @param patternString - String
	 */
	public PatternMatcher(String patternString) {
		this.patternString = patternString;
		
		this.pattern = Pattern.compile(this.patternString);
	}


	/**
	 * Get pattern String.
	 * 
	 * @return String
	 */
	public String getPatternString() {
		return this.patternString;
	}
	
	/**
	 * 
	 * @param textToParse - String
	 * 
	 * @return List of matching index start/end pairs.
	 */
	public ArrayList<ArrayList<Integer>> getMatches(String textToParse) {
		ArrayList<ArrayList<Integer>> matches = new ArrayList<ArrayList<Integer>>();

        Matcher matcher = this.pattern.matcher(textToParse);

    	while (matcher.find()) {
    		ArrayList<Integer> matchingPair = new ArrayList<Integer>();
    		matchingPair.add(matcher.start());
    		matchingPair.add(matcher.end());
    		matches.add(matchingPair);
    	}
    	
		return matches;
	}
}
