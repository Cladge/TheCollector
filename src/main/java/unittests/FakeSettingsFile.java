package unittests;

import thecollector.model.SettingsFile;

/**
 * A virtual settings file, which overrides any io operations (that is,
 * does not actually create or delete any files or directories).
 * 
 * @author Ian
 */
public class FakeSettingsFile extends SettingsFile {

	/**
	 * Constructor.
	 * 
	 * @param settingsPath - String
	 */
	public FakeSettingsFile(String settingsPath, String settingsName) {
		super(settingsPath, settingsName);
	}
	
	public boolean delete() {
		return true;
	}
	
	public boolean deletePath() {
		return true;
	}

	public boolean save(String comment) {
		return true;
	}
}
