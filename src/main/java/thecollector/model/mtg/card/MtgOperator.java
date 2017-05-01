package thecollector.model.mtg.card;

/**
 * An Enum to represent the different relational operators used in card attribute matching.
 * 
 * @author Ian Claridge
 */
public enum MtgOperator {
	EQUALS						("="),
	GREATER_THAN				(">"),
	LESS_THAN					("<"),
	GREATER_THAN_OR_EQUAL_TO	(">="),
	LESS_THAN_OR_EQUAL_TO		("<=");

	private final String operator;

	/**
	 * Constructor for the enum (used internally). Needed to provide the relevant field values. 
	 * 
	 * @param operator - String
	 */
	MtgOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Return the value of the return code instance.
	 * 
	 * @return String
	 */
	public String value() {
		return this.operator;
	}
}
