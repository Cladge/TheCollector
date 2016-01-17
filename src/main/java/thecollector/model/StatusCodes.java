package thecollector.model;

/**
 * An Enum to represent the different status codes that the application can use.
 * 
 * @author Ian Claridge
 */
public enum StatusCodes {
	OK				(0),
	WARNING			(100),
	SETTINGS_ERROR	(200),
	MISC_ERROR		(300),
	DATABASE_ERROR	(400),
	SEVERE_ERROR	(1000);

	private final int returnCode;

	/**
	 * Constructor for the enum (used internally). Needed to provide the relevant field values. 
	 * 
	 * @param returnCode - int
	 */
	StatusCodes(int returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * Return the value of the return code instance.
	 * 
	 * @return int
	 */
	public int value() {
		return this.returnCode;
	}
}
