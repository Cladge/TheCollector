package unittests;

import thecollector.model.SettingsFile;

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
}
