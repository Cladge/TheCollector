package unittests;

import thecollector.model.SettingsFile;

/**
 * A virtual settings file, which overrides any io operations (that is,
 * does not actually create any files or directories).
 * 
 * @author Ian
 */
public class FakeSettingsFile extends SettingsFile {

	/**
	 * Constructor.
	 * 
	 * @param settingsPath - String
	 */
	public FakeSettingsFile(String settingsPath) {
		super(settingsPath);
	}
	
	public boolean mkdir() {
		return true;
	}
	
	public boolean delete() {
		return true;
	}
}
